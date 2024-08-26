package com.i2i.zing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
