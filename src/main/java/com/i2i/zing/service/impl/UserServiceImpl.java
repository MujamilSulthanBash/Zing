package com.i2i.zing.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.UserRole;
import com.i2i.zing.model.Role;
import com.i2i.zing.model.User;
import com.i2i.zing.repository.UserRepository;
import com.i2i.zing.service.RoleService;
import com.i2i.zing.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public void createAdmin() {
        User user = User.builder()
                .userName("ADMIN")
                .emailId("admin@gmail.com")
                .contactNumber("123456789")
                .password(encoder.encode(System.getenv("ADMIN_PASSWORD")))
                .location("chennai")
                .build();
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.retrieveRoleByName(UserRole.ADMIN));
        user.setRoles(roles);
        if( ! userRepository.existsByUserNameAndIsDeletedFalse("ADMIN")) {
            userRepository.save(user);
        }
    }

    @Override
    public User retrieveByEmail(String emailId) {
        return userRepository.findByEmailIdIgnoreCase(emailId);
    }

    @Override
    public boolean checkByEmailId(String emailId) {
        return userRepository.existsByEmailIdIgnoreCase(emailId);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

}
