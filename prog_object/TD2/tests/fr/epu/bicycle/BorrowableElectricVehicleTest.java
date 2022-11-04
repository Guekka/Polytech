package fr.epu.bicycle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BorrowableElectricVehicleTest {

    @Test
    void borrow() {
        BorrowableElectricVehicle vehicle = new BorrowableElectricVehicle(new Battery(50));
        vehicle.charge(50);
        vehicle.borrow();
    }

    @Test
    void stopBorrow() {
        BorrowableElectricVehicle vehicle = new BorrowableElectricVehicle(new Battery(50));
        vehicle.charge(50);
        vehicle.borrow();
        vehicle.stopBorrow();
    }

    @Test
    void borrowNotEnoughCharge() {
        BorrowableElectricVehicle vehicle = new BorrowableElectricVehicle(new Battery(10));
        assertThrows(IllegalStateException.class, vehicle::borrow);
    }

    @Test
    void borrowAlreadyBorrowed() {
        BorrowableElectricVehicle vehicle = new BorrowableElectricVehicle(new Battery(50));
        vehicle.charge(50);
        vehicle.borrow();
        assertThrows(IllegalStateException.class, vehicle::borrow);
    }
}