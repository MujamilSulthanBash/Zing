package com.i2i.zing.model;

import com.i2i.zing.common.DeliveryStatus;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *      Represents blueprint for the orderAssign datatype.
 *      Contains details of order such as Id, delivery person' Id.
 *      It is used to link the order placed by the customer at order
 *      assigning record.
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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "delivery_person_id")
    private DeliveryPerson deliveryPerson;

    @Column(name = "delivery_status", columnDefinition = "varchar(32) default 'PENDING'",
            nullable = false)
    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus deliveryStatus;
}
