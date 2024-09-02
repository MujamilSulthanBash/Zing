package com.i2i.zing.service;

import com.i2i.zing.model.DarkStore;
import com.i2i.zing.model.Role;
import com.i2i.zing.model.User;
import com.i2i.zing.repository.DarkStoreRepository;
import com.i2i.zing.service.impl.DarkStoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class DarkStoreServiceImplTest {
    @Mock
    DarkStoreRepository darkStoreRepository;

    @InjectMocks
    private DarkStoreServiceImpl darkStoreService;

    @BeforeEach
    void setup() {
        Set<Role> roles = new HashSet<>();
        //Role role = new Role(1, "DELIVERY_PERSON", );
        //roles.add(role);
        LocalDate currentDate = LocalDate.now();
        //User user = new User("1", "Aravind", "apsaravindvimal@gmail.com","9843264403", "Guindy", "123456", "DELIVERYPERSON", currentDate, false);
        //DarkStore darkStore = new DarkStore("1", );
    }
}
