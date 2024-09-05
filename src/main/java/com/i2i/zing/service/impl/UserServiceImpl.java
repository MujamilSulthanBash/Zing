package com.i2i.zing.service.impl;

import java.util.HashSet;
import java.util.Set;

import com.i2i.zing.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void createAdmin() {
        if (!userRepository.existsByUserNameAndIsDeletedFalse("ADMIN")) {
            User user = UserMapper.getAdminDetails();
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.retrieveRoleByName(UserRole.ADMIN));
            user.setRoles(roles);
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
