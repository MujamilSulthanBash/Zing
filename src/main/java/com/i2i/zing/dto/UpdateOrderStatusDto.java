package com.i2i.zing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * Used to fetch the orderId and otp from interface
 * to verify.
 * </p>
 */
@Builder
@Data
public class UpdateOrderStatusDto {
    @NotBlank
    @Size(min = 10, max = 40, message = "Id should contain 3 to 40 letters.")
    String orderId;

    @NotBlank
    @Size(min = 7, max = 8, message = "Delivery status should be 'PENDING/ACCEPTED'.")
    String status;
}
