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

  getCommentRequestsByTopic(topicId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.baseUrl}/topic/${topicId}/falseStatus`);
  }


  postComment(comment: Comment): Observable<any> {
    return this.http.post(this.baseUrl, comment);
  }

  updateCommentStatus(commentId: number): Observable<Comment> {
    return this.http.put<Comment>(`${this.baseUrl}/${commentId}/status/true`, null);
  }

  deleteComment(commentId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${commentId}`);
  }
  
  updateComment(commentId: number, updatedComment: Comment): Observable<Comment> {
    return this.http.put<Comment>(`${this.baseUrl}/${commentId}`, updatedComment);
  }
}
