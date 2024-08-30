package com.i2i.zing.service.impl;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.DarkStoreRequestDto;
import com.i2i.zing.exception.EntityNotFoundException;
import com.i2i.zing.mapper.DarkStoreMapper;
import com.i2i.zing.model.DarkStore;
import com.i2i.zing.repository.DarkStoreRepository;
import com.i2i.zing.service.DarkStoreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DarkStoreServiceImpl implements DarkStoreService {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    DarkStoreRepository darkStoreRepository;

    public APIResponse addDarkStore(DarkStoreRequestDto darkStoreRequestDto) {
        APIResponse apiResponse = new APIResponse();
        DarkStore darkStore = DarkStoreMapper.convertDtoToEntity(darkStoreRequestDto);
        DarkStoreRequestDto darkStoreRequestDto1 = DarkStoreMapper.convertEntityToDto(darkStoreRepository.save(darkStore));
        if (null == darkStore.getLocation()) {
            logger.warn("An Error Occurred while Adding DarkStore with location : {}", darkStoreRequestDto.getLocation());
        }
        apiResponse.setData(darkStoreRequestDto1);
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
        if (result.isEmpty()) {
            logger.warn("Dark Store List is Empty..");
            throw new EntityNotFoundException("Dark Stores Not Found..");
        }
        apiResponse.setData(result);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse getDarkStoreById(String darkStoreId) {
        APIResponse apiResponse = new APIResponse();
        DarkStore darkStore = darkStoreRepository.findByIsDeletedFalseAndDarkStoreId(darkStoreId);
        if (null == darkStore) {
            logger.warn("DarkStore with Id : {} not found.", darkStoreId);
            throw new EntityNotFoundException("DarkStore with Id : " + darkStoreId + " not found.");
        }
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
