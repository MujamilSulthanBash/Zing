package com.i2i.zing.service.impl;

import java.util.List;
import java.util.Objects;

import com.i2i.zing.model.Cart;
import com.i2i.zing.model.CartItem;
import com.i2i.zing.service.CartService;
import com.i2i.zing.util.OtpGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.i2i.zing.common.APIResponse;
import com.i2i.zing.common.PaymentMethod;
import com.i2i.zing.dto.OrderDto;
import com.i2i.zing.exeception.EntityAlreadyExistsException;
import com.i2i.zing.exeception.EntityNotFoundException;
import com.i2i.zing.mapper.OrderMapper;
import com.i2i.zing.model.Order;
import com.i2i.zing.repository.OrderRepository;
import com.i2i.zing.service.StockService;
import com.i2i.zing.service.OrderAssignService;
import com.i2i.zing.service.OrderService;

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

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    CartService cartService;

    private static final Logger logger = LogManager.getLogger();

    @Override
    public APIResponse addOrder(OrderDto orderDto) {
        APIResponse apiResponse = new APIResponse();
        List<Order> orders = orderRepository.findByIsDeletedFalse();
        logger.debug("Getting list of orders to check previous carts.");
        for (Order order : orders) {
            if (Objects.equals(order.getCart().getCartId(), orderDto.getCartId())) {
                logger.warn("Order with cart id : {} already exists.", orderDto.getCartId());
                throw new EntityAlreadyExistsException("Order with cart id : " + orderDto.getCartId() + " already exists.");
            }
        }
        Cart cart = cartService.getCartAsModel(orderDto.getCartId());
        Order order = OrderMapper.convertToOrder(orderDto);
        Double sum = 0.0;
        for (CartItem cartItem : cart.getCartItems()) {
            sum += cartItem.getTotalPrice();
        }
        order.setCart(cart);
        order.setOrderAmount(sum);
        Order resultOrder = orderRepository.save(order);
        apiResponse.setData(resultOrder);
        apiResponse.setStatus(HttpStatus.OK.value());
        logger.debug("Checking payment method and status to assign order.");
        if ((sum > 50.0) && ((resultOrder.getPaymentMethod().equals(PaymentMethod.UPI)) ||
                (resultOrder.getPaymentMethod().equals(PaymentMethod.CASHON)))) {
            orderAssignService.addOrderAssign(resultOrder);
        }
        stockService.reduceStocks(resultOrder.getCart().getCartItems());
        String subject = "Order Acknowledgement";
        String body = "Your order " + resultOrder.getOrderId() + " has been successfully placed."
                       + "Your One Time Password for the order verification is "
                       + String.valueOf(OtpGenerator.generateOtp());
        emailSenderService.sendEmail(resultOrder.getCart().getCustomer().getUser().getEmailId(), subject, body);
        cartService.addCart(resultOrder.getCart().getCustomer());
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
        Order order = orderRepository.findByOrderIdAndIsDeletedFalse(orderId);
        if (null == order) {
            logger.warn("Order with Id : {} not found", orderId);
            throw new EntityNotFoundException("Order with Id : " + orderId + " not found");
        }
        apiResponse.setData(order);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse deleteOrder(String orderId) {
        APIResponse apiResponse = new APIResponse();
        Order order = orderRepository.findByOrderIdAndIsDeletedFalse(orderId);
        if (null == order) {
            logger.warn("Order with Id : {} not found to update.", orderId);
            throw new EntityNotFoundException("Order with Id : " + orderId + " not found to update.");
        }
        order.setDeleted(true);
        orderRepository.save(order);
        apiResponse.setData("Order with ID : " + orderId + " has been deleted");
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }
}
