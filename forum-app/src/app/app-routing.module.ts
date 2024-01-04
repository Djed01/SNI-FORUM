import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { TopicListComponent } from './topic-list/topic-list.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'topics', component: TopicListComponent },
  // Add other routes here
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
