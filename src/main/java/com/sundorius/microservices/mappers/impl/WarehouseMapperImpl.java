package com.sundorius.microservices.mappers.impl;

import com.sundorius.microservices.domain.dto.WarehouseDto;
import com.sundorius.microservices.domain.enitities.WarehouseEntity;
import com.sundorius.microservices.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMapperImpl implements Mapper<WarehouseEntity, WarehouseDto>
{
    private ModelMapper modelMapper;

    public WarehouseMapperImpl(ModelMapper modelMapper)
    {
        this.modelMapper = modelMapper;
    }

    @Override
    public WarehouseDto mapTo(WarehouseEntity warehouseEntity)
    {
        return modelMapper.map(warehouseEntity, WarehouseDto.class);
    }

    @Override
    public WarehouseEntity mapFrom(WarehouseDto warehouseDto)
    {
        return modelMapper.map(warehouseDto, WarehouseEntity.class);
    }
}
