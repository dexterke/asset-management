package com.mylab.assetmanagement.dto;

import com.mylab.assetmanagement.entity.AddressEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssetDTOTest {

    @Test
    void idTest() {
        AssetDTO testAssetDTO = new AssetDTO();
        testAssetDTO.setId(99L);

        assertEquals(99L, testAssetDTO.getId());
        assertEquals(testAssetDTO.getId().getClass(), Long.class);
    }

    @Test
    void titleTest() {
        AssetDTO testAssetDTO = new AssetDTO();
        testAssetDTO.setTitle("test");

        assertEquals("test", testAssetDTO.getTitle());
        assertEquals(testAssetDTO.getTitle().getClass(), String.class);
    }

    @Test
    void descriptionTest() {
        AssetDTO testAssetDTO = new AssetDTO();
        testAssetDTO.setDescription("test");

        assertEquals("test", testAssetDTO.getDescription());
        assertEquals(testAssetDTO.getDescription().getClass(), String.class);
    }

    @Test
    void priceTest() {
        AssetDTO testAssetDTO = new AssetDTO();
        testAssetDTO.setPrice(99D);

        assertEquals(99D, testAssetDTO.getPrice());
        assertEquals(testAssetDTO.getPrice().getClass(), Double.class);
    }

    @Test
    void userIdTest() {
        AssetDTO testAssetDTO = new AssetDTO();
        testAssetDTO.setUserId(88L);

        assertEquals(88L, testAssetDTO.getUserId());
        assertEquals(testAssetDTO.getUserId().getClass(), Long.class);
    }

}