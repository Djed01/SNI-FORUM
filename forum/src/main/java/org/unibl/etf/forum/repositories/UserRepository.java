package org.unibl.etf.forum.repositories;

import org.unibl.etf.forum.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    // Add any custom queries here if necessary, e.g., findByUsername
    UserEntity findByUsername(String username);
}
