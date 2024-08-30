package com.i2i.zing.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.i2i.zing.model.*;
import com.i2i.zing.service.CartService;
import com.i2i.zing.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.common.DeliveryStatus;
import com.i2i.zing.dto.OrderAssignDto;
import com.i2i.zing.exeception.EntityNotFoundException;
import com.i2i.zing.mapper.OrderAssignMapper;
import com.i2i.zing.repository.OrderAssignRepository;
import com.i2i.zing.service.OrderAssignService;

/**
 * <p>
 * Passes value for insertion,deletion and retrieval on OrderAssign.
 * </p>
 */
@Service
public class OrderAssignServiceImpl implements OrderAssignService {

    @Autowired
    OrderAssignRepository orderAssignRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    DeliveryPersonServiceImpl deliveryPersonService;

    @Autowired
    CustomerService customerService;

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void addOrderAssign(Order order) {
        logger.debug("Revoked userService to get use list.");
        Customer customer = customerService.getCustomer(order.getCart().getCustomer().getCustomerId());
        System.out.println(customer.getUser().getUserId());
        List<User> users = userService
                .getUserByLocation(customer.getUser().getLocation());
        List<DeliveryPerson> deliveryPersons = new ArrayList<>();
        logger.debug("Revoked deliveryPersonService to get deliveryPerson list.");
        for (User user : users) {
            DeliveryPerson d1 = deliveryPersonService.getDeliveryPersonById(user.getUserId());
            System.out.println(d1.getDeliveryPersonId());
            deliveryPersons.add(deliveryPersonService.getDeliveryPersonById(user.getUserId()));
            break;
        }
        DeliveryPerson deliveryPerson = null;
        for (DeliveryPerson person : deliveryPersons) {
            if (person.getOrderAssign().size() < 5) {
                deliveryPerson = person;
                break;
            }
        }
        logger.debug("Assigned delivery Person.");
        OrderAssign orderAssign = OrderAssign.builder()
                .order(order)
                .deliveryPerson(deliveryPerson)
                .deliveryStatus(DeliveryStatus.PENDING)
                .build();
        OrderAssign resultOrderAssign = orderAssignRepository.save(orderAssign);
    }

    @Override
    public APIResponse getOrderAssigns() {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(orderAssignRepository.findByIsDeletedFalse().stream()
                .map(OrderAssignMapper::convertToOrderAssignDto).toList());
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse getOrderAssign(String orderAssignId) {
        APIResponse apiResponse = new APIResponse();
        OrderAssign orderAssign = orderAssignRepository.findByAssignIdAndIsDeletedFalse(orderAssignId);
        if (null == orderAssign) {
            logger.warn("There is no assigning record with ID : {}", orderAssignId);
            throw new EntityNotFoundException("There is no assigning record with ID : " + orderAssignId);
        }
        apiResponse.setData(OrderAssignMapper.convertToOrderAssignDto(orderAssign));
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse updateOrderAssign(OrderAssignDto orderAssignDto) {
        APIResponse apiResponse = new APIResponse();
        OrderAssign orderAssign = orderAssignRepository.findByAssignIdAndIsDeletedFalse(orderAssignDto.getAssignId());
        if (null == orderAssign) {
            logger.warn("There is no assigning record with ID : {} to update.", orderAssignDto.getAssignId());
            throw new EntityNotFoundException("There is no assigning record with ID : " + orderAssignDto.getAssignId() + " to update.");
        }
        OrderAssign requestBody = OrderAssignMapper.convertToUpdatableEntity(orderAssignDto);
        orderAssign.setDeliveryStatus(requestBody.getDeliveryStatus());
        orderAssignRepository.save(orderAssign);
        apiResponse.setData(orderAssign);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }
}
