package com.sundorius.microservices.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseDto
{
    private Long id;

    private String wnumber;

    private String place;

    private Integer maxCapacity;

    private Integer carsStored;
}
