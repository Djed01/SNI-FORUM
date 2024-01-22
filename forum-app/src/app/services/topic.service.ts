import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Topic } from '../models/topic.model';

@Injectable({ providedIn: 'root' })
export class TopicService {
  constructor(private http: HttpClient) {}

  private createHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  getAllTopics() {
    const headers = this.createHeaders();
    return this.http.get<Topic[]>('https://localhost:8080/api/topics', { headers });
  }

  getCommentsByTopic(topicId: number) {
    const headers = this.createHeaders();
    return this.http.get<Comment[]>(`https://localhost:8080/api/topics/${topicId}`, { headers });
  }

}
