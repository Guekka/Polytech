package fr.epu.bicycle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BatteryTest {
    private Battery battery;

    @BeforeEach
    void setUp() {
        battery = new Battery(0);
    }

    @Test
    void getCharge() {
        assertEquals(0, battery.getCharge());
    }

    @Test
    void charge() {
        battery.charge(10);
        assertEquals(10, battery.getCharge());
    }

    @Test
    void chargeMax() {
        battery.charge(Battery.MAX_CHARGE + 10);
        assertEquals(Battery.MAX_CHARGE, battery.getCharge());
    }
}