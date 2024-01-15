package org.unibl.etf.forum.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "comment", schema = "forum", catalog = "")
public class CommentEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Integer id;

    @Basic
    @Column(name = "TopicID")
    private Integer topicId;

    @Basic
    @Column(name = "UserID")
    private Integer userId;

    @Basic
    @Column(name = "Content")
    private String content;

    @Basic
    @Column(name = "Time")
    private Timestamp time;

    @Basic
    @Column(name = "Status")
    private Boolean status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "ID", nullable = false, insertable=false, updatable=false)
    private UserEntity user;

}
