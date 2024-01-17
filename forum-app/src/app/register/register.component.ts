import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'] // ensure this path is correct
})
export class RegisterComponent {
  username: string = "";
  email: string = "";
  password: string = "";
  verifyPassword: string = "";

  constructor(private authService: AuthService, private router: Router,private snackBar: MatSnackBar) {}

  register() {
    if (this.password === this.verifyPassword) {
      this.authService.signup(this.username, this.email, this.password)
        .subscribe(
          response => {
            console.log('User registered successfully', response);
            this.router.navigate(['']); // Navigate to the desired route after registration
          },
          error => {
            this.snackBar.open("Registration Error!", 'Close', {
              duration: 3000,
            });
          }
        );
    } else {
      this.snackBar.open("Passwords don't match!", 'Close', {
        duration: 3000,
      });
    }
  }
}
