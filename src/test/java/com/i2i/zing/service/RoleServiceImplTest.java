package com.i2i.zing.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.i2i.zing.common.UserRole;
import com.i2i.zing.model.Role;
import com.i2i.zing.repository.RoleRepository;
import com.i2i.zing.service.impl.RoleServiceImpl;


@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {
    @Mock
    RoleRepository roleRepository;
    
    @InjectMocks
    RoleServiceImpl roleService;
    
    List<Role> roles = new ArrayList<>();

    Role firstRole;

    Role secondRole;
    
    @BeforeEach
    void setUp() {
        firstRole = Role.builder()
                .roleId("1R")
                .roleName(UserRole.ADMIN)
                .build();
        secondRole = Role.builder()
                .roleId("2R")
                .roleName(UserRole.CUSTOMER)
                .build();
        roles.add(firstRole);
        roles.add(secondRole);
    }

    @Test
    void testAddRolesSuccess() {
        roleRepository.saveAll(roles);
        roleService.addRoles();
    }

    @Test
    void testAddRolesFailure() {
        when(roleRepository.existsByRoleName(UserRole.ADMIN)).thenReturn(true);
        roleService.addRoles();
    }

    @Test
    void testRetrieveRoleByNameSuccess() {
        when(roleRepository.findByRoleName(UserRole.ADMIN)).thenReturn(firstRole);
        Role retrievedRole = roleService.retrieveRoleByName(UserRole.ADMIN);
        assertEquals(retrievedRole, firstRole);
    }

    @Test
    void testRetrieveRoleByNameFailure() {
        when(roleRepository.findByRoleName(UserRole.ADMIN)).thenReturn(null);
        roleService.retrieveRoleByName(UserRole.ADMIN);
    }
}
