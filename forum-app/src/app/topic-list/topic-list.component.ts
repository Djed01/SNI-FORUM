import { Component, OnInit } from '@angular/core';
import { TopicService } from '../services/topic.service';
import { Topic } from '../models/topic.model';

@Component({
  selector: 'app-topic-list',
  templateUrl: './topic-list.component.html',
})
export class TopicListComponent implements OnInit {
  topics: Topic[] = [];

  constructor(private topicService: TopicService) {}

  ngOnInit() {
    this.topicService.getAllTopics().subscribe((data) => {
      this.topics = data;
    });
  }
}
