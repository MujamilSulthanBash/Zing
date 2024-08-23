package com.i2i.zing.service.impl;

import com.i2i.zing.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import com.i2i.zing.dto.OrderDto;
import com.i2i.zing.mapper.OrderMapper;
import com.i2i.zing.model.Order;
import com.i2i.zing.repository.OrderRepository;

import java.util.List;

/**
 * <p>
 * Passes value for insertion,deletion and retrieval on Order.
 * </p>
 */
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderDto addOrder(OrderDto orderDto) {
        Order order = OrderMapper.convertToOrder(orderDto);
        Order resultOrder = orderRepository.save(order);
        return OrderMapper.convertToOrderDto(resultOrder);
    }

    @Override
    public List<OrderDto> getOrders() {
        return orderRepository.findByIsDeletedFalse().stream()
                .map(OrderMapper::convertToOrderDto).toList();
    }

    @Override
    public OrderDto getOrder(String orderId) {
        Order order = orderRepository.findByOrderIdAndIsDeleted(orderId, false);
        return OrderMapper.convertToOrderDto(order);
    }


    @Override
    public void deleteOrder(String orderId) {
        Order order = orderRepository.findByOrderIdAndIsDeleted(orderId, false);
        order.setDeleted(true);
        orderRepository.save(order);
    }
}
