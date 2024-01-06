import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Topic } from '../models/topic.model';

@Injectable({ providedIn: 'root' })
export class TopicService {
  constructor(private http: HttpClient) {}

  getAllTopics() {
    return this.http.get<Topic[]>('http://localhost:8080/api/topics');
  }

  getCommentsByTopic(topicId: number) {
    return this.http.get<Comment[]>(`http://localhost:8080/api/topics/${topicId}`);
  }
}