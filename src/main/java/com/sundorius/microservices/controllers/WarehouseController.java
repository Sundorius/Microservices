package com.sundorius.microservices.controllers;

import com.sundorius.microservices.domain.dto.WarehouseDto;
import com.sundorius.microservices.domain.enitities.WarehouseEntity;
import com.sundorius.microservices.services.WarehouseService;
import com.sundorius.microservices.mappers.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class WarehouseController
{

    private WarehouseService warehouseService;

    private Mapper<WarehouseEntity, WarehouseDto> warehouseMapper;

    public WarehouseController(WarehouseService warehouseService, Mapper<WarehouseEntity, WarehouseDto> warehouseMapper)
    {
        this.warehouseService = warehouseService;
        this.warehouseMapper = warehouseMapper;
    }

    @PostMapping(path = "/warehouses")
    public ResponseEntity<WarehouseDto> createWarehouse(@RequestBody WarehouseDto warehouseDto)
    {
        WarehouseEntity authorEntity = warehouseMapper.mapFrom(warehouseDto);
        WarehouseEntity savedAuthorEntity = warehouseService.create(authorEntity);
        return new ResponseEntity<>(warehouseMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/warehouses")
    public List<WarehouseDto> listWarehouses()
    {
        List<WarehouseEntity> warehouses = warehouseService.findAll();
        return warehouses.stream()
                .map(warehouseMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/warehouses/{id}")
    public ResponseEntity<WarehouseDto> getWarehouse(@PathVariable("id") Long warehouse_id)
    {
        Optional<WarehouseEntity> foundWarehouse = warehouseService.findOne(warehouse_id);
        return foundWarehouse.map(warehouseEntity ->
        {
            WarehouseDto warehouseDto = warehouseMapper.mapTo(warehouseEntity);
            return new ResponseEntity<>(warehouseDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/warehouses/{id}")
    public ResponseEntity<WarehouseDto> fullUpdateWarehouse(@PathVariable("id") Long warehouse_id,
                                                            @RequestBody WarehouseDto warehouseDto)
    {
        if(!warehouseService.isExists(warehouse_id))
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        warehouseDto.setId(warehouse_id);
        WarehouseEntity authorEntity = warehouseMapper.mapFrom(warehouseDto);
        WarehouseEntity savedAuthorEntity = warehouseService.create(authorEntity);
        return new ResponseEntity<>(
                warehouseMapper.mapTo(savedAuthorEntity),
                HttpStatus.OK);
    }

    @PatchMapping(path = "/warehouses/{id}")
    public ResponseEntity<WarehouseDto> update(@PathVariable("id") Long warehouse_id,
                                               @RequestBody Map<Object, Object> objectMap)
    {
        if(!warehouseService.isExists(warehouse_id))
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                warehouseMapper.mapTo(warehouseService.partialUpdate(warehouse_id, objectMap)),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/warehouses/{id}")
    public ResponseEntity deleteWarehouse(@PathVariable("id") Long warehouse_id)
    {
        warehouseService.delete(warehouse_id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}