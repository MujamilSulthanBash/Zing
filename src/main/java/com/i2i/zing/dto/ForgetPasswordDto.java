package com.i2i.zing.dto;

import lombok.*;

/**
 * <p>
 * This class represents the Dto for Forget Password
 * Details like email.
 * </p>
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ForgetPasswordDto {
    private String email;
}
