import java.util.*;
import java.util.stream.Collectors;

public class Minterm {
    enum MinTermState {
        ONE, ZERO, BOTH;

        public static Optional<MinTermState> fromInt(int n) {
            if (n == 0) return Optional.of(ZERO);
            if (n == 1) return Optional.of(ONE);
            if (n == -1) return Optional.of(BOTH);
            return Optional.empty();
        }

        @Override
        public String toString() {
            switch (this) {
                case ONE:
                    return "1";
                case ZERO:
                    return "0";
                case BOTH:
                    return "-";
                default:
                    return "";
            }
        }
    }

    private boolean marked = false;
    private final List<MinTermState> data;

    /**
     * @param decimal the decimal number for which we want to calculate the number of bits necessary to represent it
     * @return the minimum number of bits needed to encode this decimal in binary.
     */
    public static int numberOfBitsNeeded(int decimal) {
        if (decimal < 0) decimal = -decimal;
        int counter = 1;
        while ((decimal /= 2) != 0) counter++;
        return counter;
    }

    /*********************************************************
     * Management of the minterms structure
     ******************************************************** */


    /**
     * returns all the numbers that were used to build this minterm.
     * For example, [00*0] may have been created from 0 and 2 (* = -1)
     *
     * @return all the numbers that were used to build this minterm.
     */
    public Collection<Integer> getCombinations() {
        // Create a queue to store all the combinations to be validated
        var combinationsToValidate = new ArrayDeque<List<MinTermState>>();
        // Add the current data to the queue
        combinationsToValidate.add(data);
        // Create a list to store the validated combinations
        var validatedCombinations = new ArrayList<List<MinTermState>>();
        // Continue until there are no more combinations to validate
        while (!combinationsToValidate.isEmpty()) {
            // Get the current combination
            var currentCombination = new ArrayList<>(combinationsToValidate.poll());
            // Check if the combination contains a MinTermState.BOTH value
            var pos = currentCombination.indexOf(MinTermState.BOTH);
            // If the combination does not contain MinTermState.BOTH, add it to the validated combinations
            if (pos == -1) {
                validatedCombinations.add(currentCombination);
            }
            // If the combination does contain MinTermState.BOTH, add two new combinations with MinTermState.ZERO and MinTermState.ONE in place of MinTermState.BOTH
            else {
                currentCombination.set(pos, MinTermState.ZERO);
                combinationsToValidate.add(new ArrayList<>(currentCombination));
                currentCombination.set(pos, MinTermState.ONE);
                combinationsToValidate.add(new ArrayList<>(currentCombination));
            }
        }
        // Convert the validated combinations to integer values and return the list
        return validatedCombinations.stream().map(d -> new Minterm(d).toIntValue()).collect(Collectors.toList());
    }

    /**
     * marks the minterm as used to build another minTerm
     */
    public void mark() {
        marked = true;
    }

    /**
     * @return true if the minterm has been used to build another minterm, false otherwise.
     */
    public boolean isMarked() {
        return marked;
    }

    /*********************************************************
     * Management of the minterms contents
     ******************************************************** */
    private int numberOfXState(MinTermState state) {
        return (int) data.stream().filter(x -> x.equals(state)).count();
    }

    /**
     * @return return the number of 0 in the minterm
     */
    public int numberOfZero() {
        return numberOfXState(MinTermState.ZERO);
    }

    /**
     * @return return the number of 1 in the minterm
     */
    public int numberOfOne() {
        return numberOfXState(MinTermState.ONE);
    }


    /*********************************************************
     * Equality
     ******************************************************** */

    /**
     * @param o
     * @return true if the representation in base 2 is the same. Ignore the other elements.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Minterm minterm = (Minterm) o;
        return this.data.equals(minterm.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marked, data);
    }



/* -------------------------------------------------------------------------
        Constructors
 ------------------------------------------------------------------------- */

    /**
     * Construct a minterm corresponding to the decimal passed in parameter
     * and encode it on the given number of bits.
     * The associated combination then contains decimal.
     *
     * @param decimal      the decimal value representing the minterm
     * @param numberOfBits the number of bits of encoding of the decimal
     */
    public Minterm(int decimal, int numberOfBits) {
        data = new ArrayList<>(numberOfBits);
        for (int i = 0; i < numberOfBits; i++) {
            data.add(MinTermState.fromInt(decimal % 2).orElseThrow());
            decimal /= 2;
        }
        Collections.reverse(data);
    }


    /**
     * Builds a minterm from its representation in binary which can contain -1.
     * This constructor does not update the associated combinations.
     * The size of the binary representation corresponds to the number of parameters (binary.length).
     *
     * @param binary
     */
    protected Minterm(int... binary) {
        data = Arrays.stream(binary).mapToObj(MinTermState::fromInt).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }

    protected Minterm(List<MinTermState> binary) {
        data = binary;
    }

    /**
     * Compute the string showing the binary form of the minterm.
     * For example, "101" represents the minterm corresponding to 5,
     * while "1-1" represents a minterm resulting, for example from the merge of 5 and 7 (1 -1 1)
     *
     * @return the string
     */
    @Override
    public String toString() {
        return data.stream().map(MinTermState::toString).collect(Collectors.joining());
    }



/* -------------------------------------------------------------------------
        Binary <-> Decimal
 ------------------------------------------------------------------------- */

    /**
     * Calculates the integer value of the binary representation.
     * But in case one of the binary elements is -1, it returns -1.
     * This method is private because it should not be used outside this class.
     *
     * @returns the value of the minterm calculated from its binary representation.
     */
    public int toIntValue() {
        if (data.contains(MinTermState.BOTH)) return -1;
        return data.stream().mapToInt(x -> x.equals(MinTermState.ONE) ? 1 : 0).reduce(0, (x, y) -> x * 2 + y);
    }


   /* -------------------------------------------------------------------------
        Merge
 ------------------------------------------------------------------------- */


    /**
     * create a Minterm from the merge of two Minterms when it is posssible otherwise return null
     * Attention two minterms can only be merged if
     * - they differ by one value at most.
     * - they are of the same size.
     * If a merge is possible, the returned minterm
     * - has the same binary representation as the original minterm, but where at most one slot has been replaced by -1,
     * - and it has, for the combinations, the merge of the combinations of both minterms this and other)
     * - and the both mindterms  this and other are marked
     *
     * @param other is another Minterm which we try to unify
     * @return a new Minterm when it is possible to unify, else null * @param other is another Minterm which we try to merge
     * @return a new Minterm when it is possible to merge, else null
     */
    public Minterm merge(Minterm other) {
        // check that they are of the same size
        if (this.data.size() != other.data.size()) return null;
        // check that they only differ by one
        Optional<Integer> diffIndex = Optional.empty();
        for (int i = 0; i < data.size(); ++i) {
            if (this.data.get(i) != other.data.get(i)) {
                if (diffIndex.isPresent()) return null;
                diffIndex = Optional.of(i);
            }
        }
        if (diffIndex.isEmpty()) return null;

        var res = new ArrayList<>(data);
        res.set(diffIndex.get(), MinTermState.BOTH);
        this.mark();
        other.mark();

        return new Minterm(res);
    }

}
