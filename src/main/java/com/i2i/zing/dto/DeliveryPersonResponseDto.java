package com.i2i.zing.dto;

import lombok.*;

/**
 * <p>
 * This class represents the Dto for DeliveryPerson
 * and DeliveryPerson Details like Username, EmailId, contact Number,
 * Location.
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPersonResponseDto {
    private String deliveryPersonId;
    private String userName;
    private String emailId;
    private String contactNumber;
    private String location;
}
