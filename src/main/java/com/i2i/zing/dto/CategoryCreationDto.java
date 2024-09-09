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
 * This class represents the Dto for Category
 * Creation Details like Category Id, Name and Description
 * of the Item
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreationDto {
    private String categoryId;

    @NotBlank (message = "Name must not be blank..")
    @Pattern(regexp = "[a-zA-Z\\s]+$", message = "Category name should contain only letters.")
    private String name;

    @NotBlank
    private String description;
}
