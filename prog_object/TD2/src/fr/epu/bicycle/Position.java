package fr.epu.bicycle;

/**
 * 2D Position
 *
 * @author Edgar B
 */
public record Position(float x, float y) {
    private static final double EPSILON = 0.001;

    public boolean isEquivalent(Position other) {
        return Math.abs(this.x - other.x) < EPSILON && Math.abs(this.y - other.y) < EPSILON;
    }

    public double distance(Position other) {
        return Math.sqrt(Math.pow(other.y - this.y, 2) + Math.pow(other.x - this.x, 2));
    }
}
