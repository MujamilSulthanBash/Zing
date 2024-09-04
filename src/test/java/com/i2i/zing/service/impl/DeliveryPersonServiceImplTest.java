package com.i2i.zing.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.i2i.zing.common.UserRole;
import com.i2i.zing.model.DeliveryPerson;
import com.i2i.zing.model.Role;
import com.i2i.zing.model.User;
import com.i2i.zing.repository.DeliveryPersonRepository;

@ExtendWith(MockitoExtension.class)
public class DeliveryPersonServiceImplTest {
    @Mock
    DeliveryPersonRepository deliveryPersonRepository;

    @InjectMocks
    DeliveryPersonServiceImpl deliveryPersonService;

    DeliveryPerson deliveryPerson;

    User user;

    Role role;

    List<DeliveryPerson> deliveryPersons = new ArrayList<>();

    @BeforeEach
    void setUp() {
        role = Role.builder()
                .roleId("1R")
                .roleName(UserRole.DELIVERYPERSON)
                .build();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user = User.builder()
                .userId("1U")
                .emailId("apsaravindvimal@gmail.com")
                .contactNumber("9843264403")
                .location("Guindy")
                .password("1234567")
                .roles(roles)
                .build();
        deliveryPerson = DeliveryPerson.builder()
                .deliveryPersonId("1D")
                .aadharNumber("221144118899")
                .licenseNumber("TN:67-1234")
                .user(user)
                .build();
        deliveryPersons.add(deliveryPerson);
    }

    @Test
    void testCreateDeliveryPersonSuccess() {
        when(deliveryPersonRepository.save(any(DeliveryPerson.class))).thenReturn(deliveryPerson);
        deliveryPersonService.createDeliveryPerson(deliveryPerson);
    }

    @Test
    void testGetDeliveryPersonByLocationSuccess() {
        when(deliveryPersonRepository.findDeliverPersonByLocation("Guindy")).thenReturn(deliveryPersons);
        List<DeliveryPerson> deliveryPersons = deliveryPersonService.getDeliveryPersonByLocation("Guindy");

        assertEquals(deliveryPersons.size(), 1);
    }

}
