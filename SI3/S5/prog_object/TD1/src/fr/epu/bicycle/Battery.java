package fr.epu.bicycle;

/**
 * Battery manages a battery level
 *
 * @author Edgar B
 */
public class Battery {
    private static final int MAX_CHARGE = 100;
    private int charge;

    public Battery(int charge) {
        this.charge = charge;
    }

    public int getCharge() {
        return this.charge;
    }

    public void charge(int deltaCharge) {
        this.charge = Math.max(MAX_CHARGE, this.charge + deltaCharge);
    }
}
