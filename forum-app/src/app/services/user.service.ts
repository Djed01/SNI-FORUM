import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user.model'; // Ensure you have this model
import { UserPermission } from '../models/permission.model';
import { jwtDecode } from 'jwt-decode';


@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl = 'https://localhost:8080/api/users';
  private userPermissionsUrl = 'https://localhost:8080/api/userPermissions';
  private currentUser: User;

  constructor(private http: HttpClient) {
    this.currentUser = this.decodeUserFromToken();
  }

  // Private method to create HTTP headers with the JWT token
  private createHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  getUserById(id: number): Observable<User> {
    const headers = this.createHeaders();
    return this.http.get<User>(`${this.baseUrl}/${id}`, { headers });
  }

  getUserByUsername(username: string): Observable<User> {
    const headers = this.createHeaders();
    return this.http.get<User>(`${this.baseUrl}/username/${username}`, { headers });
  }

  //------------- GitHub Auth ------------
  public getUserID(pid:any){
    return this.http.get(this.baseUrl + "callback?code="+ pid)
  }

  public addUser(user: User){
    return this.http.post<User>(this.baseUrl, user)
  }

  // -------------------------------------

  getInactiveUsers(): Observable<User[]> {
    const headers = this.createHeaders();
    return this.http.get<User[]>(`${this.baseUrl}/status/false`, { headers });
  }

  updateUserStatus(userId: number, status: boolean): Observable<any> {
    const headers = this.createHeaders();
    return this.http.patch(`${this.baseUrl}/${userId}/activate`, { status }, { headers });
  }

  getUserPermissions(userId: number): Observable<UserPermission[]> {
    const headers = this.createHeaders();
    return this.http.get<UserPermission[]>(`${this.userPermissionsUrl}/user/${userId}`, { headers });
  }

  updateUserPermissions(userId: number, topicId: number, permission: UserPermission): Observable<UserPermission> {
    const headers = this.createHeaders();
    const url = `${this.userPermissionsUrl}/user/${userId}/topic/${topicId}`;
    return this.http.put<UserPermission>(url, permission, { headers });
  }

  declineUser(userId: number): Observable<any> {
    const headers = this.createHeaders();
    return this.http.delete(`${this.baseUrl}/${userId}`, { headers });
  }
  
  saveUserPermissions(permissions: UserPermission[]): Observable<any> {
    const headers = this.createHeaders();
    return this.http.post(`${this.userPermissionsUrl}/`, permissions, { headers });
  }

  updateUserRole(userId: number, role: string): Observable<User> {
    const headers = this.createHeaders();
    const url = `${this.baseUrl}/setRole/${userId}/${role}`;
    return this.http.put<User>(url, {}, { headers });
  }


    private decodeUserFromToken(): any {
      const token = localStorage.getItem('token');
      if (token) {
          try {
            return jwtDecode(token);

          } catch (error) {
              console.error('Error decoding token', error);
              return null;
          }
      }
      return null;
  }

    isAdmin(): boolean {
        return this.currentUser && this.currentUser.role === 'ROLE_ADMIN';
    }

    isModerator(): boolean {
        return this.currentUser && this.currentUser.role === 'ROLE_MODERATOR';
    }
}
