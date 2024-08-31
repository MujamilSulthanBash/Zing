package com.i2i.zing.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.UserRole;
import com.i2i.zing.model.Role;
import com.i2i.zing.repository.RoleRepository;
import com.i2i.zing.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void addRoles() {
        List<Role> roles = new ArrayList<>();
        if (! roleRepository.existsByRoleName(UserRole.ADMIN)) {
            roles.add(Role.builder().roleName(UserRole.ADMIN).build());
            roles.add(Role.builder().roleName(UserRole.CUSTOMER).build());
            roles.add(Role.builder().roleName(UserRole.DELIVERYPERSON).build());
            roles.add(Role.builder().roleName(UserRole.MANAGER).build());
            roleRepository.saveAll(roles);
        }
    }

    @Override
    public Role retrieveRoleByName(UserRole userRole) {
        return roleRepository.findByRoleName(userRole);
    }

}
