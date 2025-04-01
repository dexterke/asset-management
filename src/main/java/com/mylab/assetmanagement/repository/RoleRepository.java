package com.mylab.assetmanagement.repository;

import com.mylab.assetmanagement.entity.RoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    @Query("SELECT p FROM RoleEntity p WHERE p.id = :roleId")
    Optional<RoleEntity> findRoleById(@Param("roleId") Long userId);

    @Query("SELECT p FROM RoleEntity p WHERE p.name = :roleName")
    Optional<RoleEntity> findRoleByName(@Param("roleName") String roleName);

    @Query("SELECT p FROM RoleEntity p")
    List<RoleEntity> findAllRoles();

}
