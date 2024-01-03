package org.unibl.etf.forum.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@jakarta.persistence.Table(name = "permission", schema = "forum", catalog = "")
public class PermissionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "ID")
    private Integer id;

    @Basic
    @Column(name = "Type")
    private String type;

}
