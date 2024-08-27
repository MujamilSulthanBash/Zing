package com.i2i.zing.util;

import java.util.Random;

/**
 * <p>
 *     Generates otp for customers when they place the orders.
 * </p>
 */
public class OtpGenerator {
    public static char[] generateOtp() {
        String numbers = "0123456789";
        int len = 4;
        Random rndm_method = new Random();
        char[] otp = new char[len];

        for (int i = 0; i < len; i++) {
            otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        return otp;
    }
}
