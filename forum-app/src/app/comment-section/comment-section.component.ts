import { Component, OnInit, Input } from '@angular/core';
import { TopicService } from '../services/topic.service';
import { Comment } from '../models/comment.model';

@Component({
  selector: 'app-comment-section',
  templateUrl: './comment-section.component.html',
})
export class CommentSectionComponent implements OnInit {
  @Input() topicId: number = 0;
  comments: any[] = [];

  constructor(private topicService: TopicService) {}

  ngOnInit() {
    if (this.topicId) {
      this.topicService.getCommentsByTopic(this.topicId).subscribe((data) => {
        this.comments = data;
      });
    }
  }
}
