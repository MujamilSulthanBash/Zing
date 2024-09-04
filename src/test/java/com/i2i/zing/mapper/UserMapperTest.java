package com.i2i.zing.mapper;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.i2i.zing.common.UserRole;
import com.i2i.zing.dto.CustomerRequestDto;
import com.i2i.zing.dto.DeliveryPersonRequestDto;
import com.i2i.zing.dto.UserLoginRequestDto;
import com.i2i.zing.dto.VerifyEmailDto;
import com.i2i.zing.model.Customer;
import com.i2i.zing.model.Role;
import com.i2i.zing.model.User;

public class UserMapperTest {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public static User getUser() {
        return User.builder()
                .userId("1")
                .userName("Mujamil")
                .emailId("mujamil.official@gmail.com")
                .location("chennai")
                .password("12345")
                .contactNumber("8056081438")
                .build();
    }

    public static User getExistingUserWithRole() {
        Set<Role> roles = new HashSet<>();
        roles.add(getDeliveryPersonRole());
        return User.builder()
                .userId("1")
                .userName("Mujamil")
                .emailId("mujamil.official@gmail.com")
                .location("chennai")
                .password("12345")
                .contactNumber("8056081438")
                .roles(roles)
                .build();
    }

    public static User getUserWithRole() {
        Set<Role> roles = new HashSet<>();
        roles.add(getCustomerRole());
        return User.builder()
                .userId("2")
                .userName("toni")
                .emailId("toni.official@gmail.com")
                .location("chennai")
                .password("12345")
                .contactNumber("9944927792")
                .roles(roles)
                .build();
    }

    public static User getExistingUserWithSameRole() {
        Set<Role> roles = new HashSet<>();
        roles.add(getCustomerRole());
        return User.builder()
                .userId("1")
                .userName("Mujamil")
                .emailId("mujamil.official@gmail.com")
                .location("chennai")
                .password("12345")
                .contactNumber("8056081438")
                .roles(roles)
                .build();
    }

    public static User getUserWithSameRole() {
        Set<Role> roles = new HashSet<>();
        roles.add(getDeliveryPersonRole());
        return User.builder()
                .userId("2")
                .userName("toni")
                .emailId("toni.official@gmail.com")
                .location("chennai")
                .password("12345")
                .contactNumber("9944927792")
                .roles(roles)
                .build();
    }

    public static User getSavedUser() {
        Set<Role> roles = new HashSet<>();
        roles.add(getDeliveryPersonRole());
        return User.builder()
                .userId("1")
                .userName("Mujamil")
                .emailId("mujamil.official@gmail.com")
                .location("chennai")
                .password(encoder.encode("12345"))
                .contactNumber("8056081438")
                .roles(roles)
                .build();
    }

    public static User getSavedUserForDeliveryPerson() {
        Set<Role> roles = new HashSet<>();
        roles.add(getCustomerRole());
        return User.builder()
                .userId("2")
                .userName("toni")
                .emailId("toni.official@gmail.com")
                .location("chennai")
                .password("12345")
                .contactNumber("9944927792")
                .roles(roles)
                .build();
    }

    public static CustomerRequestDto getCustomerRequestDto() {
        return CustomerRequestDto.builder()
                .userName("Mujamil")
                .emailId("mujamil.official@gmail.com")
                .location("chennai")
                .password("12345")
                .contactNumber("8056081438")
                .build();
    }

    public static DeliveryPersonRequestDto getDeliveryPersonRequestDto() {
        return DeliveryPersonRequestDto.builder()
                .aadharNumber("123456")
                .contactNumber("994492")
                .userName("toni")
                .emailId("toni.official@gmail.com")
                .licenseNumber("123456789")
                .password("12345")
                .location("chennai")
                .vehicleNumber("123456")
                .build();
    }

    public static Role getCustomerRole() {
        return Role.builder().roleId("100")
                .roleName(UserRole.CUSTOMER)
                .build();
    }

    public static Role getDeliveryPersonRole() {
        return Role.builder().roleId("200")
                .roleName(UserRole.DELIVERYPERSON)
                .build();
    }

    public static UserLoginRequestDto getUserLoginRequestDto() {
        return UserLoginRequestDto.builder()
                .emailId("mujamil.official@gmail.com")
                .password("1234")
                .build();
    }

    public static UserLoginRequestDto getUserLoginRequestDtoSuccess() {
        return UserLoginRequestDto.builder()
                .emailId("mujamil.official@gmail.com")
                .password("12345")
                .build();
    }

    public static VerifyEmailDto getVerifyEmailDto() {
        return VerifyEmailDto.builder()
                .email("mujamil.official@gmail.com")
                .otp("3245")
                .build();
    }

    public static Customer getCustomer() {
        return Customer.builder()
                .customerId("100")
                .user(getUser())
                .build();
    }

}
