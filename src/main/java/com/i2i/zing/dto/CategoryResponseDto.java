package com.i2i.zing.dto;

import com.i2i.zing.model.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {
    private String categoryId;
    private String name;
    private String description;
    private Set<Item> items;
}
