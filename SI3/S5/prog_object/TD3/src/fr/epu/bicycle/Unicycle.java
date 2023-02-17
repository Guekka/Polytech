package fr.epu.bicycle;

public class Unicycle extends ElectricVehicle {
    public Unicycle(Battery battery) {
        super(battery);
    }

    public Unicycle() {
        this(new Battery(Battery.MAX_CHARGE));
    }
}
