package com.i2i.zing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * <p>
 *     This class represents the Roles of the Users
 *     in the Applications such as Customer and Delivery Person
 *     It contains the Details like Name, Email ID, Contact Number
 *     Location
 * </p>
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String userId;

    @Column(name = "name")
    private String userName;

    @Column(name = "email")
    private String emailId;

    @Column(name = "contact_number")
    private long contactNumber;

    @Column(name = "location")
    private String location;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
}
