package org.unibl.etf.forum.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "user_permission", schema = "forum", catalog = "")
@IdClass(UserPermissionEntityPK.class)
public class UserPermissionEntity {
    @Id
    @Column(name = "UserID")
    private Integer userId;
    @Id
    @Column(name = "TopicID")
    private Integer topicId;
    @Basic
    @Column(name = "AddPermission")
    private Boolean addPermission;
    @Basic
    @Column(name = "EditPermission")
    private Boolean editPermission;
    @Basic
    @Column(name = "DeletePermission")
    private Boolean deletePermission;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "ID", insertable = false,updatable = false)
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "TopicID", referencedColumnName = "ID", insertable = false,updatable = false)
    private TopicEntity topic;
}
