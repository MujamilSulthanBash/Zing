package com.i2i.zing.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class CategoryRequestDto {
    private String name;
    private String description;
}
