package fr.epu.bicycle;

import java.util.ArrayList;
import java.util.List;

public class Station {
    List<Bike> bikes = new ArrayList<>();

    Position pos;

    public Station(Position p) {
        this.pos = p;
    }

    public void addBike(Bike bike) {
        this.bikes.add(bike);
    }

    public void removeBike(Bike bike) {
        this.bikes.remove(bike);
    }

    public Position getPosition() {
        return this.pos;
    }
}
