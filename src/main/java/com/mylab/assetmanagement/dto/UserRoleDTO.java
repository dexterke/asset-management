package com.mylab.assetmanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRoleDTO {

    @Hidden
    private Long id;

    @NotNull(message = "Role Id is mandatory")
    private Long roleId;

    @NotNull(message = "User Id is mandatory")
    private Long userId;

    @NotNull(message = "Role info is mandatory")
    private String info;

    @Hidden
    private String roleName;

    @Hidden
    private String userName;
}
