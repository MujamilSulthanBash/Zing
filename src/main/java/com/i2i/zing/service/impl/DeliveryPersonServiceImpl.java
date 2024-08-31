package com.i2i.zing.service.impl;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.VerifyOrderDto;
import com.i2i.zing.model.Order;
import com.i2i.zing.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.i2i.zing.model.DeliveryPerson;
import com.i2i.zing.repository.DeliveryPersonRepository;
import com.i2i.zing.service.DeliveryPersonService;

@Service
public class DeliveryPersonServiceImpl implements DeliveryPersonService {

    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    @Autowired
    private OrderService orderService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public void createDeliveryPerson(DeliveryPerson deliveryPerson) {
        deliveryPersonRepository.save(deliveryPerson);
    }

    @Override
    public DeliveryPerson getDeliveryPersonById(String userId) {
        return deliveryPersonRepository.findByDeliveryPersonId(userId);
    }

    @Override
    public APIResponse verifyOrder(VerifyOrderDto verifyOrderDto) {
        APIResponse apiResponse = new APIResponse();
        Order order = orderService.getOrderById(verifyOrderDto.getOrderId());
        if (encoder.matches(verifyOrderDto.getOtp(), order.getOtp())) {
            return orderService.updateOrderStatus(verifyOrderDto.getOrderId());
        }
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        return apiResponse;
    }

}
