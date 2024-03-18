package com.sundorius.microservices.services.impl;


import com.sundorius.microservices.domain.enitities.CarEntity;
import com.sundorius.microservices.domain.enitities.WarehouseEntity;
import com.sundorius.microservices.repositories.CarRepository;
import com.sundorius.microservices.repositories.WarehouseRepository;
import com.sundorius.microservices.services.CarService;
import com.sundorius.microservices.services.WarehouseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CarServiceImpl implements CarService
{
    private CarRepository carRepository;
    private WarehouseRepository warehouseRepository;

    private WarehouseService warehouseService;

    public CarServiceImpl(CarRepository carRepository, WarehouseRepository warehouseRepository, WarehouseService warehouseService)
    {
        this.carRepository = carRepository;
        this.warehouseRepository = warehouseRepository;
        this.warehouseService = warehouseService;
    }

    @Override
    public CarEntity create(Long warehouse_id, CarEntity carEntity)
    {
        Optional<WarehouseEntity> warehouseEntity = warehouseRepository.findById(warehouse_id);
        if(warehouseEntity.isEmpty())
        {
            return null;
        }

        Map<Object, Object> map = new HashMap<>();
        map.put("carsStored", warehouseEntity.get().getCarsStored()+1);
        warehouseService.partialUpdate(warehouse_id, map);

        carEntity.setWarehouseEntity(warehouseEntity.get());
        return carRepository.save(carEntity);
    }

    @Override
    public CarEntity update(Long car_id, CarEntity carEntity)
    {
        if(!this.isExists(car_id))
        {
            return null;
        }

        carEntity.setId(car_id);
        return carRepository.save(carEntity);
    }

    @Override
    public CarEntity partialUpdate(Long car_id, Map<Object, Object> objectMap)
    {
        if(!this.isExists(car_id))
        {
            return null;
        }

        CarEntity carEntity = carRepository.findById(car_id).get();

        objectMap.forEach((key, value) ->
        {
            Field field = ReflectionUtils.findField(CarEntity.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, carEntity, value);
        });
        return carRepository.save(carEntity);
    }

    @Override
    public CarEntity changeWarehouse(Long car_id,  Long new_warehouse_id)
    {
        if(!isExists(car_id))
        {
            return null;
        }

        WarehouseEntity oldWarehouse = this.findOne(car_id).get().getWarehouseEntity();

        if(warehouseService.isExists(new_warehouse_id) && warehouseService.isExists(oldWarehouse.getId()))
        {
            oldWarehouse = warehouseService.findOne(oldWarehouse.getId()).get();
            WarehouseEntity newWarehouse = warehouseService.findOne(new_warehouse_id).get();

            Map<Object, Object> oldWarehouseCars = new HashMap<>();
            oldWarehouseCars.put("carsStored",oldWarehouse.getCarsStored()-1);

            Map<Object, Object> newWarehouseCars = new HashMap<>();
            newWarehouseCars.put("carsStored",newWarehouse.getCarsStored()+1);

            warehouseService.partialUpdate(oldWarehouse.getId(), oldWarehouseCars);
            warehouseService.partialUpdate(new_warehouse_id, newWarehouseCars);

            CarEntity carEntity = this.findOne(car_id).get();
            carEntity.setWarehouseEntity(warehouseService.findOne(new_warehouse_id).get());

            return carRepository.save(carEntity);
        }
        return null;
    }

    @Override
    public List<CarEntity> findAll()
    {
        return StreamSupport
                .stream(
                        carRepository.findAll().spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CarEntity> findOne(Long car_id)
    {
        return carRepository.findById(car_id);
    }

    @Override
    public boolean isExists(Long car_id)
    {
        return carRepository.existsById(car_id);
    }

    @Override
    public void delete(Long car_id)
    {
        if(this.isExists(car_id))
        {
            if(warehouseService.isExists(this.findOne(car_id).get().getWarehouseEntity().getId()))
            {
                Map<Object, Object> map = new HashMap<>();
                map.put("carsStored", this.findOne(car_id).get().getWarehouseEntity().getCarsStored()-1);
                warehouseService.partialUpdate(this.findOne(car_id).get().getWarehouseEntity().getId(), map);
            }

            carRepository.deleteById(car_id);
        }
    }
}
