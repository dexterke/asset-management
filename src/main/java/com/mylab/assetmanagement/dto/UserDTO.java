package com.mylab.assetmanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    @Hidden
    private Long id;

    private String username;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String houseNo;
    private String street;
    private String city;
    private String postalCode;
    private String country;
    private List<String> roles;

}
