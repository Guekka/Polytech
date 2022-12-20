import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QMCTest {
    @Test
    void constructor() {
        int bitLen = 5;
        var input = List.of(0, 2, 5, 9);

        var qmc = new QMC(input, bitLen);

        var positive = Map.of(
                0,new MinTerm(0, bitLen),
                2,new MinTerm(2, bitLen),
                5,new MinTerm(5, bitLen),
                9,new MinTerm(9, bitLen)
        );

        var res = qmc.terms();
        for(int i = 0; i < qmc.terms().size(); ++i) {
            var term = qmc.terms().get(new MinTerm(i, bitLen));
            assertEquals(positive.containsKey(i), term);
        }
    }
}
