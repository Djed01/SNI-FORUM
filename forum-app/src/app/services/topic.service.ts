import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Topic } from '../models/topic.model';

@Injectable({ providedIn: 'root' })
export class TopicService {
  constructor(private http: HttpClient) {}

  getAllTopics() {
    // Replace with your API endpoint
    return this.http.get<Topic[]>('/api/topics');
  }

  getCommentsByTopic(topicId: number) {
    // Replace with your API endpoint
    return this.http.get<Comment[]>(`/api/comments/topic/${topicId}`);
  }
}