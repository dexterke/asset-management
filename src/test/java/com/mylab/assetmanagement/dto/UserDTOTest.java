package com.mylab.assetmanagement.dto;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDTOTest {
    @Test
    void userIdTest() {
        UserDTO testUserDTO = new UserDTO();
        testUserDTO.setId(1L);
        Long id = testUserDTO.getId();

        assertEquals(id.getClass(), Long.class);
        assertEquals(1, id);
    }

    @Test
    void userEmailTest() {
        UserDTO testUserDTO = new UserDTO();
        testUserDTO.setEmail("email@mail");
        String email = testUserDTO.getEmail();

        assertEquals(email.getClass(), String.class);
        assertThat(email).isNotNull();
    }

    @Test
    void userPasswordTest() {
        UserDTO testUserDTO = new UserDTO();
        testUserDTO.setPassword("1234567890");
        String password = testUserDTO.getPassword();

        assertEquals(password.getClass(), String.class);
        assertThat(password).isNotNull();
    }

    @Test
    void userNameTest() {
        UserDTO testUserDTO = new UserDTO();
        testUserDTO.setName("name");
        String userName = testUserDTO.getName();

        assertEquals(userName.getClass(), String.class);
        assertThat(userName).isNotNull();
    }

    @Test
    void userPhoneTest() {
        UserDTO testUserDTO = new UserDTO();
        testUserDTO.setPhone("+00");
        String phoneNo = testUserDTO.getPhone();

        assertEquals(phoneNo.getClass(), String.class);
        assertThat(phoneNo).isNotNull();
    }

    @Test
    void userHouseNoTest() {
        UserDTO testUserDTO = new UserDTO();
        testUserDTO.setHouseNo("no");
        String houseNo = testUserDTO.getHouseNo();

        assertEquals(houseNo.getClass(), String.class);
        assertThat(houseNo).isNotNull();
    }

    @Test
    void userStreetTest() {
        UserDTO testUserDTO = new UserDTO();
        testUserDTO.setStreet("street");

        String street = testUserDTO.getStreet();
        assertEquals(street.getClass(), String.class);
        assertThat(street).isNotNull();
    }

    @Test
    void userCityTest() {
        UserDTO testUserDTO = new UserDTO();
        testUserDTO.setCity("city");
        String city = testUserDTO.getCity();

        assertEquals(city.getClass(), String.class);
        assertThat(city).isNotNull();
    }

    @Test
    void userPostalCodeTest() {
        UserDTO testUserDTO = new UserDTO();
        testUserDTO.setPostalCode("code");
        String code = testUserDTO.getPostalCode();

        assertEquals(code.getClass(), String.class);
        assertThat(code).isNotNull();
    }

    @Test
    void userCountryTest() {
        UserDTO testUserDTO = new UserDTO();
        testUserDTO.setCountry("country");
        String country = testUserDTO.getCountry();

        assertEquals(country.getClass(), String.class);
        assertThat(country).isNotNull();

    }

}
