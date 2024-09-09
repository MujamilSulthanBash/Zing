package com.i2i.zing.dto;

import jakarta.validation.constraints.Email;
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
 * This class represents the Dto for User Login
 * Request Details like Email ID and Password
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDto {
    @NotBlank
    @Size(min = 10, max = 40, message = "Mail id should contain only 10 to 40 letters.")
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Mail Id should contain @ and should not contain other special character.")
    private String emailId;

    @NotBlank
    @Size(min = 4, max = 15, message = "Password should contain  4 to 15 characters.")
    private String password;
}