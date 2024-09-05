package com.i2i.zing.controller;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.DarkStoreDto;
import com.i2i.zing.dto.DarkStoreRequestDto;
import com.i2i.zing.model.DarkStore;
import com.i2i.zing.service.impl.DarkStoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DarkStoreControllerTest {

    @Mock
    DarkStoreServiceImpl darkStoreService;

    @InjectMocks
    DarkStoreController darkStoreController;

    DarkStoreDto darkStoreDto;

    DarkStoreRequestDto darkStoreRequestDto;

    APIResponse addDarkStoreResponse;

    DarkStore darkStore;

    APIResponse getDarkStoresResponse;

    APIResponse getDarkStoreByIdResponse;

    APIResponse deleteDarkStoreResponse;

    @BeforeEach
    void setUp() {
        darkStore = DarkStore.builder()
                .darkStoreId("1D")
                .isDeleted(false)
                .build();
        darkStoreDto = DarkStoreDto.builder()
                .userName("Aravind")
                .password("1234567")
                .location("Guindy")
                .contactNumber("9843264403")
                .emailId("apsaravindvimal@gmail.com")
                .build();
        darkStoreRequestDto = DarkStoreRequestDto.builder()
                .darkStoreId("1D")
                .location("Guindy")
                .build();
        addDarkStoreResponse = APIResponse.builder()
                .data(darkStoreDto)
                .status(HttpStatus.CREATED.value())
                .build();
        getDarkStoresResponse = APIResponse.builder()
                .data(darkStoreRequestDto)
                .status(HttpStatus.OK.value())
                .build();
        getDarkStoreByIdResponse = APIResponse.builder()
                .data(darkStore)
                .status(HttpStatus.OK.value())
                .build();
        deleteDarkStoreResponse = APIResponse.builder()
                .data(null)
                .status(HttpStatus.OK.value())
                .build();
    }

    @Test
    public void testAddDarkStore() {
        when(darkStoreService.addDarkStore(darkStoreDto)).thenReturn(addDarkStoreResponse);
        ResponseEntity<APIResponse> addDarkStoreResponse = darkStoreController.addDarkStore(darkStoreDto);
        assertEquals(HttpStatus.CREATED, addDarkStoreResponse.getStatusCode());
    }

    @Test
    public void testGetDarkStores() {
        when(darkStoreService.getDarkStores()).thenReturn(getDarkStoresResponse);
        ResponseEntity<APIResponse> getDarkStoresResponse = darkStoreController.getDarkStores();
        assertEquals(HttpStatus.OK, getDarkStoresResponse.getStatusCode());
    }

    @Test
    public void testGetDarkStoreById() {
        when(darkStoreService.getDarkStoreById(darkStoreRequestDto.getDarkStoreId())).thenReturn(getDarkStoreByIdResponse);
        ResponseEntity<APIResponse> getDarkStoreByIdResponse = darkStoreController.getDarkStoreById(darkStore.getDarkStoreId());
        assertEquals(HttpStatus.OK, getDarkStoreByIdResponse.getStatusCode());
    }

    @Test
    void testDeleteDarkStore() {
        when(darkStoreService.deleteDarkStore(darkStore.getDarkStoreId())).thenReturn(deleteDarkStoreResponse);
        ResponseEntity<APIResponse> deleteDarkStoreResponse = darkStoreController.deleteDarkStore(darkStore.getDarkStoreId());
        assertEquals(HttpStatus.OK, deleteDarkStoreResponse.getStatusCode());
    }
}
