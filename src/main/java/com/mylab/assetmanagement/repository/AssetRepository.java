package com.mylab.assetmanagement.repository;

import com.mylab.assetmanagement.entity.AssetEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssetRepository extends CrudRepository<AssetEntity, Long> {
    @Query("SELECT p FROM AssetEntity p WHERE p.userEntity.id = :userId")
    List<AssetEntity> findAllByUserEntityId(@Param("userId") Long userId);

}
