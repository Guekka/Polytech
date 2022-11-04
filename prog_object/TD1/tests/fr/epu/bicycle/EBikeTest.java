package fr.epu.bicycle;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EBikeTest {
    @org.junit.jupiter.api.Test
    void testCreateEBike() {
        EBike bike = new EBike();
        assertEquals(EBike.INITIAL_DISTANCE, bike.getKm());
    }

    @org.junit.jupiter.api.Test
    void testAddKm() {
        EBike bike = new EBike();
        int value = 5;
        bike.addKm(value);
        assertEquals(value + EBike.INITIAL_DISTANCE, bike.getKm());
    }

    @org.junit.jupiter.api.Test
    void testAddNegativeKm() {
        EBike bike = new EBike();
        int value = -5;
        bike.addKm(value);
        assertEquals(EBike.INITIAL_DISTANCE, bike.getKm());
    }
}