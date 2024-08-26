package com.i2i.zing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.i2i.zing.common.UserRole;
import com.i2i.zing.model.Role;

/**
 * <p>
 *     This interface has Abstract methods for Role Operations
 *     like exists By name and find by role name.
 * </p>
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    /**
     * <p>
     *     This method return a boolean if the Role name already
     *     exist or not
     * </p>
     * @param userRole - userRole as Object
     * @return boolean value true of false if the role name exist
     */
    boolean existsByRoleName(UserRole userRole);

    /**
     * <p>
     *     This method return the specific Role by Role name
     * </p>
     * @param userRole - User Role as Object
     * @return Role as Entity Object
     */
    Role findByRoleName(UserRole userRole);

}
