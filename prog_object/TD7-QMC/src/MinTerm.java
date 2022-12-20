import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record MinTerm(List<MinTermState> data) {
    public MinTerm(int value, int count) {
        this(parse(value, count));
    }

    public MinTerm(int count) {
        this(0, count);
    }

    public int countUnchecked() {
        return (int) data.stream().filter(bit -> bit.equals(MinTermState.UNCHECKED)).count();
    }

    public MinTermState getBit(int idx) {
        return data.get(idx);
    }

    public static List<MinTermState> parse(int value, int count) {
        if(Math.pow(2, count) <= value) {
            throw new IllegalArgumentException("Value is too big");
        }

        var data = new ArrayList<MinTermState>();
        while(value != 0)
        {
            var lastBit = (value & 1) != 0;
            var c = lastBit ? MinTermState.CHECKED : MinTermState.UNCHECKED;
            data.add(c);
            value /= 2;
        }
        for(int i = data.size(); i < count; ++i) {
            data.add(MinTermState.UNCHECKED);
        }
        // the array is reversed since we iterated from the right
        Collections.reverse(data);
        return data;
    }

    public Optional<MinTerm> merge(MinTerm other) {
        if(data.size() != other.data().size()) {
            throw new IllegalArgumentException("MinTerms have different sizes");
        }
        var countUnchecked = countUnchecked();
        var otherCountUnchecked = other.countUnchecked();
        if(Math.abs(countUnchecked - otherCountUnchecked) > 1) {
            throw new IllegalArgumentException("Cannot merge terms with more than one bit difference");
        }

        var newData = new ArrayList<MinTermState>();
        for (int i = 0; i < data.size(); ++i ) {
            var newBit = mergeBit(getBit(i), other.getBit(i));
            if(newBit.isEmpty())
                return Optional.empty();
            newData.add(newBit.get());
        }
        return Optional.of(new MinTerm(newData));
    }

    private static Optional<MinTermState> mergeBit(MinTermState lhs, MinTermState rhs) {
        if(lhs == rhs)
            return Optional.of(lhs);

        if(lhs == MinTermState.CHECKED && rhs == MinTermState.UNCHECKED)
            return Optional.of(MinTermState.DONT_CARE);
        if (lhs == MinTermState.UNCHECKED && rhs == MinTermState.CHECKED)
            return Optional.of(MinTermState.DONT_CARE);

        return Optional.empty();
    }
}
