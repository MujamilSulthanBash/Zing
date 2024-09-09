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
 * This class represents the Dto for Dark Store Manager
 * and DarkStore Details like Username, EmailId, contact Number,
 * Location and Password
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DarkStoreDto {

    @NotBlank(message = "Name must not be blank..")
    @Pattern(regexp = "[a-zA-Z\\s]+$")
    private String userName;

    @NotBlank
    @Size(min = 10, max = 40, message = "Mail id should contain only 10 to 40 letters.")
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Mail Id should contain @ and should not contain other special character.")
    private String emailId;

    @NotBlank
    @Size(min = 10, max = 10, message = "Contact number must be 10 Digits..")
    private String contactNumber;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z\\s]+$", message = "Location should contain only letters.")
    private String location;

    @NotBlank
    @Size(min = 6, max = 16, message = "Password should contain  6 to 16 characters.")
    private String password;
}
