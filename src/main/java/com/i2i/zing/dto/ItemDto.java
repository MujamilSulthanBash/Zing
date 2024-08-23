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

    private String itemId;

    private String name;

    private int price;

    private String categoryId;

    private String categoryName;
}
