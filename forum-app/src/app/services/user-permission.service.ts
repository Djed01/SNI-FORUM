import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserPermissionEntity } from '../models/user-permission.model'; // You need to create this model

@Injectable({
  providedIn: 'root'
})
export class UserPermissionService {
  private apiUrl = 'http://localhost:8080/api/userPermissions';

  constructor(private http: HttpClient) {}

  getPermissionsByUserIdAndTopicId(userId: number, topicId: number): Observable<UserPermissionEntity> {
    return this.http.get<UserPermissionEntity>(`${this.apiUrl}/user/${userId}/topic/${topicId}`);
  }
}
