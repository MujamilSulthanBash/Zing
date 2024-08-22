package com.i2i.zing.dto;

import lombok.*;

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
