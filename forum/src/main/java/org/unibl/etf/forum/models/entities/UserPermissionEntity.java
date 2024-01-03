package org.unibl.etf.forum.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity
@jakarta.persistence.Table(name = "user_permission", schema = "forum", catalog = "")
@jakarta.persistence.IdClass(org.unibl.etf.forum.models.entities.UserPermissionEntityPK.class)
public class UserPermissionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "PERMISSION_ID")
    private Integer permissionId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "USER_ID")
    private Integer userId;

}
