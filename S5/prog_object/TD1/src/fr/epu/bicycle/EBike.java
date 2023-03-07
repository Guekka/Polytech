package fr.epu.bicycle;

/**
 * EBike implements the computer part of an EBike that would have a battery and a location.
 *
 * @author Edgar B
 */
public class EBike {
    static final int INITIAL_DISTANCE = 1;
    static final int INITIAL_CHARGE = 100;
    private GPS gps;
    private int km;
    private Battery battery;

    public EBike() {
        this(INITIAL_DISTANCE);
    }

    public EBike(int km) {
        this.km = km;
    }

    public Position getPosition() {
        return this.gps.getPosition();
    }

    public int getKm() {
        return km;
    }

    public void setKm(int totalKm) {
        this.km = totalKm;
    }

    /**
     * Modifies the number of km traveled by adding <code>nbKmToAdd</code> km.
     *
     * @param nbKmToAdd
     */
    public void addKm(int nbKmToAdd) {
        if (nbKmToAdd > 0)
            this.km += nbKmToAdd;
    }

    public int getCharge() {
        return this.battery.getCharge();
    }
}

