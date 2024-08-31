package com.i2i.zing.dto;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    @Size(min = 3, max = 40, message = "cartId should contain 3 to 40 characters.")
    private String cartId;

    @NotBlank
    @Size(min = 4, max = 6, message = "paymentStatus should contain 4 to 6 letters.")
    @Pattern(regexp = "^(PAID|UNPAID)$",message = "paymentStatus should contain either PAID or UNPAID.")
    private String paymentStatus;

    @NotBlank
    @Size(min = 3, max = 6, message = "paymentMethod should contain 3 to 6 letters.")
    @Pattern(regexp = "^(UPI|CASHON)$",message = "paymentMethod should contain either UPI or CASHON.")
    private String paymentMethod;

    private Date timeOfOrder;
}
