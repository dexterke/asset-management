package com.mylab.assetmanagement.dto;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetDTO {
    @Hidden
    private Long id;

    @NotNull(message = "Title is mandatory")
    private String title;

    @NotNull(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Price is mandatory")
    private Double price;

    @NotNull(message = "House no. is mandatory")
    private String houseNo;

     @NotNull(message = "Address is mandatory")
    private String street;

    @NotNull(message = "Address is mandatory")
    private String city;

    @NotNull(message = "Address is mandatory")
    private String postalCode;

    @NotNull(message = "Address is mandatory")
    private String country;

    private Long userId;

}
