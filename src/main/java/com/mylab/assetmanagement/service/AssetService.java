package com.mylab.assetmanagement.service;

import com.mylab.assetmanagement.dto.AssetDTO;

import java.util.List;

/*
 Factory design pattern: one interface can have multiple implementations.
 Depending on which implementing class creates the object
        ==>> that particular class functionality gets executed.
 */
public interface AssetService {

    AssetDTO getAsset(Long id);

    AssetDTO addAsset(AssetDTO assetDTO);

    List<AssetDTO> getAllAssets();

    List<AssetDTO> getAllAssetsOfUser(Long userId);

    AssetDTO updateAsset(AssetDTO assetDTO, Long id);

    AssetDTO updateAssetDescription(AssetDTO assetDTO, Long id);

    AssetDTO updateAssetPrice(AssetDTO assetDTO, Long id);

    AssetDTO updateAssetTitle(AssetDTO assetDTO, Long id);

    AssetDTO updateAssetAddress(AssetDTO assetDTO, Long id);

    AssetDTO updateAssetUserId(AssetDTO assetDTO, Long id);

    Long deleteAsset(Long id);

}
