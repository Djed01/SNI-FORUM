export interface UserPermission {
    userId: number;
    topicId: number;
    addPermission: boolean;
    editPermission: boolean;
    deletePermission: boolean;
  }