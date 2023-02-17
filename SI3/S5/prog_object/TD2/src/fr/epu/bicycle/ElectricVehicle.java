package fr.epu.bicycle;

import java.util.Optional;

public class ElectricVehicle implements Trackable {
    static final int INITIAL_CHARGE = 100;
    private final GPS gps = new GPS();
    private final Battery battery;

    public ElectricVehicle(Battery battery) {
        this.battery = battery;
    }

    public Optional<Position> getPosition() {
        return Optional.of(this.gps.getPosition());
    }

    public int getCharge() {
        return this.battery.getCharge();
    }

    public void charge(int amount) {
        this.battery.charge(amount);
    }
}
