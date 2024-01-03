package org.unibl.etf.forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.forum.models.entities.UserPermissionEntity;
import org.unibl.etf.forum.models.entities.UserPermissionEntityPK;

public interface UserPermissionRepository extends JpaRepository<UserPermissionEntity, UserPermissionEntityPK> {

}
