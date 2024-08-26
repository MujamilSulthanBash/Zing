package com.i2i.zing.service.impl;

import com.i2i.zing.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.common.DeliveryStatus;
import com.i2i.zing.mapper.OrderAssignMapper;
import com.i2i.zing.model.DeliveryPerson;
import com.i2i.zing.model.Order;
import com.i2i.zing.model.OrderAssign;
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
    UserService userService;

    @Autowired
    DeliveryPersonService deliveryPersonService;

    @Override
    public void addOrderAssign(Order order) {
        APIResponse apiResponse = new APIResponse();
        List<User> users = userService
                .getUserByLocation(order
                        .getCart().getCustomer().getUser().getLocation());
        List<DeliveryPerson> deliveryPersons = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            deliveryPersons.add( deliveryPersonService.getDeliveryPersonsById(users.get(i).getUserId()));
            break;
        }
        DeliveryPerson deliveryPerson = null;
        for (DeliveryPerson person : deliveryPersons) {
            if (person.getOrderAssign().size() < 5) {
                deliveryPerson = person;
                break;
            }
        }
        OrderAssign orderAssign = OrderAssign.builder()
                .order(order)
                .deliveryPerson(deliveryPerson)
                .deliveryStatus(DeliveryStatus.PENDING)
                .build();
        orderAssignRepository.save(orderAssign);
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
        OrderAssign orderAssign = orderAssignRepository.findByOrderAssignIdAndIsDeletedFalse(orderAssignId);
        apiResponse.setData(OrderAssignMapper.convertToOrderAssignDto(orderAssign));
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse deleteOrderAssign(String orderAssignId) {
        APIResponse apiResponse = new APIResponse();
        OrderAssign orderAssign = orderAssignRepository.findByOrderAssignIdAndIsDeletedFalse(orderAssignId);
        orderAssign.setDeleted(true);
        orderAssignRepository.save(orderAssign);
        apiResponse.setData(orderAssign);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }
}
