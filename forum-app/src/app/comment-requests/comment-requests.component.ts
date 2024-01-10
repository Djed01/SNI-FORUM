import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { CommentService } from '../services/comment.service';
import { Comment } from '../models/comment.model';
import { UserService } from '../services/user.service';
import { ActivatedRoute } from '@angular/router';
import { User } from '../models/user.model';
import { MatDialog } from '@angular/material/dialog';
import { TemplateRef } from '@angular/core';


@Component({
  selector: 'app-comment-requests',
  templateUrl: './comment-requests.component.html',
  styleUrl: './comment-requests.component.css'
})
export class CommentRequestsComponent implements OnInit {
  displayedColumns: string[] = ['username', 'content', 'actions'];
  @Input() topicId: number = 0;
  @ViewChild('confirmDialog') confirmDialog!: TemplateRef<any>;
  comments: Comment[] = [];
  usernames: { [userId: number]: string } = {};
  totalComments: number = 0;
  currentTopicName: string = 'Loading...';

  constructor( private commentService: CommentService,
    private userService: UserService,
    private route: ActivatedRoute,
    public dialog: MatDialog) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const topicId = +params['topicId']; // '+' converts the string 'topicId' to a number
      if (topicId) {
       // this.loadHardcodedComments();
        this.loadComments(topicId);
        this.currentTopicName = this.getTopicName(topicId);
      }
    });
  }

  loadComments(topicId: number) {
    this.commentService.getCommentRequestsByTopic(topicId).subscribe(
      (comments: Comment[]) => {
        this.comments = comments; // Assign the fetched comments
        this.totalComments = comments.length;
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

  acceptComment(commentId: number): void {
    this.commentService.updateCommentStatus(commentId).subscribe(
      () => {
        // Remove the accepted comment from the comments array
        this.comments = this.comments.filter(comment => comment.id !== commentId);
      },
      error => {
        console.error('Error updating comment status:', error);
      }
    );
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
