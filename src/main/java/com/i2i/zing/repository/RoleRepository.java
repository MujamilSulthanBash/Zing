package com.i2i.zing.repository;

import com.i2i.zing.common.UserRole;
import com.i2i.zing.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {

    boolean existsByRoleName(UserRole userRole);

    Role findByRoleName(UserRole userRole);

}
