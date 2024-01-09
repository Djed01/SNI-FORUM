package org.unibl.etf.forum.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Data
public class UserPermissionEntityPK implements Serializable {
    @Column(name = "UserID")
    @Id
    private Integer userId;
    @Column(name = "TopicID")
    @Id
    private Integer topicId;

}
