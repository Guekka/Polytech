import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QMC {
    private Map<MinTerm, Boolean> terms;

    public QMC(List<Integer> positiveTerms, int bitCount) {
        var termsCount = (int) Math.round(Math.pow(2, bitCount));
        terms = IntStream.range(0, termsCount).mapToObj(i -> new MinTerm(i, bitCount)).collect(Collectors.toMap(t -> t, t -> false));

        for (var index : positiveTerms)
            terms.put(new MinTerm(index, bitCount), true);
    }

    private static Map<Integer, List<MinTerm>> group(List<MinTerm> ungrouped) {
        return ungrouped.stream().collect(Collectors.groupingBy(MinTerm::countUnchecked));
    }

    public void merge() {

    }

    public Map<MinTerm, Boolean> terms() {
        return terms;
    }
}
