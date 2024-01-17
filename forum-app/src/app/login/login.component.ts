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
  username: string = "";
  password: string = "";

  constructor(private authService: AuthService, private router: Router,private snackBar: MatSnackBar) {}

  login() {
    this.authService.login(this.username, this.password).subscribe(
      () => {
        console.log('Login Success');
      },
      (error) => {
        console.error('Login failed', error);
        this.snackBar.open("Invalid Username or Password or Your Acount is Not Activated!", 'Close', {
          duration: 3000,
        });
        this.router.navigate(['/']);
      }
    );
    this.router.navigate(['/verification']);
  }
}

