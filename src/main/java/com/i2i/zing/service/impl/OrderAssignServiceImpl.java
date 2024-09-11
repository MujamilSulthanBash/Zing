package com.i2i.zing.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.common.DeliveryStatus;
import com.i2i.zing.dto.OrderAssignDto;
import com.i2i.zing.dto.UpdateOrderStatusDto;
import com.i2i.zing.exception.EntityNotFoundException;
import com.i2i.zing.mapper.OrderAssignMapper;
import com.i2i.zing.model.Customer;
import com.i2i.zing.model.DeliveryPerson;
import com.i2i.zing.model.Order;
import com.i2i.zing.model.OrderAssign;
import com.i2i.zing.service.CustomerService;
import com.i2i.zing.service.DeliveryPersonService;
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
    DeliveryPersonService deliveryPersonService;

    @Autowired
    CustomerService customerService;

    @Autowired
    EmailSenderService emailSenderService;

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void addOrderAssign(Order order) {
        logger.debug("Revoked userService to get use list.");
        Customer customer = customerService.getCustomer(order.getCart().getCustomer().getCustomerId());
        List<DeliveryPerson> deliveryPeoples = deliveryPersonService.getDeliveryPersonByLocation(customer.getUser().getLocation());
        logger.debug("Revoked deliveryPersonService to get deliveryPerson list.");
        for (int i = 0; i < deliveryPeoples.size(); i++) {
            for (int j = i + 1; j <= deliveryPeoples.size() - 1; j++) {
                if (orderAssignRepository.getOrdersCount(deliveryPeoples.get(i).getDeliveryPersonId()) >
                        orderAssignRepository.getOrdersCount(deliveryPeoples.get(j).getDeliveryPersonId())) {
                    DeliveryPerson temp = deliveryPeoples.get(j);
                    deliveryPeoples.set(j, deliveryPeoples.get(i));
                    deliveryPeoples.set(i, temp);
                }
            }
        }
        logger.debug("Assigned delivery Person.");
        OrderAssign orderAssign = OrderAssign.builder()
                .order(order)
                .deliveryPerson(deliveryPeoples.getFirst())
                .deliveryStatus(DeliveryStatus.ACCEPTED)
                .build();
        orderAssignRepository.save(orderAssign);
        logger.info("Order {} has been assigned to .{}", order.getOrderId(), deliveryPeoples.getFirst().getUser().getUserName());
        String subject = "Order Acknowledgement";
        String body = "Your order " + order.getOrderId() + " has been assigned to ."
                + deliveryPeoples.getFirst().getUser().getUserName() + " for verification approach "
                + deliveryPeoples.getFirst().getUser().getContactNumber();
        emailSenderService.sendEmail(order.getCustomer().getUser().getEmailId(), subject, body);
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
        OrderAssignDto orderAssignDto = OrderAssignMapper.convertToOrderAssignDto(orderAssign);
        orderAssignDto.setCustomerName(orderAssign.getOrder().getCustomer().getUser().getUserName());
        orderAssignDto.setCustomerNumber(orderAssign.getOrder().getCustomer().getUser().getContactNumber());
        apiResponse.setData(orderAssignDto);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse updateOrderAssign(UpdateOrderStatusDto updateOrderStatusDto) {
        APIResponse apiResponse = new APIResponse();
        OrderAssign orderAssign = orderAssignRepository.findByAssignIdAndIsDeletedFalse(updateOrderStatusDto.getOrderId());
        if (null == orderAssign) {
            logger.warn("There is no assigning record with ID : {} to update.", updateOrderStatusDto.getOrderId());
            throw new EntityNotFoundException("There is no assigning record with ID : " + updateOrderStatusDto.getOrderId() + " to update.");
        }
        orderAssign.setDeliveryStatus(DeliveryStatus.valueOf(updateOrderStatusDto.getStatus()));
        OrderAssign resultOrderAssign = orderAssignRepository.save(orderAssign);
        OrderAssignDto orderAssignDto = OrderAssignMapper.convertToOrderAssignDto(resultOrderAssign);
        orderAssignDto.setCustomerName(orderAssign.getOrder().getCustomer().getUser().getUserName());
        orderAssignDto.setCustomerNumber(orderAssign.getOrder().getCustomer().getUser().getContactNumber());
        apiResponse.setData(orderAssignDto);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse updateOrderStatus(String status, String orderId) {
        APIResponse apiResponse = new APIResponse();
        OrderAssign orderAssign = orderAssignRepository.findByOrderId(orderId);
        if (null == orderAssign) {
            logger.warn("There is no assigning record related to order : {} to update.", orderId);
            throw new EntityNotFoundException("There is no assigning record related to order : " + orderId + " to update.");
        }
        orderAssign.setDeliveryStatus(DeliveryStatus.valueOf(status));
        OrderAssign resultOrderAssign = orderAssignRepository.save(orderAssign);
        OrderAssignDto orderAssignDto = OrderAssignMapper.convertToOrderAssignDto(resultOrderAssign);
        orderAssignDto.setCustomerName(orderAssign.getOrder().getCustomer().getUser().getUserName());
        orderAssignDto.setCustomerNumber(orderAssign.getOrder().getCustomer().getUser().getContactNumber());
        apiResponse.setData(orderAssignDto);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }
}
