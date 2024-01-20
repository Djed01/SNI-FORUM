import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { CommentService } from '../services/comment.service';
import { UserService } from '../services/user.service';
import { Comment } from '../models/comment.model';
import { ActivatedRoute } from '@angular/router';
import { User } from '../models/user.model';
import { jwtDecode } from 'jwt-decode';
import { MatDialog } from '@angular/material/dialog';
import { TemplateRef } from '@angular/core';
import { UserPermissionService } from '../services/user-permission.service';
import { UserPermissionEntity } from '../models/user-permission.model'; 


@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
  styleUrls: ['./comment-list.component.css'],
})
export class CommentListComponent implements OnInit {
  @Input() topicId: number = 0;
  @ViewChild('confirmDialog') confirmDialog!: TemplateRef<any>;
  comments: Comment[] = [];
  usernames: { [userId: number]: string } = {};
  newCommentContent: string = ''; 
  currentPage: number = 1;
  limit: number = 20;  // Number of comments per page
  totalComments: number = 0;
  currentTopicName: string = 'Loading...';
  currentUserId: number = 0;
  userPermissions: UserPermissionEntity | null = null;

  constructor(
    private commentService: CommentService,
    private userService: UserService,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private userPermissionService: UserPermissionService,
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      const topicId = +params['topicId']; // '+' converts the string 'topicId' to a number
      if (topicId) {
        this.fetchCurrentUserId();
        this.topicId = topicId;
        this.loadComments(topicId);
        this.currentTopicName = this.getTopicName(topicId);
      }
    });
  }

  fetchUserPermissions() {
    if (this.currentUserId && this.topicId) {
      this.userPermissionService.getPermissionsByUserIdAndTopicId(this.currentUserId, this.topicId).subscribe(
        (permissions: UserPermissionEntity) => {
          // Store the permissions
          this.userPermissions = permissions;
        }
      );
    }
  }

  fetchCurrentUserId() {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken = jwtDecode(token);
      const username = decodedToken.sub || ''; // Extract the username from the token
      if (username) {
      this.userService.getUserByUsername(username).subscribe(
        (user: User) => {
            this.currentUserId = user.id;

            // GET the user permissions
            this.fetchUserPermissions();
        });
    }
  }
  }

  isCurrentUserAuthorOfComment(comment: Comment): boolean {
    return comment.userId === this.currentUserId; 
  }
  
  declineComment(commentId: number): void {
    this.commentService.deleteComment(commentId).subscribe(
      () => {
        // Remove the declined comment from the comments array
        this.comments = this.comments.filter(comment => comment.id !== commentId);
      },
      error => {
        console.error('Error updating comment status:', error);
      }
    );
  }

  editingComment: Comment | null = null;

  startEdit(comment: Comment): void {
    this.editingComment = comment;
  }

  submitEdit(comment: Comment): void {
    this.commentService.updateComment(comment.id, comment).subscribe(
      updatedComment => {
        // Find the index of the updated comment in the comments array
        const index = this.comments.findIndex(c => c.id === updatedComment.id);
        if (index !== -1) {
          // Update the comment in the array
          this.comments[index] = updatedComment;
        }
  
        // Reset the editing state
        this.editingComment = null;
      },
      error => {
        console.error('Error updating comment:', error);
      }
    );
  }
  
  cancelEdit(): void {
    this.editingComment = null;
  }

  openConfirmDialog(commentId: number) {
    const dialogRef = this.dialog.open(this.confirmDialog);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.declineComment(commentId);
      }
    });
  }

  get displayedComments(): Comment[] {
    const startIndex = (this.currentPage - 1) * this.limit;
    return this.comments.slice(startIndex, startIndex + this.limit);
  }


  loadComments(topicId: number) {
    this.commentService.getCommentsByTopic(topicId).subscribe(
      (comments: Comment[]) => {
        this.comments = comments; // Assign the fetched comments
        this.totalComments = comments.length;
        this.currentPage = Math.ceil(this.totalComments / this.limit);
        this.loadUsernames();
      },
      error => {
        // Handle any errors here
        console.error('There was an error fetching comments:', error);
      }
    );
  }

  loadUsernames() {
    this.comments.forEach(comment => {
      this.userService.getUserById(comment.userId).subscribe(
        (user: User) => {
          this.usernames[comment.userId] = user.username; // Assuming 'username' is a property of the User model
        }
      );
    });
  }

  postComment() {
    if (this.newCommentContent.trim()) {
      const token = localStorage.getItem('token');
      if (token) {
        const decodedToken = jwtDecode(token);
        const username = decodedToken.sub || ''; // Extract the username from the token
        
        if (username) {
        this.userService.getUserByUsername(username).subscribe(
          (user: User) => {
            const newComment: Comment = {
              id: 0,
              topicId: this.topicId,
              userId: user.id, // Use the actual user's ID
              content: this.newCommentContent,
              time: new Date(),
              status: false,
            };
  
            this.commentService.postComment(newComment).subscribe(
              response => {
                // Handle response here
                console.log('Comment posted successfully', response);
               // this.comments.push(newComment);
                this.newCommentContent = '';
              },
              error => {
                // Handle error here
                console.error('There was an error posting the comment:', error);
              }
            );
          },
          error => {
            console.error('Error fetching user by username:', error);
          }
        );
      }
    }
    }
  }

  changePage(newPage: number) {
    this.currentPage = newPage;
  }

  getTopicName(topicId: number): string {
    switch (topicId) {
      case 1: return 'Science';
      case 2: return 'Culture';
      case 3: return 'Sport';
      case 4: return 'Music';
      default: return 'Unknown'; // Default case for unknown topicId
    }
  }
}
