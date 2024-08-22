package com.i2i.zing.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * <p>
 *     Represents blueprint for the Delivery person datatype.
 *     Contains details of delivery person such as Id, name, vehicle Number.
 *     Includes the number of orders allotted for him.
 * </p>
 */
@Entity
@Table(name = "delivery_persons")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPerson {
    @Id
    @Column(name = "id")
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private String deliveryPersonId;

    @Column(name = "vehicle_number", nullable = false, length = 10)
    private String vehicleNumber;

    @Column(name = "aadhar_number", nullable = false, length = 12)
    private String aadharNumber;

    @Column(name = "license_number", nullable = false, length = 15)
    private String licenseNumber;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "deliveryPerson", fetch = FetchType.EAGER)
    private Set<OrderAssign> orderAssign;
}
