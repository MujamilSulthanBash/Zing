package com.i2i.zing.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreationDto {
    private String categoryId;
    private String name;
    private String description;
}
