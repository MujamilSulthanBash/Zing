package com.i2i.zing.mapper;

import com.i2i.zing.common.DeliveryStatus;
import com.i2i.zing.dto.OrderAssignDto;
import com.i2i.zing.model.DeliveryPerson;
import com.i2i.zing.model.Order;
import com.i2i.zing.model.OrderAssign;

/**
 * <p>
 *     Converts the json objects according to application operations.
 *     e.g., (dto object -> json object, json object -> dto object)
 * </p>
 */
public class OrderAssignMapper {

    /**
     * <p>
     *     Converts the entity to displayable dto format.
     * </p>
     * @param orderAssign {@link OrderAssign} for conversion into dto.
     * @return OrderAssignDto to display.
     */
    public static OrderAssignDto convertToOrderAssignDto(OrderAssign orderAssign) {
        return OrderAssignDto.builder()
                .assignId(orderAssign.getAssignId())
                .deliveryPersonId(orderAssign.getDeliveryPerson().getDeliveryPersonId())
                .deliveryStatus(orderAssign.getDeliveryStatus().toString())
                .orderId(orderAssign.getOrder().getOrderId())
                .build();
    }

    /**
     * <p>
     *     Converts input dto to entity for creating OrderAssign.
     * </p>
     * @param orderAssignDto {@link OrderAssignDto} to convert into entity.
     * @return OrderAssign for internal usage.
     */
    public static OrderAssign convertToOrderAssign(OrderAssignDto orderAssignDto) {
            DeliveryStatus deliveryStatus = DeliveryStatus.valueOf(orderAssignDto.getDeliveryStatus());
        return OrderAssign.builder()
                .order(Order.builder()
                        .orderId(orderAssignDto.getOrderId()).build())
                .deliveryPerson(DeliveryPerson.builder()
                        .deliveryPersonId(orderAssignDto.getDeliveryPersonId())
                        .build())
                .deliveryStatus(deliveryStatus)
                .build();
    }

    /**
     * <p>
     *     Converts input dto to entity for updating OrderAssign.
     * </p>
     * @param orderAssignDto {@link OrderAssignDto} to convert into entity.
     * @return OrderAssign for internal usage.
     */
    public static OrderAssign convertToUpdatableEntity(OrderAssignDto orderAssignDto) {
        DeliveryStatus deliveryStatus = DeliveryStatus.valueOf(orderAssignDto.getDeliveryStatus());
        return OrderAssign.builder()
                .assignId(orderAssignDto.getAssignId())
                .order(Order.builder()
                        .orderId(orderAssignDto.getOrderId()).build())
                .deliveryPerson(DeliveryPerson.builder()
                        .deliveryPersonId(orderAssignDto.getDeliveryPersonId())
                        .build())
                .deliveryStatus(deliveryStatus)
                .build();
    }
}
