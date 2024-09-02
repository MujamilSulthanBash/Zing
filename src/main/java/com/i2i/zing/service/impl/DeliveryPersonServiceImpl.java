package com.i2i.zing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.i2i.zing.model.DeliveryPerson;
import com.i2i.zing.repository.DeliveryPersonRepository;
import com.i2i.zing.service.DeliveryPersonService;

@Service
public class DeliveryPersonServiceImpl implements DeliveryPersonService {

    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public void createDeliveryPerson(DeliveryPerson deliveryPerson) {
        deliveryPersonRepository.save(deliveryPerson);
    }

    @Override
    public DeliveryPerson getDeliveryPersonById(String userId) {
//        return deliveryPersonRepository.findByDeliveryPersonId(userId);
        return deliveryPersonRepository.findById(userId).orElse(null);
    }

}
