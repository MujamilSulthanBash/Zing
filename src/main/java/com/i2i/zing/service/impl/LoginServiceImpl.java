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
import com.i2i.zing.dto.ForgetPasswordDto;
import com.i2i.zing.dto.ForgetPasswordValidator;
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

    private static final Map<String, String> otpStore = new HashMap<>();

    private static final Map<String, String> forgetPasswordOtpStore = new HashMap<>();

    private static final Map<String, CustomerRequestDto> primaryCacheMemory = new HashMap<>();

    private static final Map<String, DeliveryPersonRequestDto> secondaryCacheMemory = new HashMap<>();

    @Override
    public APIResponse customerSignUp(CustomerRequestDto customerRequestDto) {
        APIResponse apiResponse = new APIResponse();
        Role role = roleService.retrieveRoleByName(UserRole.CUSTOMER);
        User user = UserMapper.userEntity(customerRequestDto);
        if (userService.checkByEmailId(customerRequestDto.getEmailId())) {
            User checkUser = userService.retrieveByEmail(customerRequestDto.getEmailId());
            boolean isRoleCheck = false;
            for (Role checkRole : checkUser.getRoles()) {
                if (checkRole.getRoleId().equals(role.getRoleId())) {
                    isRoleCheck = true;
                    break;
                }
            }
            if (!isRoleCheck) {
                Set<Role> roles = checkUser.getRoles();
                roles.add(role);
                user.setRoles(roles);
                user.setUserId(checkUser.getUserId());
                user.setPassword(checkUser.getPassword());
                User savedUser = userService.createUser(user);
                Customer customer = new Customer();
                customer.setUser(savedUser);
                customerService.createCustomer(customer);
                apiResponse.setStatus(HttpStatus.OK.value());
                return apiResponse;
            }
            apiResponse.setStatus(HttpStatus.CONFLICT.value());
            apiResponse.setData("The mail id - " + customerRequestDto.getEmailId() + " already exists.");
            return apiResponse;
        }
        boolean isMailSend = true;
        for (Map.Entry<String, String> entry : otpStore.entrySet()) {
            if (entry.getKey().equals(customerRequestDto.getEmailId())) {
                isMailSend = false;
                break;
            }
        }
        if (!isMailSend) {
            apiResponse.setStatus(HttpStatus.CONFLICT.value());
            apiResponse.setData("Please verify your email !");
            return apiResponse;
        }
        String to = customerRequestDto.getEmailId();
        String subject = "Login OTP";
        String otp = String.valueOf(OtpGenerator.generateOtp());
        String body = "Your One Time Password for SignUp verification is " + otp;
        otpStore.put(customerRequestDto.getEmailId(), otp);
        emailSenderService.sendEmail(to, subject, body);
        primaryCacheMemory.put(customerRequestDto.getEmailId(), customerRequestDto);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData("Otp sent to respective mail Id - " + customerRequestDto.getEmailId());
        return apiResponse;
    }

    @Override
    public APIResponse deliveryPersonSignup(DeliveryPersonRequestDto deliveryPersonRequestDto) {
        APIResponse apiResponse = new APIResponse();
        Role role = roleService.retrieveRoleByName(UserRole.DELIVERYPERSON);
        User user = UserMapper.getUserEntityFromDeliveryPerson(deliveryPersonRequestDto);
        if (userService.checkByEmailId(deliveryPersonRequestDto.getEmailId())) {
            User checkUser = userService.retrieveByEmail(deliveryPersonRequestDto.getEmailId());
            boolean isRoleCheck = false;
            for (Role checkRole : checkUser.getRoles()) {
                if (checkRole.getRoleId().equals(role.getRoleId())) {
                    isRoleCheck = true;
                    break;
                }
            }
            if (!isRoleCheck) {
                Set<Role> roles = checkUser.getRoles();
                roles.add(role);
                user.setRoles(roles);
                user.setUserId(checkUser.getUserId());
                user.setPassword(checkUser.getPassword());
                User savedUser = userService.createUser(user);
                DeliveryPerson deliveryPerson = new DeliveryPerson();
                deliveryPerson.setAadharNumber(deliveryPersonRequestDto.getAadharNumber());
                deliveryPerson.setLicenseNumber(deliveryPersonRequestDto.getLicenseNumber());
                deliveryPerson.setVehicleNumber(deliveryPersonRequestDto.getVehicleNumber());
                deliveryPerson.setUser(savedUser);
                deliveryPersonService.createDeliveryPerson(deliveryPerson);
                apiResponse.setStatus(HttpStatus.OK.value());
                return apiResponse;
            }
            apiResponse.setStatus(HttpStatus.CONFLICT.value());
            apiResponse.setData("The mail id - " + deliveryPersonRequestDto.getEmailId() + " already exists.");
            return apiResponse;
        }
        boolean isMailSend = true;
        for (Map.Entry<String, String> entry : otpStore.entrySet()) {
            if (entry.getKey().equals(deliveryPersonRequestDto.getEmailId())) {
                isMailSend = false;
                break;
            }
        }
        if (!isMailSend) {
            apiResponse.setStatus(HttpStatus.CONFLICT.value());
            apiResponse.setData("Please verify your email !");
            return apiResponse;
        }
        String to = deliveryPersonRequestDto.getEmailId();
        String subject = "Login OTP";
        String otp = String.valueOf(OtpGenerator.generateOtp());
        String body = "Your One Time Password for SignUp verification is " + otp;
        otpStore.put(deliveryPersonRequestDto.getEmailId(), otp);
        emailSenderService.sendEmail(to, subject, body);
        secondaryCacheMemory.put(deliveryPersonRequestDto.getEmailId(), deliveryPersonRequestDto);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData("Otp sent to respective mail Id - " + deliveryPersonRequestDto.getEmailId());
        return apiResponse;
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
            apiResponse.setData("Email or password might be incorrect..");
            return apiResponse;
        }
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        apiResponse.setData("Check your Email Id and try again..");
        return apiResponse;
    }

    @Override
    public APIResponse verifyCustomerEmail(VerifyEmailDto verifyEmailDto) {
        APIResponse apiResponse = new APIResponse();
        boolean isVerified = false;
        for (Map.Entry<String, String> entry : otpStore.entrySet()) {
            if (entry.getKey().equals(verifyEmailDto.getEmail()) && entry.getValue().equals(verifyEmailDto.getOtp())) {
                isVerified = true;
                break;
            }
        }
        if (isVerified) {
            Role role = roleService.retrieveRoleByName(UserRole.CUSTOMER);
            for (Map.Entry<String, CustomerRequestDto> entry : primaryCacheMemory.entrySet()) {
                if (entry.getKey().equals(verifyEmailDto.getEmail())) {
                    User user = UserMapper.userEntity(entry.getValue());
                    user.setPassword(encoder.encode(entry.getValue().getPassword()));
                    Set<Role> roles = new HashSet<>();
                    roles.add(role);
                    user.setRoles(roles);
                    User savedUser = userService.createUser(user);
                    Customer customer = new Customer();
                    customer.setUser(savedUser);
                    Customer resultCustomer = customerService.createCustomer(customer);
                    for (Map.Entry<String, String> secondEntry : otpStore.entrySet()) {
                        if (secondEntry.getKey().equals(verifyEmailDto.getEmail()) && secondEntry.getValue().equals(verifyEmailDto.getOtp())) {
                            otpStore.remove(secondEntry.getKey());
                            break;
                        }
                    }
                    apiResponse.setStatus(HttpStatus.CREATED.value());
                    apiResponse.setData(UserMapper.convertToResponseDto(resultCustomer));
                    return apiResponse;
                }
            }
            apiResponse.setStatus(HttpStatus.FORBIDDEN.value());
            apiResponse.setData("Try with proper URI..");
            return apiResponse;
        }
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        apiResponse.setData("Email or Otp Incorrect Try Again..");
        return apiResponse;
    }

    @Override
    public APIResponse verifyDeliveryPersonEmail(VerifyEmailDto verifyEmailDto) {
        APIResponse apiResponse = new APIResponse();
        boolean isVerified = false;
        for (Map.Entry<String, String> entry : otpStore.entrySet()) {
            if (entry.getKey().equals(verifyEmailDto.getEmail()) && entry.getValue().equals(verifyEmailDto.getOtp())) {
                isVerified = true;
                break;
            }
        }
        if (isVerified) {
            Role role = roleService.retrieveRoleByName(UserRole.DELIVERYPERSON);
            for (Map.Entry<String, DeliveryPersonRequestDto> entry : secondaryCacheMemory.entrySet()) {
                if (entry.getKey().equals(verifyEmailDto.getEmail())) {
                    DeliveryPersonRequestDto deliveryPersonRequestDto = entry.getValue();
                    User user = UserMapper.userEntity(UserMapper.customerDto(deliveryPersonRequestDto));
                    user.setPassword(encoder.encode(deliveryPersonRequestDto.getPassword()));
                    Set<Role> roles = new HashSet<>();
                    roles.add(role);
                    user.setRoles(roles);
                    User savedUser = userService.createUser(user);
                    DeliveryPerson deliveryPerson = new DeliveryPerson();
                    deliveryPerson.setAadharNumber(deliveryPersonRequestDto.getAadharNumber());
                    deliveryPerson.setLicenseNumber(deliveryPersonRequestDto.getLicenseNumber());
                    deliveryPerson.setVehicleNumber(deliveryPersonRequestDto.getVehicleNumber());
                    deliveryPerson.setUser(savedUser);
                    DeliveryPerson resultDeliveryPerson = deliveryPersonService.createDeliveryPerson(deliveryPerson);
                    for (Map.Entry<String, String> secondEntry : otpStore.entrySet()) {
                        if (secondEntry.getKey().equals(verifyEmailDto.getEmail()) && secondEntry.getValue().equals(verifyEmailDto.getOtp())) {
                            otpStore.remove(secondEntry.getKey());
                            break;
                        }
                    }
                    apiResponse.setStatus(HttpStatus.CREATED.value());
                    apiResponse.setData(UserMapper.convertToDeliveryPersonResponseDto(resultDeliveryPerson));
                    return apiResponse;
                }
            }
            apiResponse.setStatus(HttpStatus.FORBIDDEN.value());
            apiResponse.setData("Try with proper URI..");
            return apiResponse;
        }
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        apiResponse.setData("Email or Otp Incorrect Try Again..");
        return apiResponse;
    }

    public APIResponse sendMailForForgetPassword(ForgetPasswordDto forgetPasswordDto) {
        APIResponse apiResponse = new APIResponse();
        System.out.println(forgetPasswordDto.getEmail());
        if (userService.checkByEmailId(forgetPasswordDto.getEmail())) {
            String to = forgetPasswordDto.getEmail();
            String subject = "Login OTP";
            String otp = String.valueOf(OtpGenerator.generateOtp());
            String body = "Your One Time Password for resetting the password is " + otp;
            forgetPasswordOtpStore.put(forgetPasswordDto.getEmail(), otp);
            emailSenderService.sendEmail(to, subject, body);
            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setData("Otp sent to respective mail Id - " + forgetPasswordDto.getEmail());
            return apiResponse;
        }
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        apiResponse.setData("Enter valid Zing email id !");
        return apiResponse;
    }

    public APIResponse verifyEmailForForgetPassword(ForgetPasswordValidator forgetPasswordValidator) {
        APIResponse apiResponse = new APIResponse();
        if (userService.checkByEmailId(forgetPasswordValidator.getEmail())) {
            boolean isVerified = false;
            for (Map.Entry<String, String> entry : forgetPasswordOtpStore.entrySet()) {
                if (entry.getKey().equals(forgetPasswordValidator.getEmail()) && entry.getValue().equals(forgetPasswordValidator.getOtp())) {
                    isVerified = true;
                    break;
                }
            }
            if(isVerified) {
                User checkUser = userService.retrieveByEmail(forgetPasswordValidator.getEmail());
                checkUser.setPassword(encoder.encode(forgetPasswordValidator.getNewPassword()));
                userService.createUser(checkUser);
                forgetPasswordOtpStore.remove(forgetPasswordValidator.getEmail());
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setData("Password changed successfully");
                return apiResponse;
            }
            apiResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            apiResponse.setData("Email or Otp Incorrect Try Again..");
            return apiResponse;
        }
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        apiResponse.setData("Enter valid Zing email id !");
        return apiResponse;
    }

}
