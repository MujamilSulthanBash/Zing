package com.i2i.zing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *     This class represents the Dto for
 *     User details like id, customerName, emailId, contactNumber, location, roleId.
 * </p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;
    private String userName;
    private String emailId;
    private String contactNumber;
    private String location;
    private String password;
    private String roleId;
}
