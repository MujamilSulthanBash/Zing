package com.i2i.zing.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * <p>
 *     This class represents the Store of Hub Details
 *     like Store ID, Location of the Store and Stock Details
 *     for this Store
 * </p>
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "darkstores")
public class DarkStore {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String darkStoreId;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "darkstore", fetch = FetchType.EAGER)
    private Set<Stock> stocks;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;
}
