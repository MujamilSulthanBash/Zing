package com.i2i.zing.service.impl;

import com.i2i.zing.model.Order;
import com.i2i.zing.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void createDeliveryPerson(DeliveryPerson deliveryPerson) {
        deliveryPersonRepository.save(deliveryPerson);
    }

    @Override
    public DeliveryPerson getDeliveryPersonById(String userId) {
        return deliveryPersonRepository.findByDeliveryPersonId(userId);
    }

    @Override
    public boolean verifyOrder(String orderId, String otp) {
        return false;
    }

}
