package com.i2i.zing.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *     This class represents the Dto for
 *     Role details like Role ID, Role name, set of {@link UserDto} details
 * </p>
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private String roleId;
    private String roleName;
    private Set<UserDto> users;
}
