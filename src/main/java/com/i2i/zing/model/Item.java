package com.i2i.zing.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * <p>
 *     Represents blueprint for the Item datatype.
 *     Contains details of item such as Id, delivery person' Id.
 *     It comes under category datatype.
 * </p>
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String itemId;

    @Column(name = "name")
    private String itemName;

    @Column(name = "price")
    private int price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
}
