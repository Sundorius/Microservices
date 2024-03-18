package com.sundorius.microservices.repositories;

import com.sundorius.microservices.domain.enitities.CarEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<CarEntity, Long>
{
    @Query("SELECT a from CarEntity a where a.hp < ?1")
    Iterable<CarEntity> hpLessThan(int hp);

    Iterable<CarEntity> findByColor(String color);

    Iterable<CarEntity> findByFuelType(String fuel_type);

    Iterable<CarEntity> findByMake(String make);

    Iterable<CarEntity> findByModel(String model);
}
