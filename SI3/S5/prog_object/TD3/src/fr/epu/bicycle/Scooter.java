package fr.epu.bicycle;

public class Scooter extends BorrowableElectricVehicle {
    private final float maxSpeed;

    public Scooter(Battery battery, float maxSpeed) {
        super(battery);
        this.maxSpeed = maxSpeed;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public boolean isBorrowable() {
        return false;
    }
}
