import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loading: boolean = false;
  username: string = "";
  password: string = "";

  constructor(
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar,
  ) {}

  login() {
    this.loading = true;
    this.authService.login(this.username, this.password).subscribe(
      () => {
        console.log('Login Success');
        this.router.navigate(['/verification']);
      },
      (error) => {
        this.authService.tempUsername = null;
        console.error('Login failed', error);
        this.snackBar.open("Invalid Username or Password or Your Acount is Not Activated!", 'Close', {
          duration: 3000,
        });
      }
    ).add(() => this.loading = false);
    
  }

  signInWithGitHub() {
    const clientId = 'f22d73ba4a4e441df84f';
    const redirectUri = 'https://localhost:4200/verification';
  
    // Redirect to GitHub for authorization
    window.location.href = `https://github.com/login/oauth/authorize?client_id=${clientId}&redirect_uri=${redirectUri}`;
  }
  

}

