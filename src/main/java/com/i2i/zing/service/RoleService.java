package com.i2i.zing.service;

import com.i2i.zing.common.UserRole;
import com.i2i.zing.model.Role;

/**
 * <p>
 * This interface has abstract methods for
 * Role Operations
 * </p>
 */
public interface RoleService {
    /**
     * <p>
     * This method add the Roles to the Database
     * </p>
     */
    void addRoles();

    /**
     * <p>
     * This method Retrieve the Role by Role Name
     * </p>
     *
     * @param userRole - User Role as Object
     * @return - Role as Entity Object
     */
    Role retrieveRoleByName(UserRole userRole);
}
