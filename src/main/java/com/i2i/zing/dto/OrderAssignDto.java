package com.i2i.zing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *     This class represents the Dto for
 *     OrderAssign details like assign id , delivery person id, delivery status.
 * </p>
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderAssignDto {
    private String assignId;
    private String deliveryPersonId;
    private String deliveryStatus;
}
