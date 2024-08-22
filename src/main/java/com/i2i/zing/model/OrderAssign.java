package com.i2i.zing.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * <p> Represents blueprint for the orderAssign datatype.
 * Contains details of order such as Id, delivery person' Id.
 * It is used to link the order placed by the customer at order
 * assigning record.
 * </p>
 */
@Entity
@Table(name = "order_Assign")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderAssign {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String assignId;

    @OneToOne(targetEntity = Order.class,
             cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "delivery_person_id")
    private Set<DeliveryPerson> deliveryPerson;

    @Column(name = "delivery_status")
    private String deliveryStatus;
}
