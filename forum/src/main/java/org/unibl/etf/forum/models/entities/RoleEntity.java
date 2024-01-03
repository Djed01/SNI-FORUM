package org.unibl.etf.forum.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@jakarta.persistence.Table(name = "role", schema = "forum", catalog = "")
public class RoleEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "ID")
    private Integer id;

    @Basic
    @Column(name = "RoleName")
    private String roleName;

}
