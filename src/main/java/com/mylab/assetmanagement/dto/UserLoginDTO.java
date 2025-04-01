package com.mylab.assetmanagement.dto;

import io.swagger.v3.oas.annotations.Hidden;

import java.util.List;

public class UserLoginDTO extends UserDTO {

    @Hidden
    private String name;

    @Hidden
    private String phone;

    @Hidden
    private String houseNo;

    @Hidden
    private String street;

    @Hidden
    private String city;

    @Hidden
    private String postalCode;

    @Hidden
    private String country;

    @Hidden
    private String email;

    @Hidden
    private List<String> roles;

}

