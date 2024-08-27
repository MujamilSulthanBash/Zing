package com.i2i.zing.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.i2i.zing.common.DeliveryStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

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
@EntityListeners(AuditingEntityListener.class)
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

    @Column(name = "delivery_status")
    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @LastModifiedDate
    private Date modifiedDate;
}
