package com.i2i.zing.model;

import com.i2i.zing.common.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * <p>
 *     Represents blueprint for the order datatype.
 *     Contains details of order such as Id, customer Id, amount.
 *     It is used to link the order placed by the customer at order
 *     assigning record.
 * </p>
 */
@Entity
@Table(name = "orders")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "payment_status", columnDefinition = "varchar(32) default 'UNPAID'",
            nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus paymentStatus;
}
