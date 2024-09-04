package com.i2i.zing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * This class represents the Dto for
 * verify the Email Id after Signup
 * </p>
 */
@Data
@Builder
public class VerifyEmailDto {
    @NotBlank
    @Size(min = 10, max = 40, message = "Mail id should contain only 10 to 40 letters.")
    @Pattern(regexp = "^(.+)@(.+)$", message = "Given mailId is not in expected order pattern.")
    String email;

    @NotBlank
    @Size(min = 4, max = 4, message = "OTP should contain only 4 digits.")
    String otp;
}
