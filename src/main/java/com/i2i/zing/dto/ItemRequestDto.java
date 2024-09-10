package com.i2i.zing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * Represents blueprint for the item datatype to be created,
 * displayed ,removed and updated at the interface.
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {
    @NotBlank
    @Size(min = 4, max = 15, message = "name should contain 4 to 15 letters.")
    @Pattern(regexp = "^[a-zA-Z]+([ ][a-zA-Z]+)*$", message = "Name should contain only letters.")
    private String name;

    @NotNull
    private double price;

    @NotBlank
    @Size(min = 3, max = 40, message = "categoryId should contain 3 to 20 characters.")
    private String categoryId;
}
