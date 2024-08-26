package com.i2i.zing.configuration;

import com.i2i.zing.service.impl.RoleServiceImpl;
import com.i2i.zing.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitialSetup implements CommandLineRunner {

    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public void run(String... args) throws Exception {
        roleServiceImpl.addRoles();
        userServiceImpl.createAdmin();
    }
}
