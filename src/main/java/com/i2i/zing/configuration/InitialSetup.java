package com.i2i.zing.configuration;

import com.i2i.zing.service.RoleService;
import com.i2i.zing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

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
