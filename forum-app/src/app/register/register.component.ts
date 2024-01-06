import { Component } from '@angular/core';
import { Router } from '@angular/router';

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

  constructor(private router: Router) {}

  register() {
    if (this.password === this.verifyPassword) {
      // Proceed with the registration process
      // Call your auth service or any business logic here
      console.log('Registering user:', this.username, this.email);

      this.router.navigate(['']);
    } else {
      // Handle the error case where the passwords do not match
      console.error('Passwords do not match!');
      // Show some error message to the user
      return;
    }
  }
}
