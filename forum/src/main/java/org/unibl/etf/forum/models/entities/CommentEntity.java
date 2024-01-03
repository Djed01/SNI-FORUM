package org.unibl.etf.forum.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@Entity
@jakarta.persistence.Table(name = "comment", schema = "forum", catalog = "")
public class CommentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "ID")
    private Integer id;

    @Basic
    @Column(name = "TopicID")
    private Integer topicId;

    @Basic
    @Column(name = "USER_ID")
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

}
