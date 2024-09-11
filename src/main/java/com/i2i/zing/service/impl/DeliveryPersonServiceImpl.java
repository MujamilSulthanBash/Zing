package com.i2i.zing.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.exception.EntityNotFoundException;
import com.i2i.zing.mapper.OrderAssignMapper;
import com.i2i.zing.model.DeliveryPerson;
import com.i2i.zing.repository.DeliveryPersonRepository;
import com.i2i.zing.service.DeliveryPersonService;

@Service
public class DeliveryPersonServiceImpl implements DeliveryPersonService {

    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    private static final Logger logger = LogManager.getLogger();

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public DeliveryPerson createDeliveryPerson(DeliveryPerson deliveryPerson) {
        return deliveryPersonRepository.save(deliveryPerson);
    }

    @Override
    public List<DeliveryPerson> getDeliveryPersonByLocation(String location) {
        return deliveryPersonRepository.findDeliverPersonByLocation(location);
    }

    @Override
    public APIResponse getOrderAssignsOfDeliveryPerson(String userId) {
        APIResponse apiResponse = new APIResponse();
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findByUserId(userId);
        if (null == deliveryPerson) {
            logger.warn("DeliveryPerson with Id : {} not exists.", userId);
            throw new EntityNotFoundException("DeliveryPerson with Id : " + userId + " not exists.");
        }
        apiResponse.setData(deliveryPerson.getOrderAssign().stream()
                .map(OrderAssignMapper::convertToOrderAssignDto).toList());
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

}
