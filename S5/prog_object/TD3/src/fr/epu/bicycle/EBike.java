package fr.epu.bicycle;

/**
 * EBike implements the computer part of an EBike that would have a battery and a location.
 *
 * @author Edgar B
 */
public class EBike extends BorrowableElectricVehicle {
    static final int INITIAL_DISTANCE = 1;
    private double km = INITIAL_DISTANCE;

    public EBike(Battery battery) {
        super(battery);
    }

    public double getKm() {
        return km;
    }

    public void setKm(double totalKm) {
        this.km = totalKm;
    }

    /**
     * Modifies the number of km traveled by adding <code>nbKmToAdd</code> km.
     *
     * @param nbKmToAdd
     */
    public void addKm(double nbKmToAdd) {
        if (nbKmToAdd > 0)
            this.km += nbKmToAdd;
    }

    public Station getStation() {
        return this.getStation();
    }
}

