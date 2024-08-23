package com.i2i.zing.dto;

import com.i2i.zing.model.Stock;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

/**
 * <p>
 *     Represents blueprint for the DarkStore datatype to be created,
 *     displayed ,removed and updated at the interface.
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DarkStoreDto{
    @NotBlank
    private String darkStoreId;

    @NotBlank
    @Size(min = 5, max = 15, message = "Location should contain 5 to 15 letters")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Category name should contain only letters.")
    private String location;
}
