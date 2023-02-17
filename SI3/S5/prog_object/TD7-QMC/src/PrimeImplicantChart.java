import java.util.*;
import java.util.stream.Collectors;

    public class PrimeImplicantChart {

        private final List<Minterm> minTerms;
        private final List<Integer> ints;

        /**
         * Initializes the grid with the original minterms and values.
         * @param values        Initial decimal values (they are also included in the combinations of minterms).
         * @param mintermList   The list of minterms reduced by merging the categories
         */
        public PrimeImplicantChart(int[] ints, List<Minterm> list) {
            this.minTerms = list;
            this.ints = Arrays.stream(ints).boxed().collect(Collectors.toList());
        }

        public static class PostFixe {

            private String[] uniteLex;
            private int indice = 0;

            private String getUniteLex(){
                return uniteLex[indice++];
            }
            private boolean isNumber(String s){
                try{ Integer.parseInt(s); }
                catch(NumberFormatException e)
                { return false; }
                return true;
            }

            private int eval(String op, int u, int v){
                switch(op){
                    case "+": return u + v;
                    case "*": return u * v;
                    case "/": return u / v;
                    case "-": return u - v;
                    default: throw new RuntimeException("op inconnu");
                }
            }

            public void setPostFixe(String expression) {
                this.uniteLex = expression.split(" ");
            }

            public static void main(String[] args) {
                var s = new PostFixe();
                s.setPostFixe("+ + 1 + 2 3 + 4 5");
                System.out.print(s.eval());
            }
        }


        private Map<Integer, List<Minterm>> getOccurences() {
            var res = new HashMap<Integer, List<Minterm>>();
            for(Integer i : ints) {
                res.put(i, new ArrayList<>());
                for(Minterm m : minTerms) {
                    if(m.getCombinations().contains(i)) {
                        res.get(i).add(m);
                    }
                }
            }
            return res;
        }
        /**
         * extracts only the essential minterms; they correspond to the minterms that are the only ones to represent one of the initial values.
         * @return essential minterm list
         */
        public List<Minterm> extractEssentialPrimeImplicants() {
            return getOccurences().entrySet().stream()
                    .filter(e -> e.getValue().size() == 1)
                    .flatMap(e -> e.getValue().stream())
                    .distinct()
                    .collect(Collectors.toList());
        }

        /**
         * After removing the initial values covered by the essential minterms,
         * choose a minterm for each remaining value not covered by an essential minterm.
         */
        public List<Minterm> extractRemainingImplicants() {
            var essential = extractEssentialPrimeImplicants();
            var remaining = new ArrayList<Minterm>();
            var occurences = getOccurences();
            for(Integer i : ints) {
                if(occurences.get(i).size() > 1) {
                    var minterms = occurences.get(i);

                    boolean found = false;
                    for (Minterm m : minterms) {
                        if (essential.contains(m)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        remaining.add(minterms.get(0));
                    }
                }
            }
            return remaining;
        }
    }
