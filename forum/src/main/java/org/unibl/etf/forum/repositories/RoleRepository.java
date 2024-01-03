package org.unibl.etf.forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.forum.models.entities.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    // Add any custom queries if required
}