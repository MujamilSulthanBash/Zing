package com.i2i.zing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemUpdateDto {
    private String itemId;

    @NotBlank
    @Size(min = 4, max = 15, message = "name should contain 4 to 15 letters.")
    @Pattern(regexp = "^[a-zA-Z]+([ ][a-zA-Z]+)*$", message = "Name should contain only letters.")
    private String name;

    @NotBlank
    @Size(min = 10 , max = 500, message = "price should contain 10 to 500 .")
    @Pattern(regexp = "^[1-5]{1}[0-9]{2}$", message = "price should contain only numbers.")
    private double price;
    private String categoryName;
}
