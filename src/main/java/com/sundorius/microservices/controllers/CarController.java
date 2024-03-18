package com.sundorius.microservices.controllers;

import com.sundorius.microservices.domain.dto.CarDto;
import com.sundorius.microservices.domain.enitities.CarEntity;
import com.sundorius.microservices.services.CarService;
import com.sundorius.microservices.mappers.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CarController
{
    private CarService carService;
    private Mapper<CarEntity, CarDto> carMapper;

    public CarController(Mapper<CarEntity, CarDto> carMapper, CarService bookService)
    {
        this.carMapper = carMapper;
        this.carService = bookService;
    }

    @PostMapping(path = "/cars/{warehouse_id}")
    public ResponseEntity<CarDto> createCar(@PathVariable("warehouse_id") Long warehouse_id,
                                            @RequestBody CarDto carDto)
    {
        CarEntity carEntity = carMapper.mapFrom(carDto);
        CarEntity savedCarEntity = carService.create(warehouse_id, carEntity);

        if(savedCarEntity == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(savedCarEntity, HttpStatus.CREATED);
    }

    @PutMapping(path = "/cars/{car_id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable("car_id") Long car_id,
                                            @RequestBody CarDto carDto)
    {
        if(!carService.isExists(car_id))
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        carDto.setId(car_id);
        CarEntity carEntity = carMapper.mapFrom(carDto);
        CarEntity savedCarEntity = carService.update(car_id,carEntity);
        return new ResponseEntity<>(
                carMapper.mapTo(savedCarEntity),
                HttpStatus.OK);
    }

    @PutMapping(path = "/cars/warehouse/{car_id}/{warehouse_id}")
    public ResponseEntity<CarDto> updateCarWarehouse(@PathVariable("car_id") Long car_id,
                                                     @PathVariable("warehouse_id") Long warehouse_id)
    {
        if(!carService.isExists(car_id))
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CarEntity savedCarEntity = carService.changeWarehouse(car_id, warehouse_id);
        return new ResponseEntity<>(
                carMapper.mapTo(savedCarEntity),
                HttpStatus.OK);
    }

    @PatchMapping(path = "/cars/{car_id}")
    public ResponseEntity<CarDto> partialUpdateCar(@PathVariable("car_id") Long car_id,
                                                   @RequestBody Map<Object, Object> objectMap )
    {
        if(!carService.isExists(car_id))
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CarEntity updatedCarEntity = carService.partialUpdate(car_id, objectMap);
        return new ResponseEntity<>(
                carMapper.mapTo(updatedCarEntity),
                HttpStatus.OK);

    }


    @GetMapping(path = "/cars")
    public List<CarDto> listCars()
    {
        List<CarEntity> cars = carService.findAll();
        return cars.stream()
                .map(carMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/cars/{car_id}")
    public ResponseEntity<CarDto> getCar(@PathVariable("car_id") Long car_id)
    {
        Optional<CarEntity> foundCar = carService.findOne(car_id);
        return foundCar.map(carEntity -> {
            CarDto carDto = carMapper.mapTo(carEntity);
            return new ResponseEntity<>(carDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/cars/{car_id}")
    public ResponseEntity deleteCar(@PathVariable("car_id") Long car_id)
    {
        carService.delete(car_id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
