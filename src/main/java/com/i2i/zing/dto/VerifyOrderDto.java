package com.i2i.zing.dto;

import lombok.Data;

/**
 * <p>
 *     Used to fetch the orderId and otp from interface
 *     to verify.
 * </p>
 */
@Data
public class VerifyOrderDto {
        String orderId;
        String otp;
}
