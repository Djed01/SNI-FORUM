package org.unibl.etf.forum.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "user", schema = "forum", catalog = "")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Integer id;
    @Basic
    @Column(name = "Username")
    private String username;
    @Basic
    @Column(name = "PasswordHash")
    private String passwordHash;
    @Basic
    @Column(name = "Email")
    private String email;
    @Basic
    @Column(name = "Status")
    private Boolean status;
    @Basic
    @Column(name = "Role")
    private String role;

    @OneToMany(mappedBy = "user")
    private List<UserPermissionEntity> userPermissions;

}
