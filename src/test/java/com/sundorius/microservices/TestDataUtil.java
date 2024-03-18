package com.sundorius.microservices;


import com.sundorius.microservices.domain.enitities.CarEntity;
import com.sundorius.microservices.domain.enitities.WarehouseEntity;

public final class TestDataUtil {
    private TestDataUtil() {
    }

    public static WarehouseEntity createTestWarehouseA() {
        return WarehouseEntity.builder()
                .id(1L)
                .wnumber("W1")
                .place("Heraklion")
                .maxCapacity(100)
                .carsStored(0)
                .build();
    }

    public static WarehouseEntity createTestWarehouseB() {
        return WarehouseEntity.builder()
                .id(2L)
                .wnumber("W2")
                .place("Athens")
                .maxCapacity(60)
                .carsStored(0)
                .build();
    }


    public static CarEntity createTestCarA(final WarehouseEntity warehouseEntity) {
        return CarEntity.builder()
                .id(1L)
                .identCode(100)
                .make("Opel")
                .model("Corsa")
                .color("black")
                .fuelType("diesel")
                .hp(75)
                .warehouseEntity(warehouseEntity)
                .build();
    }

    public static CarEntity createTestCarB(final WarehouseEntity warehouseEntity) {
        return CarEntity.builder()
                .id(2L)
                .identCode(200)
                .make("Opel")
                .model("Astra")
                .color("red")
                .fuelType("petrol")
                .hp(100)
                .warehouseEntity(warehouseEntity)
                .build();
    }

    public static CarEntity createTestCarC(final WarehouseEntity warehouseEntity) {
        return CarEntity.builder()
                .id(3L)
                .identCode(300)
                .make("VW")
                .model("Polo")
                .color("white")
                .fuelType("diesel")
                .hp(80)
                .warehouseEntity(warehouseEntity)
                .build();
    }

    public static CarEntity createTestCarD(final WarehouseEntity warehouseEntity) {
        return CarEntity.builder()
                .id(4L)
                .identCode(400)
                .make("VW")
                .model("Golf")
                .color("blue")
                .fuelType("petrol")
                .hp(400)
                .warehouseEntity(warehouseEntity)
                .build();
    }
}
