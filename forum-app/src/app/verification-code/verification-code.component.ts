import { Component, Output, EventEmitter } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
@Component({
  selector: 'app-verification-code',
  templateUrl: './verification-code.component.html',
  styleUrls: ['./verification-code.component.css']
})
export class VerificationCodeComponent {
  code: string = '';

  constructor(private authService: AuthService, private router: Router,private snackBar: MatSnackBar) {}

  onSubmit() {
    this.authService.verifyCode(this.code).subscribe(
      (response) => {
        localStorage.setItem('token', response.token);
        this.authService.clearTempUsername(); // Clear the temporary username
        this.router.navigate(['/topics']);
      },
      (error) => {
        this.snackBar.open("Invalid Verification Code!", 'Close', {
          duration: 3000,
        });
      }
    );
  }
}

