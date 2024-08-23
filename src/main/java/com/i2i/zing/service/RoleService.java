package com.i2i.zing.service;

import com.i2i.zing.common.UserRole;
import com.i2i.zing.model.Role;
import com.i2i.zing.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;



    public void addRoles() {
        List<Role> roles = new ArrayList<>();
        if (! roleRepository.existsByRoleName(UserRole.ADMIN)) {
            roles.add(Role.builder().roleName(UserRole.ADMIN).build());
            roles.add(Role.builder().roleName(UserRole.CUSTOMER).build());
            roles.add(Role.builder().roleName(UserRole.DELIVERYPERSON).build());
            roleRepository.saveAll(roles);
        }
    }

}
