package com.i2i.zing.mapper;

import com.i2i.zing.dto.CustomerRequestDto;
import com.i2i.zing.dto.CustomerResponseDto;
import com.i2i.zing.dto.DeliveryPersonRequestDto;
import com.i2i.zing.dto.DeliveryPersonResponseDto;
import com.i2i.zing.model.Customer;
import com.i2i.zing.model.DeliveryPerson;
import com.i2i.zing.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <p>
 * Converts the json objects according to application operations.
 * e.g., (dto object -> entity object, entity object -> dto object)
 * </p>
 */
public class UserMapper {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    /**
     * <p>
     *     This method convert the CustomerRequestDto
     *     to User Entity Object
     * </p>
     * @param customerRequestDto  {@link CustomerRequestDto} - Customer Request as Dto
     * @return User as Entity Object
     */
    public static User userEntity(CustomerRequestDto customerRequestDto) {
        return User.builder()
                .userName(customerRequestDto.getUserName())
                .emailId(customerRequestDto.getEmailId())
                .contactNumber(customerRequestDto.getContactNumber())
                .location(customerRequestDto.getLocation())
                .password(customerRequestDto.getPassword())
                .build();
    }

    /**
     * <p>
     *     This class convert the Dto to Entity Object
     * </p>
     * @param deliveryPersonRequestDto {@link DeliveryPersonRequestDto} as Dto Object
     * @return CustomerRequestDto as Dto Object
     */
    public static CustomerRequestDto customerDto(DeliveryPersonRequestDto deliveryPersonRequestDto) {
        return CustomerRequestDto.builder()
                .userName(deliveryPersonRequestDto.getUserName())
                .emailId(deliveryPersonRequestDto.getEmailId())
                .contactNumber(deliveryPersonRequestDto.getContactNumber())
                .location(deliveryPersonRequestDto.getLocation())
                .password(deliveryPersonRequestDto.getPassword())
                .build();
    }

    /**
     * <p>
     *     This class convert the object to dto for user
     *     acknowledgement.
     * </p>
     * @param customer {@link Customer} as Dto Object
     * @return CustomerResponseDto as Dto Object
     */
    public static CustomerResponseDto convertToResponseDto(Customer customer) {
        return CustomerResponseDto.builder()
                .customerId(customer.getCustomerId())
                .userName(customer.getUser().getUserName())
                .contactNumber(customer.getUser().getContactNumber())
                .emailId(customer.getUser().getEmailId())
                .location(customer.getUser().getLocation())
                .build();
    }

    /**
     * <p>
     *     This method convert the Dto to Entity Object
     * </p>
     * @param deliveryPersonRequestDto {@link DeliveryPersonRequestDto} as Dto Object
     * @return User as Entity Object
     */
    public static User getUserEntityFromDeliveryPerson(DeliveryPersonRequestDto deliveryPersonRequestDto) {
        return User.builder()
                .userName(deliveryPersonRequestDto.getUserName())
                .emailId(deliveryPersonRequestDto.getEmailId())
                .contactNumber(deliveryPersonRequestDto.getContactNumber())
                .location(deliveryPersonRequestDto.getLocation())
                .password(deliveryPersonRequestDto.getPassword())
                .build();
    }

    /**
     * <p>
     *     This class convert the object to dto for user
     *     acknowledgement.
     * </p>
     * @param deliveryPerson {@link DeliveryPerson} as Dto Object
     * @return DeliveryPersonResponseDto as Dto Object
     */
    public static DeliveryPersonResponseDto convertToDeliveryPersonResponseDto(
            DeliveryPerson deliveryPerson) {
        return DeliveryPersonResponseDto.builder()
                .deliveryPersonId(deliveryPerson.getDeliveryPersonId())
                .userName(deliveryPerson.getUser().getUserName())
                .contactNumber(deliveryPerson.getUser().getContactNumber())
                .location(deliveryPerson.getUser().getLocation())
                .emailId(deliveryPerson.getUser().getEmailId())
                .build();
    }

    /**
     * <p>
     *     This class creates a user object for internal operation.
     * </p>
     * @return User as Dto Object
     */
    public static User getAdminDetails() {
        return User.builder()
                .userName("ADMIN")
                .emailId("mujamil.official@gmail.com")
                .contactNumber("123456789")
                .password(encoder.encode(System.getenv("ADMIN_PASSWORD")))
                .location("chennai")
                .build();
    }

}
