package com.i2i.zing.service.impl;

import com.i2i.zing.common.PaymentMethod;
import com.i2i.zing.common.PaymentStatus;
import com.i2i.zing.service.OrderAssignService;
import com.i2i.zing.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.dto.OrderDto;
import com.i2i.zing.service.OrderService;
import com.i2i.zing.mapper.OrderMapper;
import com.i2i.zing.model.Order;
import com.i2i.zing.repository.OrderRepository;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Passes value for insertion,deletion and retrieval on Order.
 * </p>
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderAssignService orderAssignService;

    @Autowired
    StockService stockService;

    @Override
    public APIResponse addOrder(OrderDto orderDto) {
        APIResponse apiResponse = new APIResponse();
        Order resultOrder = orderRepository.save(OrderMapper.convertToOrder(orderDto));
        apiResponse.setData(resultOrder);
        apiResponse.setStatus(HttpStatus.OK.value());
        if ((resultOrder.getCart().getPaymentMethod().equals(PaymentMethod.UPI)) &&
                (resultOrder.getPaymentStatus().equals(PaymentStatus.PAID)) ||
                ((resultOrder.getCart().getPaymentMethod().equals(PaymentMethod.CASHON)))) {
            orderAssignService.addOrderAssign(resultOrder);
        }
        stockService.reduceStocks(resultOrder.getCart().getCartItems());
        return apiResponse;
    }

    @Override
    public APIResponse getOrders() {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(orderRepository.findByIsDeletedFalse().stream()
                .map(OrderMapper::convertToOrderDto).toList());
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse getOrder(String orderId) {
        APIResponse apiResponse = new APIResponse();
        Order order = orderRepository.findByOrderIdAndIsDeleted(orderId, false);
        apiResponse.setData(order);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse deleteOrder(String orderId) {
        APIResponse apiResponse = new APIResponse();
        Order order = orderRepository.findByOrderIdAndIsDeleted(orderId, false);
        order.setDeleted(true);
        orderRepository.save(order);
        apiResponse.setData("Order with ID : " + orderId + " has been deleted");
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }
}
