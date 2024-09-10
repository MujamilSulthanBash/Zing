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
 * This class represents the Dto for
 * Delivery Person Signup Credentials like Username,
 * password, location, Contact Number, vehicle Number
 * Aadhaar Number and License Number
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPersonRequestDto {

    @NotBlank(message = "Name must not be blank..")
    @Pattern(regexp = "[a-zA-Z\\s]+$")
    private String userName;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Mail Id should contain @ and should not contain other special character.")
    private String emailId;

    @NotBlank
    @Size(min = 10, max = 10, message = "Contact number must be 10 Digits..")
    @Pattern(regexp = "^[1-9]{1}[0-9]{9}$", message = "Contact Number should contain only numbers.")
    private String contactNumber;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z\\s]+$")
    private String location;

    @NotBlank
    @Size(min = 6, max = 16, message = "Password must be contain 6 Characters..")
    private String password;

    @NotBlank
    @Size(min = 6, max = 16, message = "Vehicle Number must be contain 6 Characters..")
    private String vehicleNumber;

    @NotBlank
    @Size(min = 12, max = 12, message = "Invalid Aadhaar Number..")
    private String aadharNumber;

    @NotBlank
    private String licenseNumber;
}
