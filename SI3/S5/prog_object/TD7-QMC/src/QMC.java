import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QMC {


    private final int[] values;
    private final int bitCount;

    /**
     * Initialize the algorithm
     *
     * @param values decimals
     */
    public QMC(int... values) {
        this.values = values;
        this.bitCount = Minterm.numberOfBitsNeeded(Arrays.stream(values).max().orElse(0));
    }

    /**
     * Calculates and returns the necessary and sufficient minterms.
     */
    public List<Minterm> computePrimeImplicants() {
        var res = new ArrayList<Minterm>();

        // Create the initial minterms
        List<Minterm> minterms = new ArrayList<Minterm>();
        for (int i : values) {
            minterms.add(new Minterm(i, bitCount));
        }

        while (true) {
            var manager = new CategoryManager(minterms, bitCount);
            // Merge the categories
            minterms = manager.mergeCategories();
            if (manager.isLastTurn()) {
                break;
            }
        }

        var chart = new PrimeImplicantChart(values, minterms);
        res.addAll(chart.extractEssentialPrimeImplicants());
        res.addAll(chart.extractRemainingImplicants());
        return res;
    }
}
