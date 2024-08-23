package com.i2i.zing.service.impl;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.CustomerRequestDto;
import com.i2i.zing.mapper.UserMapper;
import com.i2i.zing.model.User;
import com.i2i.zing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public APIResponse signUp(CustomerRequestDto customerRequestDto) {
        APIResponse apiResponse = new APIResponse();
        User user = userRepository.save(UserMapper.userEntity(customerRequestDto));
        apiResponse.setData(user);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(user);
        return apiResponse;
    }

}
