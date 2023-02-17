package fr.epu.bicycle;

import java.util.HashMap;
import java.util.Map;


// TODO finish that class
public class FleetManager {
    private final Map<VehicleType, Fleet<Trackable>> fleets;

    public FleetManager(VehicleType... vehicleTypes) {
        fleets = new HashMap<>();
        for (VehicleType vehicleType : vehicleTypes) {
            fleets.put(vehicleType, new Fleet<>());
        }
    }
}
