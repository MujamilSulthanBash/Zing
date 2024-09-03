package com.i2i.zing.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.i2i.zing.common.PaymentMethod;
import com.i2i.zing.common.PaymentStatus;
import com.i2i.zing.dto.OrderDto;
import com.i2i.zing.model.*;
import com.i2i.zing.repository.OrderRepository;
import com.i2i.zing.service.impl.EmailSenderService;
import com.i2i.zing.service.impl.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    @InjectMocks
    OrderServiceImpl orderServiceImpl;

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderAssignService orderAssignService;

    @Mock
    StockService stockService;

    @Mock
    EmailSenderService emailSenderService;

    @Mock
    CartService cartService;

    private String cartId;
    private Cart cart;
    private CartItem cartItem;
    private Order order;
    private OrderDto orderDto;
    private Stock stock;

    @BeforeEach
    public void setUp() {
        cartId = "1c";
        cart = Cart.builder()
                .cartId(cartId)
                .cartItems(Set.of(CartItem.builder()
                        .id("1ci")
                        .cart(cart)
                        .item(Item.builder()
                                .itemId("1i").build())
                        .totalPrice(12.0)
                        .quantity(2)
                        .build()))
                .build();
        Set<CartItem> cartItems = new HashSet<>();
        cartItem = CartItem.builder()
                .id("1ci")
                .cart(cart)
                .item(Item.builder()
                        .itemId("1i").build())
                .totalPrice(12.0)
                .quantity(2)
                .build();
        cartItems.add(cartItem);
        order = Order.builder()
                .orderId("1o")
                .paymentStatus(PaymentStatus.PAID)
                .paymentMethod(PaymentMethod.UPI)
                .cart(cart)
                .build();
        orderDto = OrderDto.builder()
                .orderId("1o")
                .paymentStatus("PAID")
                .paymentMethod("UPI")
                .cartId("1c").build();
        stock = Stock.builder()
                .darkstore(DarkStore.builder().darkStoreId("1ds").build())
                .item(Item.builder().itemId("1i").build())
                .quantity(10).build();
    }
}
