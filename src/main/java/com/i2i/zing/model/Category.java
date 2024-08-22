package com.i2i.zing.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * <p>
 *     Represents blueprint for the Category datatype.
 *     Contains details of category such as Id, category name.
 *     It defines type of items to be stored in a dark store.
 * </p>
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String categoryId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private Set<Item> items;
}
