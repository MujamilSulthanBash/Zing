package com.i2i.zing.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemCreationDto {
    private String itemId;
    private String name;
    private double price;
    private String categoryName;
}
