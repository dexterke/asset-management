package com.mylab.assetmanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    @Hidden
    private Long id;
    @NotNull(message = "User email is mandatory")
    private String email;
    @NotNull(message = "Password is mandatory")
    @Size(min = 8, max = 16, message = "Password length should be between 8 and 16 characters")
    private String password;

    private String name;
    private String phone;

    private String houseNo;
    private String street;
    private String city;
    private String postalCode;
    private String country;
}
