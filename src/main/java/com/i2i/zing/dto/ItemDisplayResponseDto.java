package com.i2i.zing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDisplayResponseDto {
    private String name;
    private int price;
    private String categoryName;
    private int quantity;
    private String status;
}
