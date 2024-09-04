package com.i2i.zing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

/**
 * <p>
 * Used to fetch the orderId and otp from interface
 * to verify.
 * </p>
 */
@Data
public class VerifyOrderDto {
    @NotBlank
    @Size(min = 10, max = 40, message = "Id should contain 3 to 40 letters.")
    String orderId;

    @NotBlank
    @Size(min = 4, max = 4, message = "OTP should contain only 4 digits.")
    String otp;
}
