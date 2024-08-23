package com.i2i.zing.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import com.i2i.zing.common.UserRole;

/**
 * <p>
 *     This class represents the Role Details of the User
 *     like Role Name and an user has Many roles as well
 * </p>
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String roleId;

    @Column(name = "role_name")
    @Enumerated(value = EnumType.STRING)
    private UserRole roleName;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users;
}
