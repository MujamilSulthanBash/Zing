package com.i2i.zing.service.impl;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.DarkStoreDto;
import com.i2i.zing.exception.EntityNotFoundException;
import com.i2i.zing.model.DarkStore;
import com.i2i.zing.repository.DarkStoreRepository;

@ExtendWith(MockitoExtension.class)
public class DarkStoreServiceImplTest {

    @Mock
    DarkStoreRepository darkStoreRepository;

    @InjectMocks
    private DarkStoreServiceImpl darkStoreService;

    DarkStore darkStore;

    DarkStoreDto darkStoreDto;

    @BeforeEach
    void setup() {
        darkStore = DarkStore.builder()
                .darkStoreId("1")
                .isDeleted(false)
                .build();
        darkStoreDto = DarkStoreDto.builder()
                .userName("Aravind")
                .emailId("aravind.sureshkumar@ideas2it.com")
                .contactNumber("9843264403")
                .location("Guindy")
                .password("1234567")
                .build();
    }

    @Test
    void testGetAllDarkStores() {
        when(darkStoreRepository.findByIsDeletedFalse()).thenReturn(List.of(darkStore));
        APIResponse apiResponse = darkStoreService.getDarkStores();

        assertThat(apiResponse.getData()).isNotNull();
    }

    @Test
    void testGetDarkStoreByIdSuccess(){
        when(darkStoreRepository.findByIsDeletedFalseAndDarkStoreId("1")).thenReturn(darkStore);
        APIResponse apiResponse = darkStoreService.getDarkStoreById(darkStore.getDarkStoreId());

        assertThat(apiResponse.getData()).isNotNull();
    }

    @Test
    void testGetDarkStoreByIdFailure() {
        when(darkStoreRepository.findByIsDeletedFalseAndDarkStoreId("1")).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> {
            darkStoreService.getDarkStoreById("1");
        });
    }

    @Test
    void testDeleteDarkStore() {
        when(darkStoreRepository.save(any(DarkStore.class))).thenReturn(darkStore);
        when(darkStoreRepository.findByIsDeletedFalseAndDarkStoreId("1")).thenReturn(darkStore);

        APIResponse apiResponse = darkStoreService.deleteDarkStore("1");
        assertEquals(apiResponse.getData(), "Dark store Deleted Successfully : " + darkStore.getDarkStoreId());
    }

}
