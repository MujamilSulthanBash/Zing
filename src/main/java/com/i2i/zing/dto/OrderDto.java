package com.i2i.zing.dto;

import lombok.*;

/**
 * <p>
 *     Represents blueprint for the Order datatype to be displayed
 *     at the interface.
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private String orderId;
    private String cartId;
    private String paymentStatus;
}
