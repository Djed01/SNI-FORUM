import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { TopicListComponent } from './topic-list/topic-list.component';
import { RegisterComponent } from './register/register.component';
import { CommentListComponent } from './comment-list/comment-list.component';
import { VerificationCodeComponent } from './verification-code/verification-code.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'topics', component: TopicListComponent },
  { path: 'register', component: RegisterComponent},
  { path: 'comments/:topicId', component: CommentListComponent },
  { path: 'verification', component: VerificationCodeComponent}
  // Add other routes here
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
