package com.i2i.zing.service;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.i2i.zing.common.UserRole;
import com.i2i.zing.model.Role;
import com.i2i.zing.model.User;
import com.i2i.zing.repository.UserRepository;
import com.i2i.zing.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    UserRepository userRepository;

    @Mock
    RoleService roleService;

    @InjectMocks
    UserServiceImpl userService;

    User user;

    Set<Role> roles = new HashSet<>();

    Role role;

    @BeforeEach
    void setUp() {
        role = Role.builder()
                .roleId("1R")
                .roleName(UserRole.ADMIN)
                .build();
        roles.add(role);
        user = User.builder()
                .userName("ADMIN")
                .emailId("mujamil.official.com")
                .contactNumber("9876543210")
                .password("1234567")
                .location("chennai")
                .roles(roles)
                .build();
    }

    @Test
    void testCreateAdminSuccess() {
        when(userRepository.existsByUserNameAndIsDeletedFalse("ADMIN")).thenReturn(true);
        userRepository.save(user);
        roleService.retrieveRoleByName(UserRole.ADMIN);
        userService.createAdmin();
    }

    @Test
    void testRetrieveByEmail() {
        when(userRepository.findByEmailIdIgnoreCase(user.getEmailId())).thenReturn(user);
        User retrievedUser = userService.retrieveByEmail(user.getEmailId());

        assertEquals(retrievedUser.getEmailId(), user.getEmailId());
    }

    @Test
    void testCheckByEmailId() {
        when(userRepository.existsByEmailIdIgnoreCase(user.getEmailId())).thenReturn(true);
        boolean validEmail = userService.checkByEmailId(user.getEmailId());

        assertTrue(validEmail);
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(user)).thenReturn(user);
        User createdUser = userService.createUser(user);

        assertEquals(createdUser, user);
    }
}
