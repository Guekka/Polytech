import java.util.*;
import java.util.stream.Collectors;

public class CategoryManager {
    List<Minterm> minterms;
    int numBits;
    boolean isLastTurn = false;
    int curCategory = 0;

    /**
     * CategoryManager : compute the categories from a list of minterms according to the number of 11
     *
     * @param mintermList
     * @return
     */
    public CategoryManager(List<Minterm> mintermList, int nbBits) {
        minterms = mintermList;
        numBits = nbBits;
    }


    public int numberOfCategories() {
        return (int) minterms.stream().map(Minterm::numberOfOne).distinct().count();
    }


    /**
     * @param numberOfOne
     * @return the Category Of Minterms containing  numberOfOne
     */
    public MintermCategory getCategory(int numberOfOne) {
        return minterms.stream().filter(m -> m.numberOfOne() == numberOfOne).collect(Collectors.toCollection(MintermCategory::new));
    }


    /**
     * isLastTurn()
     *
     * @return true is it's the last turn.
     */
    public boolean isLastTurn() {
        return isLastTurn;
    }

    /**
     * Merge the categories two by two if they have only one "one" between them.
     * The minterms are the result of the merging of the categories.
     * Be careful for a category of n "one", if the category of "n+1" has no minterms,
     * you must recover the minterms of the category of n "one" which were not marked.
     * This is the last round if no terms could be merged.
     *
     * @return the merged terms
     */
    public List<Minterm> mergeCategories() {
        Set<Minterm> result = new HashSet<>();
        for(int i = 1; i <= numBits; ++i)
        {
            var curCatego = getCategory(i - 1);
            var nextCatego = getCategory(i);

            result.addAll(curCatego.merge(nextCatego));
        }
        isLastTurn = result.containsAll(minterms) && result.size() == minterms.size();
        return new ArrayList<>(result);
    }
}