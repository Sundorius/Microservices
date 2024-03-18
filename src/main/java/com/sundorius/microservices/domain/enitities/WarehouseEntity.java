package com.sundorius.microservices.domain.enitities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="warehouses")
public class WarehouseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "warehouse_id_seq")
    private Long id;

    @Column
    private String wnumber;

    @Column
    private String place;

    @Column
    private Integer maxCapacity;

    @Column
    private Integer carsStored;
}
