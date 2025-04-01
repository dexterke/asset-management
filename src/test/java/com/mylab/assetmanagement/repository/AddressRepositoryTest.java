package com.mylab.assetmanagement.repository;

import com.mylab.assetmanagement.entity.AddressEntity;
import com.mylab.assetmanagement.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class AddressRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;

    private UserEntity userEntity;
    private AddressEntity addressEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setName("test");
        userEntity.setUsername("test");
        userEntity.setPassword("test");
        userEntity.setPhone("test");
        userEntity.setEmail("test");

        addressEntity = new AddressEntity();
        addressEntity.setCity("test");
        addressEntity.setCountry("test");
        addressEntity.setStreet("test");
        addressEntity.setHouseNo("test");
        addressEntity.setPostalCode("test");
        addressEntity.setType(AddressEntity.ADDRESS_TYPE.PRIMARY.ordinal());
        addressEntity.setUserEntity(userEntity);
    }

    @Test
    void findAllByUserEntityIdTest() {
        userRepository.save(userEntity);
        addressRepository.save(addressEntity);
        List<AddressEntity> result = addressRepository.findAllByUserEntityId(userEntity.getId());
        assertThat(result).isNotNull();
    }

    @Test
    void findPrimaryTypeByUserEntityIdTest() {
        userRepository.save(userEntity);
        addressRepository.save(addressEntity);
        Optional<AddressEntity> result = addressRepository.findPrimaryTypeByUserEntityId(userEntity.getId());
        assertThat(result).isNotNull().isPresent();
        assertThat(result.get().getStreet()).isEqualTo("test");
    }

    @Test
    void findAssetTypeByUserEntityIdTest() {
        userRepository.save(userEntity);
        addressRepository.save(addressEntity);
        Optional<AddressEntity> result = addressRepository.findAssetTypeByUserEntityId(userEntity.getId());
        assertThat(result).isNotNull().isNotPresent();
    }
}