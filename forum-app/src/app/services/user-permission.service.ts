import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserPermissionEntity } from '../models/user-permission.model'; // You need to create this model

@Injectable({
  providedIn: 'root'
})
export class UserPermissionService {
  private apiUrl = 'https://localhost:8080/api/userPermissions';

  constructor(private http: HttpClient) {}

    // Private method to create HTTP headers with the JWT token
    private createHeaders(): HttpHeaders {
      const token = localStorage.getItem('token');
      return new HttpHeaders().set('Authorization', `Bearer ${token}`);
    }

  getPermissionsByUserIdAndTopicId(userId: number, topicId: number): Observable<UserPermissionEntity> {
    const headers = this.createHeaders();
    return this.http.get<UserPermissionEntity>(`${this.apiUrl}/user/${userId}/topic/${topicId}`, { headers });
  }
}
