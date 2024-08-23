package com.i2i.zing.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPersonRequestDto {
    private String userName;
    private String emailId;
    private String contactNumber;
    private String location;
    private String password;
    private String vehicleNumber;
    private String aadharNumber;
    private String licenseNumber;
}
