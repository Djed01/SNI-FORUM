package org.unibl.etf.forum.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "user_permission", schema = "forum", catalog = "")
@IdClass(UserPermissionEntityPK.class)
public class UserPermissionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "UserID")
    private Integer userId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "TopicID")
    private Integer topicId;
    @Basic
    @Column(name = "Add")
    private Byte add;
    @Basic
    @Column(name = "Edit")
    private Byte edit;
    @Basic
    @Column(name = "Delete")
    private Byte delete;
    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "ID", nullable = false)
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "TopicID", referencedColumnName = "ID", nullable = false)
    private TopicEntity topic;

}
