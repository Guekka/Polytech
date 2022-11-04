package fr.epu.bicycle;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FleetOfEBike extends FleetOfTrackable<EBike> {
    public FleetOfEBike() {
        super();
    }

    public List<EBike> EBikesWithMileageOver(int km) {
        return getTrackables().stream().filter(b -> b.getKm() > km).toList();
    }

    Set<Station> stationAround(Position currentPosition, int maxKm) {
        return getTrackables()
                .stream()
                .map(EBike::getStation)
                .distinct()
                .filter(s -> s.getPosition().distance(currentPosition) < maxKm)
                .collect(Collectors.toSet());
    }
}

