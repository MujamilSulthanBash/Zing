package com.i2i.zing.service.impl;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.DarkStoreDto;
import com.i2i.zing.mapper.DarkStoreMapper;
import com.i2i.zing.model.DarkStore;
import com.i2i.zing.repository.DarkStoreRepository;
import com.i2i.zing.service.DarkStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DarkStoreServiceImpl implements DarkStoreService {

    @Autowired
    DarkStoreRepository darkStoreRepository;

    public APIResponse addDarkStore(DarkStoreDto darkStoreDto) {
        APIResponse apiResponse = new APIResponse();
        DarkStore darkStore = DarkStoreMapper.convertDtoToEntity(darkStoreDto);
        DarkStoreMapper.convertEntityToDto(darkStoreRepository.save(darkStore));
        apiResponse.setData(darkStore);
        apiResponse.setStatus(HttpStatus.CREATED.value());
        return apiResponse;
    }

    @Override
    public APIResponse getDarkStores() {
        APIResponse apiResponse = new APIResponse();
        List<DarkStoreDto> result = new ArrayList<>();
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
