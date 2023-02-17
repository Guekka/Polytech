package fr.epu.bicycle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ScooterTest {

    @Test
    void test() {
        Scooter scooter = new Scooter(new Battery(50), 50);
        assertEquals(50, scooter.getMaxSpeed());
    }


}