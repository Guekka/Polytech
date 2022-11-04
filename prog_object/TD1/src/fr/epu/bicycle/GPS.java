package fr.epu.bicycle;

/**
 * GPS provides a position
 *
 * @author Edgar B
 */
public class GPS {
    private Position position;

    public Position getPosition() {
        return this.position;
    }

    public void move(float dx, float dy) {
        this.position = new Position(position.x() + dx, position.y() + dy);
    }
}
