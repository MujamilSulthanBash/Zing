package com.i2i.zing.dto;

import java.util.Set;

import lombok.*;

import com.i2i.zing.model.Item;

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
public class CategoryDto {
    private String categoryId;
    private String Name;
    private String description;
    private Set<Item> items;
}
