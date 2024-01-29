import { Component, Output, EventEmitter } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
@Component({
  selector: 'app-verification-code',
  templateUrl: './verification-code.component.html',
  styleUrls: ['./verification-code.component.css']
})
export class VerificationCodeComponent {
  code: string = '';

  ngOnInit(){
    if (!this.authService.tempUsername) {
      this.router.navigate(['/']); // Redirect to home or another route
    }
  }

  constructor(private authService: AuthService, private router: Router,private snackBar: MatSnackBar,private route: ActivatedRoute) {
    this.route.queryParams.subscribe(params => {
    
      const githubCode = params['code'];
      if (githubCode) {
        authService.tempUsername = "GitHubTempUsername";
        this.router.navigate(['/verification']);
        this.authService.exchangeGitHubCodeForToken(githubCode).subscribe(
          response => {
            authService.tempUsername = response.username;
            console.log(authService.tempUsername);
          },
          error => {
            this.router.navigate(['/']);
            this.snackBar.open("GitHub Login Failed or Your Account Is Not Activated", 'Close', {
              duration: 5000,
            });
          }
        );
      }else{
        
      }
    });
  }

  onSubmit() {
    this.authService.verifyCode(this.code).subscribe(
      (response) => {
        localStorage.setItem('token', response.token);
        this.authService.clearTempUsername(); // Clear the temporary username
        this.router.navigate(['/topics']).then(() => {
          window.location.reload(); // Reload the page after navigation
        });
      },
      (error) => {
        this.snackBar.open("Invalid Verification Code!", 'Close', {
          duration: 5000,
        });
      }
    );
  }
}

