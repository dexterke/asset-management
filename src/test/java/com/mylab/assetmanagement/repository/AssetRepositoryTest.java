package com.mylab.assetmanagement.repository;

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

    private UserEntity userEntity;

    private AssetEntity assetEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setName("test");
        userEntity.setPassword("test");
        userEntity.setPhone("test");
        userEntity.setEmail("test");

        assetEntity = new AssetEntity();
        assetEntity.setAddress("test");
        assetEntity.setTitle("test");
        assetEntity.setPrice(0D);
        assetEntity.setDescription("test");
        assetEntity.setUserEntity(userEntity);
    }

    @Test
    void findAllByUserEntityId() {
        userRepository.save(userEntity);
        assetRepository.save(assetEntity);
        List<AssetEntity> result =
                assetRepository.findAllByUserEntityId(999L);
        assertEquals(0, result.size());
    }

}