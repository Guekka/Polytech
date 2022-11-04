package fr.epu.bicycle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PositionTest {
    private Position position1;
    private Position position2;

    @Test
    void testEquals() {
        assertEquals(position1, position2);
    }

    @Test
    void testHashCode() {
        assertEquals(position1.hashCode(), position2.hashCode());
    }

    @Test
    void getX() {
        assertEquals(0, position1.x());
    }

    @Test
    void getY() {
        assertEquals(0, position1.y());
    }


    @BeforeEach
    void setUp() {
        position1 = new Position(0, 0);
        position2 = new Position(0, 0);
    }

    @Test
    void toStringTest() {
        System.out.println(position1.toString());
        assertTrue(true);

    }

    @Nested
    class testDistance {
        @Test
        void testSameDistance() {
            Position position1 = new Position(0, 0);
            Position position2 = new Position(0, 0);
            assertEquals(0, position1.distance(position2));
        }

        @Test
        void testDifferentDistance() {
            Position position1 = new Position(0, 0);
            Position position2 = new Position(0, 10);
            assertEquals(10, position1.distance(position2));
        }

    }

}

