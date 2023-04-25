package ads.poo2.lab5.sorting;

import org.junit.jupiter.api.Test;

class HeapSortTest extends AbstractSortTest{
    @Override
    protected <T extends Comparable> void sort(T[] array) {
        HeapSort.sort(array);
    }

    //We use override to avoid the warning
    @Test
    void testStability(){
        System.out.println("Do not test stability, this algo is not stable");
    }
}
