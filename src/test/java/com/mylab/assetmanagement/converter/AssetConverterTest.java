package com.mylab.assetmanagement.converter;

import com.mylab.assetmanagement.dto.AssetDTO;
import com.mylab.assetmanagement.entity.AssetEntity;
import com.mylab.assetmanagement.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AssetConverterTest {
    AssetConverter assetConverter;
    AssetDTO testAssetDTO;
    AssetEntity testAssetEntity;
    UserEntity testUserEntity;

    @BeforeEach
    void setUp() {
        assetConverter = new AssetConverter();

        testAssetDTO = new AssetDTO();
        testAssetDTO.setTitle("test");
        testAssetDTO.setAddress("test");
        testAssetDTO.setPrice(0D);
        testAssetDTO.setDescription("test");
        testAssetDTO.setTitle("test");
        testAssetDTO.setUserId(1L);

        testUserEntity = new UserEntity();
        testUserEntity.setId(1L);

        testAssetEntity = new AssetEntity();
        testAssetEntity.setTitle(testAssetDTO.getTitle());
        testAssetEntity.setAddress(testAssetDTO.getAddress());
        testAssetEntity.setPrice(testAssetDTO.getPrice());
        testAssetEntity.setDescription(testAssetDTO.getDescription());
        testAssetEntity.setTitle(testAssetDTO.getTitle());
        testAssetEntity.setUserEntity(testUserEntity);
    }

    @Test
    void convertDTOtoEntityTest() {
        AssetEntity assetEntity = assetConverter.convertDTOtoEntity(testAssetDTO);
        assertThat(assetEntity).isNotNull()
                .usingRecursiveComparison().ignoringFields("id", "userEntity")
                .isEqualTo(testAssetDTO);
    }

    @Test
    void convertEntityToDtoTest() {
        AssetDTO assetDTO = assetConverter.convertEntityToDTO(testAssetEntity);
        assertThat(assetDTO).usingRecursiveComparison().ignoringFields("userId")
                .isEqualTo(testAssetEntity);
    }

}