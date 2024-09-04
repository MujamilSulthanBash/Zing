package com.i2i.zing.util;

import java.util.Random;

/**
 * <p>
 * Generates otp for customers when they place the orders.
 * </p>
 */
public class OtpGenerator {

    /**
     * This method is responsible for generate random 4 digits number.
     *
     * @return four digits number.
     */
    public static char[] generateOtp() {
        String numbers = "0123456789";
        int len = 4;
        Random random = new Random();
        char[] otp = new char[len];

        for (int i = 0; i < len; i++) {
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        return otp;
    }

}
