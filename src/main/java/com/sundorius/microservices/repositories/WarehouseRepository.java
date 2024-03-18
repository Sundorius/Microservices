package com.sundorius.microservices.repositories;

import com.sundorius.microservices.domain.enitities.WarehouseEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  WarehouseRepository extends CrudRepository<WarehouseEntity, Long> {

    @Query("SELECT a from WarehouseEntity a where a.maxCapacity < ?1")
    Iterable<WarehouseEntity> maxCapacityLessThan(int maxCapacity);

    Iterable<WarehouseEntity> findByWnumber(String wnumber);

    Iterable<WarehouseEntity> findByPlace(String place);

    @Query("SELECT a from WarehouseEntity a where a.carsStored < ?1")
    Iterable<WarehouseEntity> carStoredLessThan(int carsStored);

}
