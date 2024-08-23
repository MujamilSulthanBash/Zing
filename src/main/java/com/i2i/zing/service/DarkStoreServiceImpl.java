package com.i2i.zing.service;

import com.i2i.zing.dto.DarkStoreDto;
import com.i2i.zing.mapper.DarkStoreMapper;
import com.i2i.zing.model.DarkStore;
import com.i2i.zing.repository.DarkStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DarkStoreServiceImpl implements DarkStoreService {

    @Autowired
    DarkStoreRepository darkStoreRepository;

    public DarkStoreDto addDarkStore(DarkStoreDto darkStoreDto) {
        DarkStore darkStore = DarkStoreMapper.convertDtoToEntity(darkStoreDto);
        return DarkStoreMapper.convertEntityToDto(darkStoreRepository.save(darkStore));
    }

    @Override
    public List<DarkStoreDto> getDarkStores() {
        List<DarkStoreDto> result = new ArrayList<>();
        List<DarkStore> darkStores = darkStoreRepository.findByIsDeletedFalse();
        for (DarkStore darkStore : darkStores) {
            result.add(DarkStoreMapper.convertEntityToDto(darkStore));
        }
        return result;
    }

    @Override
    public DarkStoreDto getDarkStoreById(String darkStoreId) {
        DarkStore darkStore = darkStoreRepository.findByIsDeletedFalseAndDarkStoreId(darkStoreId);
        return DarkStoreMapper.convertEntityToDto(darkStore);
    }

    @Override
    public void deleteDarkStore(String darkStoreId) {
        DarkStore darkStore = darkStoreRepository.findByIsDeletedFalseAndDarkStoreId(darkStoreId);
        darkStore.setDeleted(true);
        darkStoreRepository.save(darkStore);
    }

}
