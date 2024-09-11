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
 * This class represents the Dto for
 * OrderAssign details like assign id , delivery person id, delivery status.
 * </p>
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderAssignDto {
    @NotBlank
    @Size(min = 3, max = 40, message = "assignId should contain 3 to 40 characters.")
    private String assignId;

    @NotBlank
    @Size(min = 3, max = 40, message = "deliveryPersonId should contain 3 to 40 characters.")
    private String deliveryPersonId;

    @NotBlank
    @Size(min = 8, max = 9, message = "stockId should contain 8 to 9 letters.")
    @Pattern(regexp = "^(PENDING|ACCEPTED|PICKEDUP|DELIVERED)$", message = "paymentStatus should contain either of (PENDING,ACCEPTED,PICKEDUP,DELIVERED)")
    private String deliveryStatus;

    @NotBlank
    @Size(min = 3, max = 40, message = "orderId should contain 3 to 20 characters.")
    private String orderId;

    private String customerName;

    private String customerNumber;

    private Date timeOfUpdate;
}
