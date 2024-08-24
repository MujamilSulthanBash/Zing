package com.i2i.zing.service.impl;

import com.i2i.zing.model.DeliveryPerson;
import com.i2i.zing.model.User;
import com.i2i.zing.repository.DeliveryPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPersonService {

    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    public void createDeliveryPerson(DeliveryPerson deliveryPerson, User user) {
        DeliveryPerson person = DeliveryPerson.builder()
                .aadharNumber(deliveryPerson.getAadharNumber())
                .licenseNumber(deliveryPerson.getLicenseNumber())
                .vehicleNumber(deliveryPerson.getVehicleNumber())
                .user(user)
                .build();
        deliveryPersonRepository.save(person);
    }

}
