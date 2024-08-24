package com.i2i.zing.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * <p>
 *     This class represents the Stock Details
 *     for Various Store like Quantity of the Item
 * </p>
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "stocks")
public class Stock extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String stockId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "darkstore_id")
    private DarkStore darkstore;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;
}
