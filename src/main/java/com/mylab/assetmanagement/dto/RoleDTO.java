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
public class RoleDTO {

    @Hidden
    private Long id;

    @NotNull(message = "Role name is mandatory")
    private String name;

    @NotNull(message = "Description is mandatory")
    private String description;

}