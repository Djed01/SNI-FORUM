package org.unibl.etf.forum.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @OneToMany(mappedBy = "topicId")
    private List<CommentEntity> commentsById;
    @JsonIgnore
    @OneToMany(mappedBy = "topicId")
    private List<UserPermissionEntity> userPermissionsById;

}
