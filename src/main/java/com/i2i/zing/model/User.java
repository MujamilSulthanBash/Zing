package com.i2i.zing.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

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
@Builder
@Table(name = "users")
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String userId;

    @Column(name = "name")
    private String userName;

    @Column(name = "email")
    private String emailId;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "location")
    private String location;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Role> roles;
}
