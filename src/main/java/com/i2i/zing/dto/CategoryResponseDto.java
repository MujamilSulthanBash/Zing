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
 * This class represents the Category Response
 * Details like ID, name, Description and Set of Items
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {
    private String categoryId;
    private String name;
    private String description;
}
