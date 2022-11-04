package fr.epu.bicycle;

import java.util.List;

public class Fleet<T extends Vehicle> extends FleetOfTrackable<T> {
    public List<T> getVehiclesAround(Position center, double radius) {
        return super.closeTo(center, radius).stream().filter(Vehicle::isBorrowable).toList();
    }
}
