import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Comment } from '../models/comment.model'; 

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private baseUrl = 'http://localhost:8080/api/comments';

  constructor(private http: HttpClient) {}

  getCommentsByTopic(topicId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.baseUrl}/topic/${topicId}`);
  }


  postComment(comment: Comment): Observable<any> {
    return this.http.post(this.baseUrl, comment);
  }
  
}
