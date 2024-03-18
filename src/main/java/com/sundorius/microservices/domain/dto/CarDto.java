package com.sundorius.microservices.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDto
{
    private Long id;

    private Integer identCode;

    private String make;

    private String model;

    private String color;

    private String fuelType;

    private Integer hp;

    private WarehouseDto warehouse;
}
