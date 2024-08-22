package com.i2i.zing.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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
}
