package org.unibl.etf.forum.repositories;

import org.unibl.etf.forum.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);
    List<UserEntity> findAllByStatusFalse();
}
