package fr.epu.bicycle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FleetOfTrackable<T extends Trackable> {
    List<T> trackables = new ArrayList<>();

    List<Position> getPositions() {
        return trackables
                .stream()
                .map(Trackable::getPosition)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public List<T> getTrackables() {
        return trackables;
    }

    public List<T> closeTo(Position center, double radius) {
        return trackables
                .stream()
                .filter(b -> b.getPosition().map(p -> p.distance(center) < radius).orElse(false))
                .toList();
    }

    public void addVehicle(T vehicle) {
        trackables.add(vehicle);
    }

    public int numberOfVehicles() {
        return trackables.size();
    }
}
