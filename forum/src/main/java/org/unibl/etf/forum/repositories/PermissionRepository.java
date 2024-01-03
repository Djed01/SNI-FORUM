package org.unibl.etf.forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.forum.models.entities.PermissionEntity;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Integer> {

}
