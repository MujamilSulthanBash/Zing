package com.i2i.zing.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.i2i.zing.service.RoleService;
import com.i2i.zing.service.UserService;

/**
 * <p>
 *     This class represent the initial setup need to be done while running the application
 *     like roles to be stored in the database and admin account will be create.
 * </p>
 */
@Configuration
public class InitialSetup implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        roleService.addRoles();
        userService.createAdmin();
    }
}
