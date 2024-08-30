package com.i2i.zing.dto;

import lombok.Data;

@Data
public class VerifyEmailDto {
    String email;
    String otp;
}
