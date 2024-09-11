package com.i2i.zing.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import com.i2i.zing.dto.DarkStoreResponseDto;
import com.i2i.zing.dto.ItemUpdateDto;
import com.i2i.zing.model.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.common.UserRole;
import com.i2i.zing.dto.DarkStoreDto;
import com.i2i.zing.dto.DarkStoreRequestDto;
import com.i2i.zing.exception.EntityNotFoundException;
import com.i2i.zing.model.Role;
import com.i2i.zing.model.User;
import com.i2i.zing.service.RoleService;
import com.i2i.zing.service.UserService;
import com.i2i.zing.mapper.DarkStoreMapper;
import com.i2i.zing.model.DarkStore;
import com.i2i.zing.repository.DarkStoreRepository;
import com.i2i.zing.service.DarkStoreService;

@Service
public class DarkStoreServiceImpl implements DarkStoreService {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    DarkStoreRepository darkStoreRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Override
    public APIResponse addDarkStore(DarkStoreDto darkStoreDto) {
        APIResponse apiResponse = new APIResponse();
        Role role = roleService.retrieveRoleByName(UserRole.MANAGER);
        if (userService.checkByEmailId(darkStoreDto.getEmailId())) {
            User user = userService.retrieveByEmail(darkStoreDto.getEmailId());
            boolean isRoleExist = false;
            for (Role existingRole : user.getRoles()) {
                if (existingRole.getRoleId().equals(role.getRoleId())) {
                    isRoleExist = true;
                    break;
                }
            }
            if (isRoleExist) {
                apiResponse.setStatus(HttpStatus.CONFLICT.value());
                apiResponse.setData("Try a new mail id, Id : " +
                        darkStoreDto.getEmailId()  + " already exists.");
                return apiResponse;
            }
            user.getRoles().add(role);
            userService.createUser(user);
            apiResponse.setStatus(HttpStatus.CREATED.value());
            return apiResponse;
        }
        DarkStore darkStore = DarkStoreMapper.convertDtoToEntity(darkStoreDto);
        User user = DarkStoreMapper.convertDtoToUser(darkStoreDto);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        User savedUser = userService.createUser(user);
        darkStore.setUser(savedUser);
        DarkStore resultDarkStore = darkStoreRepository.save(darkStore);
        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setData(DarkStoreMapper.convertResponseDto(resultDarkStore));
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
        if (result.isEmpty()) {
            logger.warn("Dark Store List is Empty..");
        }
        return apiResponse;
    }

    @Override
    public APIResponse getDarkStoreById(String darkStoreId) {
        APIResponse apiResponse = new APIResponse();
        DarkStore darkStore = darkStoreRepository.findByIsDeletedFalseAndDarkStoreId(darkStoreId);
        apiResponse.setData(darkStore);
        apiResponse.setStatus(HttpStatus.OK.value());
        if (null == darkStore) {
            logger.warn("An Error Occurred while getting DarkStore with Id : {} ", darkStoreId);
            throw new EntityNotFoundException("DarkStore Not found with Id : " + darkStoreId);
        }
        return apiResponse;
    }

    @Override
    public APIResponse deleteDarkStore(String darkStoreId) {
        APIResponse apiResponse = new APIResponse();
        DarkStore darkStore = darkStoreRepository.findByIsDeletedFalseAndDarkStoreId(darkStoreId);
        if (null == darkStore) {
            logger.warn("Dark Store Not found with ID :{}", darkStoreId);
            throw new EntityNotFoundException("Dark Store Not found with Id : " + darkStoreId);
        }
        darkStore.setDeleted(true);
        darkStoreRepository.save(darkStore);
        apiResponse.setData("Dark store Deleted Successfully : " + darkStoreId);
        apiResponse.setStatus(HttpStatus.NO_CONTENT.value());
        return apiResponse;
    }

    @Override
    public boolean verifyDarkStoreId(String darkStoreId) {
        return darkStoreRepository.existsById(darkStoreId);
    }

    @Override
    public APIResponse updateDarkStore(DarkStoreResponseDto darkStoreResponseDto) {
        APIResponse apiResponse = new APIResponse();
        DarkStore existingDarkStore = darkStoreRepository.findByIsDeletedFalseAndDarkStoreId(darkStoreResponseDto.getDarkStoreId());
        if (null == existingDarkStore) {
            logger.warn("DarkStore Not found to Update with Id :{}", darkStoreResponseDto.getDarkStoreId());
            throw new EntityNotFoundException("DarkStore Not found with Id : " + darkStoreResponseDto.getDarkStoreId());
        }
        existingDarkStore.setUser(existingDarkStore.getUser());
        DarkStoreResponseDto updatedDarkStore = DarkStoreMapper.convertResponseDto(darkStoreRepository.save(existingDarkStore));
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(updatedDarkStore);
        return apiResponse;
    }

}
