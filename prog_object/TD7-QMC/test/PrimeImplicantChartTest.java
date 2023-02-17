import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrimeImplicantChartTest {

    @Test
    void t1() {
        //extract Essential PrimeImplicants

        List<Minterm> list = new ArrayList<>();
        Minterm m0 = new Minterm(1, -1, 1);
        list.add(m0);
        Minterm m1 = new Minterm(1, 1, -1);
        list.add(m1);
        //m0.addCombination(5,7);
        //m1.addCombination(6,7);
        PrimeImplicantChart pmc = new PrimeImplicantChart(new int[]{5, 6, 7}, list);
        List<Minterm> essential = pmc.extractEssentialPrimeImplicants();
        assertEquals(2, essential.size());
        assertTrue(essential.contains(m0));
        assertTrue(essential.contains(m1));

        // Not other

        List<Minterm> implicants = pmc.extractRemainingImplicants();
        assertEquals(0, implicants.size());

    }

    @Test
    void t2() {
        List<Minterm> list = new ArrayList<>();
        Minterm m0 = new Minterm(-1, 0, 0);
        list.add(m0);
//    m0.addCombination(0,4);
        Minterm m1 = new Minterm(-1, 1, 1);
        list.add(m1);
        //  m1.addCombination(3,7);
        Minterm m2 = new Minterm(1, 0, -1);
        list.add(m2);
        //m2.addCombination(4,5);
        Minterm m3 = new Minterm(1, -1, 1);
        list.add(m3);
        //m3.addCombination(7,5);

        PrimeImplicantChart pmc = new PrimeImplicantChart(new int[]{0, 3, 4, 5, 7}, list);
        List<Minterm> essential = pmc.extractEssentialPrimeImplicants();
        assertEquals(2, essential.size());
        assertTrue(essential.contains(m0));
        assertTrue(essential.contains(m1));
        // other
        List<Minterm> implicants = pmc.extractRemainingImplicants();

        assertEquals(1, implicants.size());
        assertTrue(list.containsAll(implicants));
        assertTrue(implicants.contains(m2) || implicants.contains(m3));
    }

}