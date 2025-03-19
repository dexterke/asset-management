package com.mylab.assetmanagement.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddressEntityTest {
    private UserEntity testUserEntity;
    private AddressEntity testAddressEntity;

    @BeforeEach
    void setUp() {
        testUserEntity = new UserEntity();
        testUserEntity.setName("test");
        testUserEntity.setPassword("test");
        testUserEntity.setPhone("test");
        testUserEntity.setEmail("test");

        testAddressEntity = new AddressEntity();
        testAddressEntity.setStreet("test");
        testAddressEntity.setCity("test");
        testAddressEntity.setCountry("test");
        testAddressEntity.setHouseNo("test");
        testAddressEntity.setHouseNo("1");
        testAddressEntity.setPostalCode("test");
    }

    @Test
    void idTest() {
        testAddressEntity.setId(0L);
        Object id = testAddressEntity.getId();
        assertThat(id).isNotNull().isInstanceOf(Long.class);
    }

    @Test
    void userEntityTest() {
        testUserEntity.setId(1L);
        testAddressEntity.setUserEntity(testUserEntity);
        UserEntity userEntity = testAddressEntity.getUserEntity();
        assertThat(userEntity).isNotNull().usingRecursiveComparison().isEqualTo(testUserEntity);
    }
}