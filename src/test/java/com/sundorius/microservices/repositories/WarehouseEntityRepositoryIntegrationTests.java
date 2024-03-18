package com.sundorius.microservices.repositories;

import com.sundorius.microservices.TestDataUtil;
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
public class WarehouseEntityRepositoryIntegrationTests 
{

    private final WarehouseRepository warehouseRepositoryTest;

    @Autowired
    public WarehouseEntityRepositoryIntegrationTests(WarehouseRepository warehouseRepository) {
        this.warehouseRepositoryTest = warehouseRepository;
    }

    @Test
    public void testThatWarehouseCanBeCreatedAndRecalled()
    {
        WarehouseEntity warehouseEntity = TestDataUtil.createTestWarehouseA();
        warehouseRepositoryTest.save(warehouseEntity);
        Optional<WarehouseEntity> result = warehouseRepositoryTest.findById(warehouseEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(warehouseEntity);
    }

    @Test
    public void multipleWarehousesCanBeCreatedAndRecalled()
    {
        WarehouseEntity warehouseEntityA = TestDataUtil.createTestWarehouseA();
        warehouseRepositoryTest.save(warehouseEntityA);
        WarehouseEntity warehouseEntityB = TestDataUtil.createTestWarehouseB();
        warehouseRepositoryTest.save(warehouseEntityB);

        Iterable<WarehouseEntity> result = warehouseRepositoryTest.findAll();
        assertThat(result)
                .hasSize(2)
                .containsExactly(warehouseEntityA, warehouseEntityB);
    }

    @Test
    public void testThatWarehouseCanBeUpdated()
    {
        WarehouseEntity warehouseEntity = TestDataUtil.createTestWarehouseA();
        warehouseEntity.setWnumber("UPDATED");
        warehouseRepositoryTest.save(warehouseEntity);
        Optional<WarehouseEntity> result = warehouseRepositoryTest.findById(warehouseEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(warehouseEntity);
    }

    @Test
    public void testThatWarehouseCanBeDeleted()
    {
        WarehouseEntity warehouseEntity = TestDataUtil.createTestWarehouseA();
        warehouseRepositoryTest.save(warehouseEntity);
        warehouseRepositoryTest.deleteById(warehouseEntity.getId());
        Optional<WarehouseEntity> result = warehouseRepositoryTest.findById(warehouseEntity.getId());
        assertThat(result).isEmpty();
    }

    @Test
        public void testThatGetWarehouseWithMaxCapacityLessThan()
        {
            WarehouseEntity warehouseEntityA = TestDataUtil.createTestWarehouseA();
            warehouseRepositoryTest.save(warehouseEntityA);
            WarehouseEntity warehouseEntityB = TestDataUtil.createTestWarehouseB();
            warehouseRepositoryTest.save(warehouseEntityB);

            Iterable<WarehouseEntity> result = warehouseRepositoryTest.maxCapacityLessThan(70);
            assertThat(result)
                    .hasSize(1)
                    .containsExactly(warehouseEntityB);
    }

    @Test
    public void testGetWarehouseWithCarsStoredLessThan()
    {
        WarehouseEntity warehouseEntityA = TestDataUtil.createTestWarehouseA();
        warehouseEntityA.setCarsStored(2);
        warehouseRepositoryTest.save(warehouseEntityA);
        WarehouseEntity warehouseEntityB = TestDataUtil.createTestWarehouseB();
        warehouseRepositoryTest.save(warehouseEntityB);

        Iterable<WarehouseEntity> result = warehouseRepositoryTest.carStoredLessThan(2);
        assertThat(result)
                .hasSize(1)
                .containsExactly(warehouseEntityB);
    }

    @Test
    public void testGetWarehouseByPlace()
    {
        WarehouseEntity warehouseEntityA = TestDataUtil.createTestWarehouseA();
        warehouseRepositoryTest.save(warehouseEntityA);
        WarehouseEntity warehouseEntityB = TestDataUtil.createTestWarehouseB();
        warehouseRepositoryTest.save(warehouseEntityB);

        Iterable<WarehouseEntity> result = warehouseRepositoryTest.findByPlace("Heraklion");
        assertThat(result)
                .hasSize(1)
                .containsExactly(warehouseEntityA);
    }
}
