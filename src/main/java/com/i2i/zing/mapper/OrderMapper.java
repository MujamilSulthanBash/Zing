package com.i2i.zing.mapper;

import com.i2i.zing.common.PaymentStatus;
import com.i2i.zing.dto.OrderDto;
import com.i2i.zing.model.Cart;
import com.i2i.zing.model.Order;

/**
 * <p>
 *     Converts the json objects according to application operations.
 *     e.g., (dto object -> json object, json object -> dto object)
 * </p>
 */
public class OrderMapper {

    /**
     * <p>
     *     Converts the entity to displayable dto format.
     * </p>
     * @param order {@link Order} for conversion into dto.
     * @return OrderDto to display.
     */
    public static OrderDto convertToOrderDto(Order order) {
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .cartId(order.getCart().getCartId())
                .paymentStatus(order.getPaymentStatus().toString())
                .timeOfOrder(order.getCreatedDate())
                .build();
    }

    /**
     * <p>
     *     Converts input dto to entity for creating Order.
     * </p>
     * @param orderDto {@link OrderDto} to convert into entity.
     * @return Order to for internal usage.
     */
    public static Order convertToOrder(OrderDto orderDto) {
        return Order.builder()
                .cart(Cart.builder()
                        .cartId(orderDto.getCartId())
                        .build())
                .paymentStatus("PAID".equals(orderDto.getPaymentStatus()) ? PaymentStatus.PAID : PaymentStatus.UNPAID)
                .build();
    }
}
