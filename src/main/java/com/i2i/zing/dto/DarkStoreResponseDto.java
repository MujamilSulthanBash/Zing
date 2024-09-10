package com.i2i.zing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * This class represents the Dto for Dark Store Manager
 * and DarkStore Details like Username, EmailId, contact Number,
 * Location.
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DarkStoreResponseDto {
    private String darkStoreId;
    private String userName;
    private String emailId;
    private String contactNumber;
    private String location;
}
