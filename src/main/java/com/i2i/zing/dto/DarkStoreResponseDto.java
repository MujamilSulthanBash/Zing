package com.i2i.zing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * This class represents the Dto for DarkStore
 * Response Details like Location
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DarkStoreResponseDto {
    private String location;
}
