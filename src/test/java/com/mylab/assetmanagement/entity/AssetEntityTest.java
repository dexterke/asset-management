package com.mylab.assetmanagement.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class AssetEntityTest {

    private AssetEntity assetEntity;
    private AddressEntity addressEntity;
    private UserEntity userEntity;


    @BeforeEach
    void setUp() {
        assetEntity = new AssetEntity();
        assetEntity.setDescription("test");
        assetEntity.setTitle("test");
        assetEntity.setDescription("test");
        assetEntity.setId(999L);

        addressEntity = new AddressEntity();
        addressEntity.setId(999L);
        addressEntity.setType(AddressEntity.ADDRESS_TYPE.ASSET.ordinal());

        userEntity = new UserEntity();
        userEntity.setId(999L);

    }

    @Test
    void idTest() {
        assetEntity.getId();
        Object id = assetEntity.getId();
        assertThat(id).isNotNull().isInstanceOf(Long.class).isNotEqualTo(0L);
    }

    @Test
    void userEntityTest() {
        assetEntity.setUserEntity(userEntity);
        UserEntity user = assetEntity.getUserEntity();
        assertThat(user).usingRecursiveComparison().isEqualTo(userEntity);

    }

    @Test
    void addressEntityTest() {
        assetEntity.setAddressEntity(addressEntity);
        AddressEntity address = assetEntity.getAddressEntity();
        assertThat(address).usingRecursiveComparison().isEqualTo(address);
    }

}