package com.i2i.zing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.i2i.zing.model.DeliveryPerson;
import com.i2i.zing.repository.DeliveryPersonRepository;
import com.i2i.zing.service.DeliveryPersonService;

@Service
public class DeliveryPersonServiceImpl implements DeliveryPersonService {

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

    @Override
    public DeliveryPerson getDeliveryPersonById(String userId) {
        return deliveryPersonRepository.findByDeliveryPersonId(userId);
    }

}
