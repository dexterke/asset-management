package com.mylab.assetmanagement.repository;

import com.mylab.assetmanagement.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRoleRepository extends CrudRepository<UserRoleEntity, Long> {
    @Query("SELECT p FROM UserRoleEntity p WHERE p.userEntity.id = :userId")
    List<UserRoleEntity> findAllByUserEntityId(@Param("userId") Long userId);

    @Query("SELECT p FROM UserRoleEntity p WHERE p.userEntity.id = :userId AND p.roleEntity.id = :roleId")
    Optional<UserRoleEntity> findByUserEntityIdAndRoleEntityId(@Param("userId") Long userId, @Param("roleId") Long roleId);

}