package com.i2i.zing.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

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
@EntityListeners(AuditingEntityListener.class)
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

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @LastModifiedDate
    private Date modifiedDate;
}
