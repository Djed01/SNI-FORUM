export interface UserPermissionEntity {
    userId: number;
    topicId: number;
    addPermission: boolean;
    editPermission: boolean;
    deletePermission: boolean;
  }
  