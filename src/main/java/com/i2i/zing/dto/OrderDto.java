package com.i2i.zing.dto;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * Represents blueprint for the Order datatype to be displayed
 * at the interface.
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

    @NotBlank
    @Size(min = 3, max = 6, message = "paymentMethod should contain 3 to 6 letters.")
    @Pattern(regexp = "^(UPI|CASHON)$", message = "paymentMethod should contain either UPI or CASHON.")
    private String paymentMethod;

    private Date timeOfOrder;
}
