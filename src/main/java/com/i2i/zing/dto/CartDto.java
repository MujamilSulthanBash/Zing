package com.i2i.zing.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *     This class represents the Dto for
 *     Cart details like Cart ID, Customer ID
 *     and Set of {@link CartItemDto}.
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private String cartId;
    private String customerId;
    private String paymentMethod;
    private Double totalAmount;
}
