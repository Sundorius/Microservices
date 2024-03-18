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
@Table(name="cars")
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_id_seq")
    private Long id;

    @Column
    private Integer identCode;

    @Column
    private String make;

    @Column
    private String model;

    @Column
    private String color;

    @Column
    private String fuelType;

    @Column
    private Integer hp;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private WarehouseEntity warehouseEntity;
}
