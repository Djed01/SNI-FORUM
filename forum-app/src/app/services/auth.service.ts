import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private baseUrl = 'http://localhost:8080/auth';
  private tempUsername: string | null = null; 
  constructor(private http: HttpClient) {}

  signup(username: string, email: string, password: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/signup`, { username, email, password });
  }

  login(username: string, password: string): Observable<any> {
    this.tempUsername = username; // Store username temporarily
    return this.http.post<any>(`${this.baseUrl}/login`, { username, password });
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
