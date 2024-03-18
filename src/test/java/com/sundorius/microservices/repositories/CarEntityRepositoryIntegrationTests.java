package com.sundorius.microservices.repositories;

import com.sundorius.microservices.TestDataUtil;
import com.sundorius.microservices.domain.enitities.CarEntity;
import com.sundorius.microservices.domain.enitities.WarehouseEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class CarEntityRepositoryIntegrationTests
{

    private final CarRepository carRepositoryTest;
    private final WarehouseRepository warehouseRepositoryTest;

    @Autowired
    public CarEntityRepositoryIntegrationTests(WarehouseRepository warehouseRepositoryTest,
            CarRepository carRepository) {
        this.warehouseRepositoryTest = warehouseRepositoryTest;
        this.carRepositoryTest = carRepository;
    }

    @Test
    public void testThatCarCanBeCreatedAndRecalled()
    {
        WarehouseEntity warehouseEntity = TestDataUtil.createTestWarehouseA();
        CarEntity car = TestDataUtil.createTestCarA(warehouseEntity);
        warehouseRepositoryTest.save(warehouseEntity);
        carRepositoryTest.save(car);
        Optional<CarEntity> result = carRepositoryTest.findById(car.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(car);
    }

    @Test
    public void multipleCarsCanBeCreatedAndRecalled()
    {
        WarehouseEntity warehouseEntityA = TestDataUtil.createTestWarehouseA();
        WarehouseEntity warehouseEntityB = TestDataUtil.createTestWarehouseB();
        warehouseRepositoryTest.save(warehouseEntityA);
        warehouseRepositoryTest.save(warehouseEntityB);
        CarEntity carA = TestDataUtil.createTestCarA(warehouseEntityA);
        carRepositoryTest.save(carA);
        CarEntity carB = TestDataUtil.createTestCarB(warehouseEntityA);
        carRepositoryTest.save(carB);
        CarEntity carC = TestDataUtil.createTestCarC(warehouseEntityB);
        carRepositoryTest.save(carC);
        CarEntity carD = TestDataUtil.createTestCarD(warehouseEntityB);
        carRepositoryTest.save(carD);

        Iterable<CarEntity> result = carRepositoryTest.findAll();
        assertThat(result)
                .hasSize(4)
                .containsExactly(carA, carB, carC, carD);
    }

    @Test
    public void testThatCarCanBeUpdated() 
    {
        WarehouseEntity warehouseEntity = TestDataUtil.createTestWarehouseA();
        CarEntity car = TestDataUtil.createTestCarA(warehouseEntity);
        car.setMake("UPDATED");
        warehouseRepositoryTest.save(warehouseEntity);
        carRepositoryTest.save(car);
        Optional<CarEntity> result = carRepositoryTest.findById(car.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(car);
    }

    @Test
    public void testThatCarCanBeDeleted() 
    {
        WarehouseEntity warehouseEntity = TestDataUtil.createTestWarehouseA();
        CarEntity car = TestDataUtil.createTestCarA(warehouseEntity);
        warehouseRepositoryTest.save(warehouseEntity);
        carRepositoryTest.save(car);
        carRepositoryTest.deleteById(car.getId());
        Optional<CarEntity> result = carRepositoryTest.findById(car.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testGetCarsWithHpLessThan() 
    {
        WarehouseEntity warehouseEntity = TestDataUtil.createTestWarehouseA();
        warehouseRepositoryTest.save(warehouseEntity);
        CarEntity carB = TestDataUtil.createTestCarB(warehouseEntity);
        carRepositoryTest.save(carB);
        CarEntity carC = TestDataUtil.createTestCarC(warehouseEntity);
        carRepositoryTest.save(carC);

        Iterable<CarEntity> result = carRepositoryTest.hpLessThan(90);
        assertThat(result)
                .hasSize(1)
                .containsExactly(carC);
    }

    @Test
    public void testGetCarsByColor() 
    {
        WarehouseEntity warehouseEntity = TestDataUtil.createTestWarehouseA();
        warehouseRepositoryTest.save(warehouseEntity);
        CarEntity carA = TestDataUtil.createTestCarA(warehouseEntity);
        carRepositoryTest.save(carA);
        CarEntity carB = TestDataUtil.createTestCarB(warehouseEntity);
        carRepositoryTest.save(carB);

        Iterable<CarEntity> result = carRepositoryTest.findByColor("red");
        assertThat(result)
                .hasSize(1)
                .containsExactly(carB);
    }

    @Test
    public void testGetCarsByFuel() 
    {
        WarehouseEntity warehouseEntity = TestDataUtil.createTestWarehouseA();
        warehouseRepositoryTest.save(warehouseEntity);
        CarEntity carA = TestDataUtil.createTestCarA(warehouseEntity);
        carRepositoryTest.save(carA);
        CarEntity carB = TestDataUtil.createTestCarB(warehouseEntity);
        carRepositoryTest.save(carB);

        Iterable<CarEntity> result = carRepositoryTest.findByFuelType("diesel");
        assertThat(result)
                .hasSize(1)
                .containsExactly(carA);
    }

    @Test
    public void testGetCarsByMake() 
    {
        WarehouseEntity warehouseEntity = TestDataUtil.createTestWarehouseA();
        warehouseRepositoryTest.save(warehouseEntity);
        CarEntity carA = TestDataUtil.createTestCarA(warehouseEntity);
        carRepositoryTest.save(carA);
        CarEntity carB = TestDataUtil.createTestCarB(warehouseEntity);
        carRepositoryTest.save(carB);
        CarEntity carC = TestDataUtil.createTestCarC(warehouseEntity);
        carRepositoryTest.save(carC);

        Iterable<CarEntity> result = carRepositoryTest.findByMake("Opel");
        assertThat(result)
                .hasSize(2)
                .containsExactly(carA, carB);
    }

    @Test
    public void testGetCarsByModel() 
    {
        WarehouseEntity warehouseEntity = TestDataUtil.createTestWarehouseA();
        warehouseRepositoryTest.save(warehouseEntity);
        CarEntity carA = TestDataUtil.createTestCarA(warehouseEntity);
        carRepositoryTest.save(carA);
        CarEntity carB = TestDataUtil.createTestCarB(warehouseEntity);
        carRepositoryTest.save(carB);
        CarEntity carC = TestDataUtil.createTestCarC(warehouseEntity);
        carRepositoryTest.save(carC);

        Iterable<CarEntity> result = carRepositoryTest.findByModel("Corsa");
        assertThat(result)
                .hasSize(1)
                .containsExactly(carA);
    }
}
