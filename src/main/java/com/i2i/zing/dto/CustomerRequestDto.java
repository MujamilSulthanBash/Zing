package com.i2i.zing.dto;

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
 *     This class represents the Dto for
 *     User details like id, customerName, emailId, contactNumber, location, roleId.
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDto {

    @NotBlank(message = "Name must not be blank..")
    @Pattern(regexp = "[a-zA-Z\\s]+$")
    private String userName;

    @NotBlank
    @Pattern(regexp = "^(.+)@(.+)$")
    private String emailId;

    @NotBlank
    @Size(min = 10, max = 10, message = "Contact number must be 10 Digits..")
    private String contactNumber;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z\\s]+$")
    private String location;

    @NotBlank
    @Size(min = 6, max = 16, message = "Password must be contain 8 Characters..")
    private String password;
}
