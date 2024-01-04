package org.unibl.etf.forum.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "topic", schema = "forum", catalog = "")
public class TopicEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Integer id;
    @Basic
    @Column(name = "Name")
    private String name;
    @OneToMany(mappedBy = "topic")
    private List<CommentEntity> commentsById;
    @OneToMany(mappedBy = "topic")
    private List<UserPermissionEntity> userPermissionsById;

}
