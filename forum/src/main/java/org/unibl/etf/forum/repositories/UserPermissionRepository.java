package org.unibl.etf.forum.repositories;

import org.unibl.etf.forum.models.entities.UserPermissionEntity;
import org.unibl.etf.forum.models.entities.UserPermissionEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermissionEntity, UserPermissionEntityPK> {
    List<UserPermissionEntity> findByUserId(Integer userId);
    List<UserPermissionEntity> findByTopicId(Integer topicId);
    UserPermissionEntity findByUserIdAndTopicId(Integer userId, Integer topicId);

}