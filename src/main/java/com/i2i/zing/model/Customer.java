package com.i2i.zing.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.i2i.zing.common.Membership;

/**
 * <p>
 *     Represents blueprint for the Customer datatype.
 *     Contains details of customer such as Id, customer's membership.
 *     It is subtype of User type and contains all the fields in the
 *     User datatype
 * </p>
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String customerId;

    @Column(name = "membership", columnDefinition = "varchar(32) default 'SILVER'",
            nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Membership memberShip;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
