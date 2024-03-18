package com.sundorius.microservices.services;

import com.sundorius.microservices.domain.enitities.WarehouseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
public interface  WarehouseService
{
    WarehouseEntity create(WarehouseEntity warehouseEntity);

    public WarehouseEntity update(Long warehouse_id, WarehouseEntity warehouseEntity);

    public WarehouseEntity partialUpdate(Long warehouse_id, Map<Object, Object> objectMap);

    List<WarehouseEntity> findAll();

    Optional<WarehouseEntity> findOne(Long warehouse_id);

    boolean isExists(Long warehouse_id);

    void delete(Long warehouse_id);
}
