package org.unibl.etf.forum.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPermissionEntityPK implements Serializable {
    private Integer permissionId;
    private Integer userId;
}
