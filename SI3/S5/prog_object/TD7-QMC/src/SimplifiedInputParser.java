import java.util.Arrays;
import java.util.List;

public record SimplifiedInputParser(List<Integer> values) {
    public SimplifiedInputParser(String input) {
        this(Arrays.stream(input.split(" ")).distinct().map(Integer::valueOf).sorted().toList());
    }

    public int bitCount() {
        if (values.isEmpty()) {
            return 0;
        }
        var biggest = values.get(values.size() - 1);

        int len = 0;

        while (biggest != 0) {
            len += 1;
            biggest /= 2;
        }

        return len;
    }
}
