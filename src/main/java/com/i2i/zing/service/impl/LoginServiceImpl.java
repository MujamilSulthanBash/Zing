package com.i2i.zing.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.common.UserRole;
import com.i2i.zing.configuration.JwtService;
import com.i2i.zing.dto.CustomerRequestDto;
import com.i2i.zing.dto.DeliveryPersonRequestDto;
import com.i2i.zing.dto.UserLoginRequestDto;
import com.i2i.zing.dto.VerifyEmailDto;
import com.i2i.zing.mapper.UserMapper;
import com.i2i.zing.model.Customer;
import com.i2i.zing.model.DeliveryPerson;
import com.i2i.zing.model.Role;
import com.i2i.zing.model.User;
import com.i2i.zing.service.CustomerService;
import com.i2i.zing.service.DeliveryPersonService;
import com.i2i.zing.service.LoginService;
import com.i2i.zing.service.RoleService;
import com.i2i.zing.service.UserService;
import com.i2i.zing.util.OtpGenerator;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DeliveryPersonService deliveryPersonService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    EmailSenderService emailSenderService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    private static Map<String, String> otpStore = new HashMap<>();

    private static Map<String, CustomerRequestDto> primaryCacheMemory = new HashMap<>();

    private static Map<String, DeliveryPersonRequestDto> secondaryCacheMemory = new HashMap<>();

    public APIResponse customerSignUp(CustomerRequestDto customerRequestDto) {
        APIResponse apiResponse = new APIResponse();
        Role role = roleService.retrieveRoleByName(UserRole.CUSTOMER);
        User user = UserMapper.userEntity(customerRequestDto);
        if (userService.checkByEmailId(customerRequestDto.getEmailId())) {
            User checkUser = userService.retrieveByEmail(customerRequestDto.getEmailId());
            boolean checkRole = checkRole(checkUser.getRoles(), role.getRoleId());
            if (!checkRole) {
                Set<Role> roles = checkUser.getRoles();
                roles.add(role);
                user.setRoles(roles);
                user.setUserId(checkUser.getUserId());
                user.setPassword(checkUser.getPassword());
                User savedUser = userService.createUser(user);
                createCustomer(savedUser);
                apiResponse.setStatus(HttpStatus.OK.value());
                return apiResponse;
            }
            apiResponse.setStatus(HttpStatus.FOUND.value());
            return apiResponse;
        }
        if (! sendMail(customerRequestDto.getEmailId())) {
            apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            apiResponse.setData("Please verify your email !");
            return apiResponse;
        }
        primaryCacheMemory.put(customerRequestDto.getEmailId(), customerRequestDto);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public APIResponse deliveryPersonSignup(DeliveryPersonRequestDto deliveryPersonRequestDto) {
        APIResponse apiResponse = new APIResponse();
        Role role = roleService.retrieveRoleByName(UserRole.DELIVERYPERSON);
        User user = UserMapper.userEntity(deliveryPersonRequestDto);
        if (userService.checkByEmailId(deliveryPersonRequestDto.getEmailId())) {
            User checkUser = userService.retrieveByEmail(deliveryPersonRequestDto.getEmailId());
            boolean checkRole = checkRole(checkUser.getRoles(), role.getRoleId());
            if (!checkRole) {
                Set<Role> roles = checkUser.getRoles();
                roles.add(role);
                user.setRoles(roles);
                user.setUserId(checkUser.getUserId());
                user.setPassword(checkUser.getPassword());
                User savedUser = userService.createUser(user);
                createDeliveryPerson(deliveryPersonRequestDto, savedUser);
                apiResponse.setStatus(HttpStatus.OK.value());
                return apiResponse;
            }
            apiResponse.setStatus(HttpStatus.FOUND.value());
            return apiResponse;
        }
        if (! sendMail(deliveryPersonRequestDto.getEmailId())) {
            apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            apiResponse.setData("Please verify your email !");
            return apiResponse;
        }
        secondaryCacheMemory.put(deliveryPersonRequestDto.getEmailId(), deliveryPersonRequestDto);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    /**
     * <p>
     *     This method is responsible for create customer account.
     * </p>
     * @param user - {@link User} details.
     */
    private void createCustomer(User user) {
        Customer customer = new Customer();
        customer.setUser(user);
        customerService.createCustomer(customer);
    }

    /**
     * <p>
     *     This method is responsible for create Delivery person account.
     * </p>
     * @param deliveryPersonRequestDto - {@link DeliveryPersonRequestDto} details.
     * @param user - {@link User} details.
     */
    private void createDeliveryPerson(DeliveryPersonRequestDto deliveryPersonRequestDto, User user) {
        DeliveryPerson deliveryPerson = new DeliveryPerson();
        deliveryPerson.setAadharNumber(deliveryPersonRequestDto.getAadharNumber());
        deliveryPerson.setLicenseNumber(deliveryPersonRequestDto.getLicenseNumber());
        deliveryPerson.setVehicleNumber(deliveryPersonRequestDto.getVehicleNumber());
        deliveryPerson.setUser(user);
        deliveryPersonService.createDeliveryPerson(deliveryPerson);
    }

    /**
     * <p>
     *     This method is responsible for create a user account.
     * </p>
     * @param customerRequestDto - {@link CustomerRequestDto} details.
     * @param role - {@link Role} details.
     * @return saved {@link User} details.
     */
    private User createUser(CustomerRequestDto customerRequestDto, Role role) {
        User user = UserMapper.userEntity(customerRequestDto);
        user.setPassword(encoder.encode(customerRequestDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        return userService.createUser(user);
    }

    /**
     * <p>
     *     This method is responsible for check the role is present or not.
     * </p>
     * @param roles - set of {@link Role} details.
     * @param roleId - {@link Role} role id.
     * @return true if the role is already present else return false.
     */
    private boolean checkRole(Set<Role> roles, String roleId) {
        for (Role checkRole : roles) {
            if (checkRole.getRoleId().equals(roleId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public APIResponse userLogin(UserLoginRequestDto userLoginRequestDto) {
        APIResponse apiResponse = new APIResponse();
        if (userService.checkByEmailId(userLoginRequestDto.getEmailId())) {
            User checkUser = userService.retrieveByEmail(userLoginRequestDto.getEmailId());
            if (encoder.matches(userLoginRequestDto.getPassword(), checkUser.getPassword())) {
                String token = jwtService.generateJwt(checkUser);
                apiResponse.setData(token);
                apiResponse.setStatus(HttpStatus.OK.value());
                return apiResponse;
            }
            apiResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            return apiResponse;
        }
        apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return apiResponse;
    }

    @Override
    public APIResponse verifyCustomerEmail(VerifyEmailDto verifyEmailDto) {
        APIResponse apiResponse = new APIResponse();
        if (verifyOtp(verifyEmailDto.getEmail(), verifyEmailDto.getOtp())) {
            Role role = roleService.retrieveRoleByName(UserRole.CUSTOMER);
            for (Map.Entry<String, CustomerRequestDto> entry : primaryCacheMemory.entrySet()) {
                if (entry.getKey().equals(verifyEmailDto.getEmail())) {
                    User savedUser = createUser(entry.getValue(), role);
                    createCustomer(savedUser);
                    apiResponse.setStatus(HttpStatus.OK.value());
                    return apiResponse;
                }
            }
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        }
        apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return apiResponse;
    }

    @Override
    public APIResponse verifyDeliveryPersonEmail(VerifyEmailDto verifyEmailDto) {
        APIResponse apiResponse = new APIResponse();
        if (verifyOtp(verifyEmailDto.getEmail(), verifyEmailDto.getOtp())) {
            Role role = roleService.retrieveRoleByName(UserRole.DELIVERYPERSON);
            for (Map.Entry<String, DeliveryPersonRequestDto> entry : secondaryCacheMemory.entrySet()) {
                if (entry.getKey().equals(verifyEmailDto.getEmail())) {
                    DeliveryPersonRequestDto deliveryPersonRequestDto = entry.getValue();
                    User savedUser = createUser(UserMapper.customerDto(deliveryPersonRequestDto), role);
                    createDeliveryPerson(deliveryPersonRequestDto, savedUser);
                    apiResponse.setStatus(HttpStatus.OK.value());
                    return apiResponse;
                }
            }
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        }
        apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return apiResponse;
    }

    private boolean sendMail(String email) {
        for (Map.Entry<String, String> entry : otpStore.entrySet()) {
            if (entry.getKey().equals(email)) {
                return false;
            }
        }
        String to = email;
        String subject = "Login OTP";
        String otp = String.valueOf(OtpGenerator.generateOtp());
        String body = "Your One Time Password for SignUp verification is " + otp;
        otpStore.put(email, otp);
        emailSenderService.sendEmail(to, subject, body);
        return true;
    }

    private boolean verifyOtp(String email, String otp) {
        for (Map.Entry<String, String> entry : otpStore.entrySet()) {
            if (entry.getKey().equals(email) && entry.getValue().equals(otp)) {
                otpStore.remove(entry.getKey());
                return true;
            }
        }
        return false;
    }

}
