package com.mylab.assetmanagement.repository;

import com.mylab.assetmanagement.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRoleRepository extends CrudRepository<UserRoleEntity, Long> {
    @Query("SELECT p FROM UserRoleEntity p WHERE p.userEntity.id = :userId")
    List<UserRoleEntity> findAllByUserEntityId(@Param("userId") Long userId);

}