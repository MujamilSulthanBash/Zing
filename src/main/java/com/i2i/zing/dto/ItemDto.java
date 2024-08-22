package com.i2i.zing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * <p>
 *     Represents blueprint for the item datatype to be created,
 *     displayed ,removed and updated at the interface.
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    @NotBlank
    private String itemId;

    @NotBlank
    @Size(min = 3, max = 15, message = "Name should contain 3 to 15 letters.")
    @Pattern(regexp = "^[a-zA-Z]+([ ][a-zA-Z]+)*$", message = "Name should contain only letters.")
    private String name;

    @NotBlank
    @Size(min = 5, max = 500, message = "Price should contain 5 to 100 number.")
    @Pattern(regexp = "^[1-9]{1}[0-9]{2}$", message = "Price should contain only digits.")
    private int price;

    @NotBlank
    private String categoryId;

    @NotBlank
    @Size(min = 3, max = 20, message = "Name should contain 3 to 20 letters.")
    @Pattern(regexp = "^[a-zA-Z]+([ ][a-zA-Z]+)*$", message = "Category name should contain only letters.")
    private String categoryName;
}
