package com.i2i.zing.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.*;

import com.i2i.zing.model.OrderAssign;

/**
 * <p>
 *     Represents blueprint for the DeliveryPerson datatype to be created,
 *     displayed ,removed and updated at the interface.
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPersonDto {
    @NotBlank
    private String deliveryPersonId;

    @NotBlank
    @Size(min = 5, max = 10, message = "Vehicle Number should contain 5 to 10 units.")
    private String vehicleNumber;

    @NotBlank
    @Size(min = 12, max = 12, message = "Aadhar Number should contain 5 to 12 units.")
    @Pattern(regexp = "^[1-9]{1}[0-9]{12}$", message = "Aadhar number should contain only digits.")
    private String aadharNumber;

    @NotBlank
    @Size(min = 15, max = 15, message = "License Number should contain 5 to 15 units.")
    @Pattern(regexp = "^[1-9]{1}[0-9]{15}$", message = "license number should contain only digits.")
    private String licenseNumber;

    @NotBlank
    private String userId;

    @NotBlank
    private String  userType;

    private Set<OrderAssign> orderAssign;
}
