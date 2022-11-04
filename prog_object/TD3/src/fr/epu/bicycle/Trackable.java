package fr.epu.bicycle;

import java.util.Optional;

public interface Trackable extends Vehicle {
    Optional<Position> getPosition();
}
