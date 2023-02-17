import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QMCTest {
    @Test
    void t1() {
        QMC qmc = new QMC(1,2,3,5);
        List<Minterm> primaryTerms =  qmc.computePrimeImplicants();
        assertEquals(2,primaryTerms.size());
        assertTrue(primaryTerms.contains(new Minterm(-1,0,1)));
        assertTrue(primaryTerms.contains(new Minterm(0,1,-1)));
    }
}
