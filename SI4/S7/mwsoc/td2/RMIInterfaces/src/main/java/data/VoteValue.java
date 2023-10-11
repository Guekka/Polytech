package data;

import java.io.Serializable;

public enum VoteValue implements Serializable {
    ONE,
    TWO,
    THREE,
    FOUR,
    ;

    public static VoteValue fromValue(int vote) {
        return switch (vote) {
            case 1 -> ONE;
            case 2 -> TWO;
            case 3 -> THREE;
            case 4 -> FOUR;
            default -> throw new IllegalArgumentException("Invalid vote value: " + vote);
        };
    }

    public Integer value() {
        return switch (this) {
            case ONE -> 1;
            case TWO -> 2;
            case THREE -> 3;
            case FOUR -> 4;
        };
    }
}
