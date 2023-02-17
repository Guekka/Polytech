package fr.epu.bicycle;

import java.util.ArrayList;
import java.util.List;

public class Fleet {
    List<Vehicle> vehicles = new ArrayList<>();

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public int numberOfVehicles() {
        return vehicles.size();
    }

    public List<Vehicle> getVehiclesAround(Position center, double radius) {
        return vehicles
                .stream()
                .filter(Borrowable::isBorrowable)
                .filter(b -> b.getPosition().map(p -> p.distance(center) < radius).orElse(false))
                .toList();
    }
}
