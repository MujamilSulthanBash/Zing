package com.i2i.zing.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.i2i.zing.common.UserRole;
import com.i2i.zing.mapper.UserMapperTest;
import com.i2i.zing.model.Role;
import com.i2i.zing.model.User;
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
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
public class DarkStoreServiceImplTest {

    @Mock
    DarkStoreRepository darkStoreRepository;

    @Mock
    RoleServiceImpl roleService;

    @Mock
    UserServiceImpl userService;

    @InjectMocks
    private DarkStoreServiceImpl darkStoreService;

    DarkStore darkStore;
    List<DarkStore> darkStores;
    DarkStoreDto darkStoreDto;
    Role managerRole;
    Role userRole;
    User user;
    User secondUser;

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
        darkStores = new ArrayList<>();
        managerRole = Role.builder()
                .roleName(UserRole.MANAGER)
                .roleId("001")
                .build();
        userRole = Role.builder()
                .roleName(UserRole.CUSTOMER)
                .roleId("002")
                .build();
        user = UserMapperTest.getUser();
        secondUser = UserMapperTest.getUser();
        Set<Role> roles = new HashSet<>();
        Set<Role> secondRoles = new HashSet<>();
        roles.add(managerRole);
        secondRoles.add(userRole);
        user.setRoles(roles);
        secondUser.setRoles(secondRoles);
    }

    @Test
    void testAddDarkStoreFailure() {
        when(roleService.retrieveRoleByName(UserRole.MANAGER)).thenReturn(managerRole);
        when(userService.checkByEmailId(darkStoreDto.getEmailId())).thenReturn(false);
        when(userService.createUser(any())).thenReturn(user);
        assertEquals(darkStoreService.addDarkStore(darkStoreDto).getStatus(), HttpStatus.CREATED.value());
    }

    @Test
    void testAddDarkStoreSuccess() {
        when(roleService.retrieveRoleByName(UserRole.MANAGER)).thenReturn(managerRole);
        when(userService.checkByEmailId(darkStoreDto.getEmailId())).thenReturn(true);
        when(userService.retrieveByEmail(darkStoreDto.getEmailId())).thenReturn(user);
        assertEquals(darkStoreService.addDarkStore(darkStoreDto).getStatus(), HttpStatus.FOUND.value());
    }

    @Test
    void testAddDarkStoreAnotherSuccess() {
        when(roleService.retrieveRoleByName(UserRole.MANAGER)).thenReturn(managerRole);
        when(userService.checkByEmailId(darkStoreDto.getEmailId())).thenReturn(true);
        when(userService.retrieveByEmail(darkStoreDto.getEmailId())).thenReturn(secondUser);
        assertEquals(darkStoreService.addDarkStore(darkStoreDto).getStatus(), HttpStatus.CREATED.value());
    }

    @Test
    void testGetAllDarkStores() {
        when(darkStoreRepository.findByIsDeletedFalse()).thenReturn(List.of(darkStore));
        APIResponse apiResponse = darkStoreService.getDarkStores();
        assertThat(apiResponse.getData()).isNotNull();
    }

    @Test
    void testGetAllDarkStoresFailure() {
        when(darkStoreRepository.findByIsDeletedFalse()).thenReturn(darkStores);
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

    @Test
    void testDeleteDarkStoreFailure() {
        when(darkStoreRepository.findByIsDeletedFalseAndDarkStoreId(darkStore.getDarkStoreId())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> {
            darkStoreService.deleteDarkStore(darkStore.getDarkStoreId());
        });
    }

}
