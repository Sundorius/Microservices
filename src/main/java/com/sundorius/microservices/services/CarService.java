package com.sundorius.microservices.services;

import com.sundorius.microservices.domain.enitities.CarEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
public interface CarService
{
    CarEntity create(Long warehouse_id, CarEntity carEntity);

    CarEntity update(Long car_id, CarEntity carEntity);

    CarEntity partialUpdate(Long car_id,  Map<Object, Object> objectMap);

    CarEntity changeWarehouse(Long car_id,  Long new_warehouse_id);

    List<CarEntity> findAll();

    Optional<CarEntity> findOne(Long car_id);

    boolean isExists(Long car_id);

    void delete(Long car_id);
}
