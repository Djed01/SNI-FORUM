import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Comment } from '../models/comment.model';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private baseUrl = 'http://localhost:8080/api/comments';

  constructor(private http: HttpClient) {}

  // Private method to create HTTP headers with the JWT token
  private createHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  getCommentsByTopic(topicId: number): Observable<Comment[]> {
    const headers = this.createHeaders();
    return this.http.get<Comment[]>(`${this.baseUrl}/topic/${topicId}`, { headers });
  }

  getCommentRequestsByTopic(topicId: number): Observable<Comment[]> {
    const headers = this.createHeaders();
    return this.http.get<Comment[]>(`${this.baseUrl}/topic/${topicId}/falseStatus`, { headers });
  }

  postComment(comment: Comment): Observable<any> {
    const headers = this.createHeaders();
    return this.http.post(this.baseUrl, comment, { headers });
  }

  updateCommentStatus(commentId: number): Observable<Comment> {
    const headers = this.createHeaders();
    return this.http.put<Comment>(`${this.baseUrl}/${commentId}/status/true`, null, { headers });
  }

  deleteComment(commentId: number): Observable<any> {
    const headers = this.createHeaders();
    return this.http.delete(`${this.baseUrl}/${commentId}`, { headers });
  }
  
  updateComment(commentId: number, updatedComment: Comment): Observable<Comment> {
    const headers = this.createHeaders();
    return this.http.put<Comment>(`${this.baseUrl}/${commentId}`, updatedComment, { headers });
  }
}
