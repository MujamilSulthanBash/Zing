package com.i2i.zing.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * This class represents the Dto for Forget Password Validation
 * Details like email, otp and New Password
 * </p>
 */
@Builder
@Setter
@Getter
public class ForgetPasswordValidator {
    private String email;
    private String otp;
    private String newPassword;
}
