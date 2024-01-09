import { Component, OnInit } from '@angular/core';
import { UserPermission } from '../models/permission.model';
import { User } from '../models/user.model';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-not-activated-users',
  templateUrl: './not-activated-users.component.html',
  styleUrls: ['./not-activated-users.component.css']
})
export class NotActivatedUsersComponent implements OnInit {
  inactiveUsers: User[] = [];
  permissions: { [userId: number]: { [topicId: string]: { add: boolean, edit: boolean, delete: boolean } } } = {};
  displayedColumns: string[] = ['userName','email','roleColumn', 'sciencePermissions','culturePermissions',
  'sportPermissions','musicPermissions', 'activate'];

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.loadInactiveUsers();
  }

  loadInactiveUsers() {
    this.userService.getInactiveUsers().subscribe(users => {
      this.inactiveUsers = users;
      users.forEach(user => {
        this.permissions[user.id] = {
          'science': { add: false, edit: false, delete: false }, // science
          'culture': { add: false, edit: false, delete: false }, // culture
          'sport': { add: false, edit: false, delete: false }, // sport
          'music': { add: false, edit: false, delete: false } // music
        };
      });
    });
  }



activateUser(user: User) {
  const userPermissions: UserPermission[] = this.constructUserPermissions(user.id);
  console.log(userPermissions);
  this.userService.saveUserPermissions(userPermissions).subscribe(() => {
    // Call the updateUserRole API
    this.userService.updateUserRole(user.id, user.role).subscribe(() => {
      // Update user status after saving role
      this.userService.updateUserStatus(user.id, true).subscribe(() => {
        this.loadInactiveUsers(); // Refresh the list after activation
      });
    });
  });
}


  constructUserPermissions(userId: number): UserPermission[] {
    let permissionsToSave: UserPermission[] = [];
    Object.keys(this.permissions[userId]).forEach(topicId => {
      let permission: UserPermission = {
        userId: userId,
        topicId: this.getTopicId(topicId),
        addPermission: this.permissions[userId][topicId].add,
        editPermission: this.permissions[userId][topicId].edit,
        deletePermission: this.permissions[userId][topicId].delete
      };
      permissionsToSave.push(permission);
    });
    return permissionsToSave;
  }


  handleDecline(userId: number) {
    this.userService.declineUser(userId).subscribe({
      next: () => {
        console.log(`User with ID ${userId} declined successfully.`);
        this.loadInactiveUsers(); // Refresh the list
      },
      error: (error) => {
        console.error('There was an error declining the user', error);
      }
    });
  }
 

  getTopicId(topicName: string): number {
    interface TopicIds {
      [key: string]: number;
    }
    const topicIds: TopicIds = { 'science': 1, 'culture': 2, 'sport': 3, 'music': 4 };
    return topicIds[topicName] || 0;
  }

}
