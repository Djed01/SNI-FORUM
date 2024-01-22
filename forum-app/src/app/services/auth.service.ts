import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private baseUrl = 'https://localhost:8080/auth';
  public tempUsername: string | null = null; 
  constructor(private http: HttpClient) {}

  private createHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  signup(username: string, email: string, password: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/signup`, { username, email, password });
  }

  login(username: string, password: string): Observable<any> {
    this.tempUsername = username; // Store username temporarily
    return this.http.post<any>(`${this.baseUrl}/login`, { username, password });
  }

  exchangeGitHubCodeForToken(code: string): Observable<any> {
  
    const url = `${this.baseUrl}/github-token-endpoint`;

    const options = {
      params: {
        code: code,
      },
    };

    return this.http.get(url, options);
  }


  logout() {
    const token = localStorage.getItem('token');
    const url = `${this.baseUrl}/logout`;
    const headers = this.createHeaders();
    return this.http.post(url, { token }, { headers }).toPromise();
}

  verifyCode(code: string): Observable<any> {
    if (!this.tempUsername) {
      throw new Error('Username not set');
    }
    return this.http.post<any>(`${this.baseUrl}/verify-2fa`, { username: this.tempUsername, userSubmittedCode: code });
  }

  clearTempUsername(): void {
    this.tempUsername = null;
  }


}
