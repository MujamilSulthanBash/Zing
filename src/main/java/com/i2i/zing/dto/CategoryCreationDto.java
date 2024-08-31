package com.i2i.zing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreationDto {
    private String categoryId;

    @NotBlank (message = "Name must not be blank..")
    @Pattern(regexp = "[a-zA-Z\\s]+$")
    private String name;

    @NotBlank
    private String description;
}
