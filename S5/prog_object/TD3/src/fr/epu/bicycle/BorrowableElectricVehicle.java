package fr.epu.bicycle;

public class BorrowableElectricVehicle extends ElectricVehicle implements Vehicle {
    private boolean borrowed = false;

    public BorrowableElectricVehicle(Battery battery) {
        super(battery);
    }

    @Override
    public boolean isBorrowable() {
        return getCharge() >= 20 && !this.borrowed;
    }

    @Override
    public void borrow() {
        if (this.isBorrowable()) {
            this.borrowed = true;
        } else {
            throw new IllegalStateException("Cannot borrow this vehicle");
        }
    }

    @Override
    public void stopBorrow() {
        this.borrowed = false;
    }
}
