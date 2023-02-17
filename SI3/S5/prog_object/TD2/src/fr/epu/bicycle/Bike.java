package fr.epu.bicycle;

import java.util.Optional;

public class Bike implements Vehicle {
    private final Station station;

    private boolean borrowed = false;

    public Bike(Station station) {
        this.station = station;
    }

    @Override
    public Optional<Position> getPosition() {
        return Optional.of(station.getPosition());
    }

    @Override
    public boolean isBorrowable() {
        return !this.borrowed;
    }

    public void borrow() {
        if (this.isBorrowable()) {
            station.removeBike(this);
            this.borrowed = true;
        } else {
            throw new IllegalStateException("Cannot borrow this vehicle");
        }
    }

    @Override
    public void stopBorrow() {
        station.addBike(this);
        this.borrowed = false;
    }
}
