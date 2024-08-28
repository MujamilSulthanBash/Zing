package com.i2i.zing.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {
    private String customerId;
    private String paymentMethod;
    private Double totalAmount;
}
