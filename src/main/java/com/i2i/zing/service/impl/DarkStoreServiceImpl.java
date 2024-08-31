package com.i2i.zing.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.i2i.zing.dto.DarkStoreDto;
import com.i2i.zing.model.User;
import com.i2i.zing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.DarkStoreRequestDto;
import com.i2i.zing.mapper.DarkStoreMapper;
import com.i2i.zing.model.DarkStore;
import com.i2i.zing.repository.DarkStoreRepository;
import com.i2i.zing.service.DarkStoreService;

@Service
public class DarkStoreServiceImpl implements DarkStoreService {

    @Autowired
    DarkStoreRepository darkStoreRepository;

    @Autowired
    UserService userService;

    @Override
    public APIResponse addDarkStore(DarkStoreDto darkStoreDto) {
        APIResponse apiResponse = new APIResponse();
        DarkStore darkStore = DarkStoreMapper.convertDtoToEntity(darkStoreDto);
        User user = DarkStoreMapper.convertDtoToUser(darkStoreDto);
        User savedUser = userService.createUser(user);
        darkStore.setUser(savedUser);
        darkStoreRepository.save(darkStore);
        apiResponse.setStatus(HttpStatus.CREATED.value());
        return apiResponse;
    }

    @Override
    public APIResponse getDarkStores() {
        APIResponse apiResponse = new APIResponse();
        List<DarkStoreRequestDto> result = new ArrayList<>();
        List<DarkStore> darkStores = darkStoreRepository.findByIsDeletedFalse();
        for (DarkStore darkStore : darkStores) {
            result.add(DarkStoreMapper.convertEntityToDto(darkStore));
        }
        apiResponse.setData(result);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse getDarkStoreById(String darkStoreId) {
        APIResponse apiResponse = new APIResponse();
        DarkStore darkStore = darkStoreRepository.findByIsDeletedFalseAndDarkStoreId(darkStoreId);
        DarkStoreMapper.convertEntityToDto(darkStore);
        apiResponse.setData(darkStore);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse deleteDarkStore(String darkStoreId) {
        APIResponse apiResponse = new APIResponse();
        DarkStore darkStore = darkStoreRepository.findByIsDeletedFalseAndDarkStoreId(darkStoreId);
        darkStore.setDeleted(true);
        darkStoreRepository.save(darkStore);
        apiResponse.setData("Dark store Deleted Successfully.." + darkStoreId);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

}
