package com.i2i.zing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import com.i2i.zing.common.UserRole;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String roleId;

    @Column(name = "role_name", columnDefinition = "varchar(32) default 'CUSTOMER'",
            nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole roleName;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users;
}
