package com.i2i.zing.service.impl;

import com.i2i.zing.model.DeliveryPerson;
import com.i2i.zing.repository.DeliveryPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPersonServiceImpl {

    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    public void createDeliveryPerson(DeliveryPerson deliveryPerson) {
        DeliveryPerson person = DeliveryPerson.builder()
                .aadharNumber(deliveryPerson.getAadharNumber())
                .licenseNumber(deliveryPerson.getLicenseNumber())
                .vehicleNumber(deliveryPerson.getVehicleNumber())
                .build();
        deliveryPersonRepository.save(person);
    }

    public DeliveryPerson getDeliveryPersonsById(String userId) {
        return deliveryPersonRepository.findByDeliveryPersonId(userId);
    }
}
