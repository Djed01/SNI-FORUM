import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

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

  constructor(private authService: AuthService, private router: Router) {}

  register() {
    if (this.password === this.verifyPassword) {
      this.authService.signup(this.username, this.email, this.password)
        .subscribe(
          response => {
            console.log('User registered successfully', response);
            this.router.navigate(['']); // Navigate to the desired route after registration
          },
          error => {
            console.error('Registration failed', error);
            // Handle registration error
          }
        );
    } else {
      console.error('Passwords do not match!');
      // Handle the error case where the passwords do not match
    }
  }
}
