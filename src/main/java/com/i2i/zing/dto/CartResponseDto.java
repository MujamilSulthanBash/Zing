package com.i2i.zing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * This class represents the Dto for
 * Cart Details like Cart Id and Customer Id
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {
    private String cartId;
    private String customerId;
}
