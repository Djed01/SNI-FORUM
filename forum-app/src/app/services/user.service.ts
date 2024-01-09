import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user.model'; // Ensure you have this model
import { UserPermission } from '../models/permission.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl = 'http://localhost:8080/api/users';
  private usersUrl = 'http://localhost:8080/api/users';
  private userPermissionsUrl = 'http://localhost:8080/api/userPermissions';

  constructor(private http: HttpClient) {}

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/${id}`);
  }

  getInactiveUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.usersUrl}/status/false`);
  }

  updateUserStatus(userId: number, status: boolean): Observable<any> {
    return this.http.patch(`${this.usersUrl}/${userId}/activate`, { status });
  }

  getUserPermissions(userId: number): Observable<UserPermission[]> {
    return this.http.get<UserPermission[]>(`${this.userPermissionsUrl}/user/${userId}`);
  }

  updateUserPermissions(userId: number, topicId: number, permission: UserPermission): Observable<UserPermission> {
    const url = `${this.userPermissionsUrl}/user/${userId}/topic/${topicId}`;
    return this.http.put<UserPermission>(url, permission);
  }


  declineUser(userId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${userId}`);
  }
  
  saveUserPermissions(permissions: UserPermission[]): Observable<any> {
    // Assuming your backend expects a POST request to save multiple permissions at once
    return this.http.post(`${this.userPermissionsUrl}/`, permissions);
  }
  
}
