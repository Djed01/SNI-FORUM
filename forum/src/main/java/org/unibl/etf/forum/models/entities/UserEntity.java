package org.unibl.etf.forum.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@jakarta.persistence.Table(name = "user", schema = "forum", catalog = "")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "ID")
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
    @Column(name = "RoleID")
    private Integer roleId;

}
