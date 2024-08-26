package com.i2i.zing.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.i2i.zing.model.User;
import com.i2i.zing.repository.UserRepository;
import com.i2i.zing.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
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

    @Override
    public List<User> getUserByLocation(String location) {
        return userRepository.findUsersByLocation(location);
    }
}
