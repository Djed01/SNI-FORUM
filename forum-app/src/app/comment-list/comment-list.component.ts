import { Component, OnInit, Input } from '@angular/core';
import { CommentService } from '../services/comment.service';
import { UserService } from '../services/user.service';
import { Comment } from '../models/comment.model';
import { ActivatedRoute } from '@angular/router';
import { User } from '../models/user.model';

@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
  styleUrls: ['./comment-list.component.css'],
})
export class CommentListComponent implements OnInit {
  @Input() topicId: number = 0;
  comments: Comment[] = [];
  usernames: { [userId: number]: string } = {};

  constructor(
    private commentService: CommentService,
    private userService: UserService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      const topicId = +params['topicId']; // '+' converts the string 'topicId' to a number
      if (topicId) {
       // this.loadHardcodedComments();
        this.loadComments(topicId);
      }
    });
  }

  loadComments(topicId: number) {
    this.commentService.getCommentsByTopic(topicId).subscribe(
      (comments: Comment[]) => {
        this.comments = comments; // Assign the fetched comments
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
}
