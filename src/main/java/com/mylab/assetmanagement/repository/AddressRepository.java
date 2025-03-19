package com.mylab.assetmanagement.repository;

import com.mylab.assetmanagement.entity.AddressEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
    @Query("SELECT p FROM AddressEntity p WHERE p.userEntity.id = :userId")
    Optional<AddressEntity> findAllByUserEntityId(@Param("userId") Long userId);
}
