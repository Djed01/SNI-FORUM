package org.unibl.etf.forum.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@jakarta.persistence.Table(name = "topic", schema = "forum", catalog = "")
public class TopicEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "ID")
    private Integer id;

    @Basic
    @Column(name = "Name")
    private String name;

}
