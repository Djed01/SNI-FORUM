import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private http: HttpClient) {}

  login(username: string, password: string) {
    // Replace with your API endpoint
    return this.http.post<any>('/api/auth/login', { username, password });
  }
}