package com.mylab.assetmanagement.repository;

import com.mylab.assetmanagement.entity.AddressEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
    @Query("SELECT p FROM AddressEntity p WHERE p.userEntity.id = :userId")
    List<AddressEntity> findAllByUserEntityId(@Param("userId") Long userId);

    @Query("SELECT p FROM AddressEntity p WHERE p.userEntity.id = :userId and p.type = 1")
    Optional<AddressEntity> findPrimaryTypeByUserEntityId(@Param("userId") Long userId);

    @Query("SELECT p FROM AddressEntity p WHERE p.userEntity.id = :userId and p.type = 0 and p.assetEntity.id is not null ")
    Optional<AddressEntity> findAssetTypeByUserEntityId(@Param("userId") Long userId);

}
