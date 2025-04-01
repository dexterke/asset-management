package com.mylab.assetmanagement.repository;

import com.mylab.assetmanagement.entity.AddressEntity;
import com.mylab.assetmanagement.entity.AssetEntity;
import com.mylab.assetmanagement.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@ExtendWith(SpringExtension.class)
class AssetRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    AddressRepository addressRepository;

    private UserEntity userEntity;

    private AssetEntity assetEntity;

    private AddressEntity addressEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setName("test");
        userEntity.setPassword("test");
        userEntity.setUsername("test");
        userEntity.setPhone("test");
        userEntity.setEmail("test");

        addressEntity = new AddressEntity();
        addressEntity.setStreet("test");
        addressEntity.setHouseNo("test");
        addressEntity.setType(0);
        addressEntity.setCity("test");
        addressEntity.setPostalCode("test");
        addressEntity.setCountry("test");
        addressEntity.setUserEntity(userEntity);

        assetEntity = new AssetEntity();
        assetEntity.setTitle("test");
        assetEntity.setPrice(0D);
        assetEntity.setDescription("test");
        assetEntity.setUserEntity(userEntity);
        assetEntity.setAddressEntity(addressEntity);
    }

    @Test
    void findAllByUserEntityId() {
        userRepository.save(userEntity);
        addressRepository.save(addressEntity);
        assetRepository.save(assetEntity);
        List<AssetEntity> result =
                assetRepository.findAllByUserEntityId(userEntity.getId());
        assertEquals(1, result.size());
    }

}