package com.sundorius.microservices.services.impl;

import com.sundorius.microservices.domain.enitities.WarehouseEntity;
import com.sundorius.microservices.repositories.WarehouseRepository;
import com.sundorius.microservices.services.WarehouseService;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class WarehouseServiceImpl implements WarehouseService
{
    WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository)
    {
        this.warehouseRepository = warehouseRepository;
    }


    @Override
    public WarehouseEntity create(WarehouseEntity warehouseEntity)
    {
        return warehouseRepository.save(warehouseEntity);
    }

    @Override
    public WarehouseEntity update(Long warehouse_id, WarehouseEntity warehouseEntity)
    {
        warehouseEntity.setId(warehouse_id);
        return warehouseRepository.save(warehouseEntity);
    }

    @Override
    public WarehouseEntity partialUpdate(Long warehouse_id, Map<Object, Object> objectMap)
    {
        if(!this.isExists(warehouse_id))
        {
            return null;
        }
        WarehouseEntity warehouseEntity = warehouseRepository.findById(warehouse_id).get();

        objectMap.forEach((key, value) ->
        {
            Field field = ReflectionUtils.findField(WarehouseEntity.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, warehouseEntity, value);
        });
        return warehouseRepository.save(warehouseEntity);
    }

    @Override
    public List<WarehouseEntity> findAll()
    {
        return StreamSupport
                .stream(
                        warehouseRepository.findAll().spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<WarehouseEntity> findOne(Long warehouse_id)
    {
        return warehouseRepository.findById(warehouse_id);
    }

    @Override
    public boolean isExists(Long warehouse_id)
    {
        return warehouseRepository.existsById(warehouse_id);
    }

    @Override
    public void delete(Long warehouse_id)
    {
        warehouseRepository.deleteById(warehouse_id);
    }
}
