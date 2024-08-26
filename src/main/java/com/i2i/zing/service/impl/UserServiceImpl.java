package com.i2i.zing.service.impl;

import com.i2i.zing.model.User;
import com.i2i.zing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public void createAdmin() {
        User user = User.builder()
                .userName("ADMIN")
                .emailId("admin@1234")
                .contactNumber("123456789")
                .password(encoder.encode("Admin@123"))
                .location("chennai")
                .build();
        if( ! userRepository.existsByUserName("ADMIN")) {
            userRepository.save(user);
        }
    }

    public User retrieveByEmail(String emailId) {
        return userRepository.findByEmailIdIgnoreCase(emailId);
    }

    public boolean checkByEmailId(String emailId) {
        return userRepository.existsByEmailIdIgnoreCase(emailId);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getUserByLocation(String location) {
        return userRepository.findUsersByLocation(location);
    }
}
