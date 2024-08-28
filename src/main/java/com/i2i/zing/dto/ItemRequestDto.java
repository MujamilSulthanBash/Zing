package com.i2i.zing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class ItemRequestDto {

    private String itemId;

    private String name;

    private int price;

    private String categoryId;

    private String categoryName;
}
