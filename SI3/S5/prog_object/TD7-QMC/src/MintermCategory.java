import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MintermCategory extends ArrayList<Minterm> {
    public MintermCategory() {}
    public MintermCategory(List<Minterm> merge) {
        this.addAll(merge);
    }

    /**
     * It computes the list of minterms m, such that :
     * - either m results from  merging a minterm from the category "this" with a minterm from the other category ;
     * - either m belongs to the current category (this) and could not be unified with a minterm of the other category
     * @param otherCategory
     * @return  the list of merged minterms
     */
    public List<Minterm> merge(MintermCategory otherCategory){
        List<Minterm> result = new ArrayList<>();
        for (Minterm m1 : this) {
            for (Minterm m2 : otherCategory) {
                var merged = m1.merge(m2);
                if (merged != null) {
                    result.add(merged);
                }
            }
        }
        result.addAll(this.stream().filter(m -> !m.isMarked()).collect(Collectors.toList()));
        return result;
    }

}