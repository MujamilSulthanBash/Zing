package com.i2i.zing.service.impl;

import com.i2i.zing.model.User;
import com.i2i.zing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createAdmin() {
        User user = User.builder()
                .userName("ADMIN")
                .emailId("admin@1234")
                .contactNumber("123456789")
                .password("Admin@123")
                .location("chennai")
                .build();
        if( ! userRepository.existsByUserName("ADMIN")) {
            userRepository.save(user);
        }
    }

}
