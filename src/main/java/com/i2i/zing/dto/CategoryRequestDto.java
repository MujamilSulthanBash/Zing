package com.i2i.zing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *     This class represents the Dto for
 *     Category Details like Category ID , Name,
 *     Description of the Category and the list of items
 *     based on the Category
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDto {

    @NotBlank (message = "Name must not be blank..")
    @Pattern(regexp = "[a-zA-Z\\s]+$")
    private String name;

    @NotBlank (message = "Description must not be empty..")
    private String description;
}
