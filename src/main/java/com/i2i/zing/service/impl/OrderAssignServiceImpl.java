package com.i2i.zing.service.impl;

import java.util.*;

import com.i2i.zing.model.*;
import com.i2i.zing.service.CustomerService;
import com.i2i.zing.service.DeliveryPersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.common.DeliveryStatus;
import com.i2i.zing.dto.OrderAssignDto;
import com.i2i.zing.exception.EntityNotFoundException;
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
    DeliveryPersonService deliveryPersonService;

    @Autowired
    CustomerService customerService;

    private static final Logger logger = LogManager.getLogger();

    private static Map<String, String> otpStore = new HashMap<>();

    @Override
    public void addOrderAssign(Order order) {
        logger.debug("Revoked userService to get use list.");
        Customer customer = customerService.getCustomer(order.getCart().getCustomer().getCustomerId());
        System.out.println(customer.getUser().getUserId());
        System.out.println(customer.getUser().getLocation());
        List<User> users = userService.getUserByLocation(customer.getUser().getLocation());
        logger.debug("Revoked deliveryPersonService to get deliveryPerson list.");
        List<DeliveryPerson> deliveryPeoples = new ArrayList<>();
        for (User user : users) {
            System.out.println(user.getUserId());
            DeliveryPerson d1 = deliveryPersonService.getDeliveryPersonById(user.getUserId());
//            deliveryPeoples.add(deliveryPersonService.getDeliveryPersonById(user.getUserId()));
            System.out.println(d1.getDeliveryPersonId());
        }

        System.out.println(deliveryPeoples.size());
        System.out.println(deliveryPeoples.getFirst().getAadharNumber());
//        for (int i = 0; i < deliveryPeoples.size(); i++) {
//            for (int j = i; j < deliveryPeoples.size()-1; j++) {
//                if (deliveryPeoples.get(i).getOrderAssign().size() < deliveryPeoples.get(j).getOrderAssign().size()) {
//                    DeliveryPerson temp = deliveryPeoples.get(j);
//                    deliveryPeoples.set(j,deliveryPeoples.get(i));
//                    deliveryPeoples.set(i, temp);
//                }
//            }
//        }
        deliveryPeoples.sort(Comparator.comparingInt(dp -> dp.getOrderAssign().size()));
        System.out.println(deliveryPeoples.getFirst().getDeliveryPersonId());
        logger.debug("Assigned delivery Person.");
        OrderAssign orderAssign = OrderAssign.builder()
                .order(order)
                .deliveryPerson(deliveryPeoples.getFirst())
                .deliveryStatus(DeliveryStatus.ACCEPTED)
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

    @Override
    public void updateOrderStatus(String status, String orderId) {
        OrderAssign orderAssign = orderAssignRepository.findByOrderId(orderId);
        if (null == orderAssign) {
            logger.warn("There is no assigning record related to order : {} to update.", orderId);
            throw new EntityNotFoundException("There is no assigning record related to order : " + orderId + " to update.");
        }
        orderAssign.setDeliveryStatus(DeliveryStatus.valueOf(status));
        orderAssignRepository.save(orderAssign);
    }
}
