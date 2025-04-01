package com.mylab.assetmanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegistrationDTO extends UserDTO {

    @NotNull(message = "Email is mandatory")
    private String email;

    @NotNull(message = "Username is mandatory")
    private String username;

    @NotNull(message = "Password is mandatory")
    @Size(min = 8, max = 16, message = "Password length should be between 8 and 16")
    private String password;

    @NotNull(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Phone number is mandatory")
    private String phone;

    @Hidden
    private List<String> roles;
}
