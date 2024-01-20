import { Component } from '@angular/core';
import { UserService } from './services/user.service';
import { Router } from '@angular/router';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  constructor(private router: Router, public userService: UserService, private authService:AuthService) {}

  async logout() {
    try {
      await this.authService.logout();
      this.router.navigate(['/']);
    } catch (error) {
      console.error('Logout failed:', error);
    }
  }

  getForumLink(): string[] {
    return this.shouldShowMenu() ? ['/topics'] : ['/'];
  }


  shouldShowMenu(): boolean {
    const currentUrl = this.router.url;
    const hideOnRoutes = ['/', '/register','/verification'];
    const isOnHideRoute = hideOnRoutes.includes(currentUrl);
    
    return !isOnHideRoute;
  }
  title = 'forum-app';
}
