package fr.epu.bicycle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FleetTest {
    private Fleet fleet;

    @BeforeEach
    void setUp() {
        fleet = new Fleet();

    }

    @AfterEach
    void tearDown() {
        fleet = null;
    }

    @Test
    void testAddBike() {
        fleet.addVehicle(new EBike(new Battery(50)));
        assertEquals(1, fleet.numberOfVehicles());
    }

    @Test
    void testGetVehiclesAroundAPosition() {
        fleet.addVehicle(new EBike(new Battery(100)));
        fleet.addVehicle(new EBike(new Battery(100)));
        fleet.addVehicle(new EBike(new Battery(100)));
        assertEquals(3, fleet.getVehiclesAround(new Position(0, 0), 5).size());
    }

    @Test
    void evaluateTime4around() {
        long totalTime = 0;
        for (int i = 0; i < 1000; i++) {
            fleet.addVehicle(new EBike(new Battery(100)));
        }
        Position currentPosition = new Position(7, 7);
        for (int i = 0; i < 1000; i++) {
            long startTime = System.nanoTime();
            fleet.getVehiclesAround(currentPosition, 10);
            long endTime = System.nanoTime();
            long durationInNano = (endTime - startTime);  //Total execution time in nano seconds
            totalTime += durationInNano;
        }
        System.out.println("total time in nano : " + totalTime);
        System.out.println("total time in milli : " + TimeUnit.NANOSECONDS.toMillis(totalTime));
    }

}