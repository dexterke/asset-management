package com.mylab.assetmanagement.repository;

import com.mylab.assetmanagement.entity.UserEntity;
import com.mylab.assetmanagement.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findOneByEmailAndPassword(String email, String password);

    Optional<UserEntity> findOneByUsernameAndPassword(String email, String password);

    Optional<UserEntity> findOneByEmail(String email);

    Optional<UserEntity> findOneByUsername(String username);

    @Query("SELECT p FROM UserRoleEntity p WHERE p.userEntity.id = :userId")
    List<UserRoleEntity> getRoles(@Param("userId") Long userId);

}
