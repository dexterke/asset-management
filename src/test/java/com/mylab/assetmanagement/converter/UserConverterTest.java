package com.mylab.assetmanagement.converter;

import com.mylab.assetmanagement.dto.UserDTO;
import com.mylab.assetmanagement.entity.AddressEntity;
import com.mylab.assetmanagement.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserConverterTest {
    UserConverter userConverter;
    private UserDTO testUserDTO;
    private UserEntity testUserEntity;
    private AddressEntity testAddressEntity;


    @BeforeEach
    void setUp() {
        userConverter = new UserConverter();

        testUserDTO = new UserDTO();
        testUserDTO.setId(1L);
        testUserDTO.setName("name");
        testUserDTO.setPassword("1234567890");
        testUserDTO.setEmail("email@mail");
        testUserDTO.setCity("city");
        testUserDTO.setHouseNo("no");
        testUserDTO.setCountry("country");
        testUserDTO.setStreet("street");
        testUserDTO.setPhone("+00");
        testUserDTO.setPostalCode("code");

        testUserEntity = new UserEntity();
        testUserEntity.setId(testUserDTO.getId());
        testUserEntity.setName(testUserDTO.getName());
        testUserEntity.setPassword(testUserDTO.getPassword());
        testUserEntity.setEmail(testUserDTO.getPassword());
        testUserEntity.setPhone(testUserDTO.getPhone());

        testAddressEntity = new AddressEntity();
        testAddressEntity.setStreet("test");
        testAddressEntity.setCity("test");
        testAddressEntity.setCountry("test");
        testAddressEntity.setHouseNo("test");
        testAddressEntity.setHouseNo("1");
        testAddressEntity.setPostalCode("test");
    }

    @Test
    void convertDTOtoEntityTest() {
        UserEntity userEntity = userConverter.convertDTOtoEntity(testUserDTO);
        assertThat(userEntity).isNotNull()
                .usingRecursiveComparison().ignoringFields("id")
                .isEqualTo(testUserDTO);
    }

    @Test
    void convertEntityToDtoTest() {
        UserDTO userDTO = userConverter.convertEntityToDTO(testUserEntity);
        assertThat(userDTO).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("country", "city", "street", "postalCode", "houseNo")
                .isEqualTo(testUserEntity);
    }

    @Test
    void setUserDTOaddressTest() {
        UserDTO userDTO = userConverter.setUserDTOaddress(testUserDTO, testAddressEntity);
        assertThat(userDTO).isNotNull().usingRecursiveComparison()
                .ignoringFields("id", "password", "phone", "name", "email")
                .isEqualTo(testAddressEntity);
    }
}