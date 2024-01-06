import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TopicService } from '../services/topic.service';
import { Topic } from '../models/topic.model';

@Component({
  selector: 'app-topic-list',
  templateUrl: './topic-list.component.html',
  styleUrls: ['./topic-list.component.css']
})
export class TopicListComponent implements OnInit {
  topics: Topic[] = [];

  constructor(private topicService: TopicService, private router: Router) {}

  ngOnInit() {
    this.topicService.getAllTopics().subscribe((data) => {
      this.topics = data;
    });
  }

  onTopicSelect(topic: Topic) {
    this.router.navigate(['/comments', topic.id]);
  }

  getImageUrl(topicName: string): string {
    const images: { [key: string]: string } = {
      'Science': 'assets/images/science.jpg',
      'Sport': 'assets/images/sport.png',
      'Music': 'assets/images/music.jpg',
      'Culture': 'assets/images/culture.jpg'
    };
    return images[topicName] || 'assets/images/placeholder.jpg';
  }
  
  getDescription(topicName: string): string {
    const descriptions: { [key: string]: string } = {
      'Science': 'Explore the wonders of science, from physics to biology and beyond.',
      'Sport': 'Dive into the world of sports, featuring events, news, and athlete updates.',
      'Music': 'Discover the rhythms and beats of the music world, covering all genres and eras.',
      'Culture': 'Immerse yourself in diverse cultures, traditions, arts, and societal trends.'
    };
  
    return descriptions[topicName] || 'Explore various topics of interest and expand your knowledge.';
  }
  
}
