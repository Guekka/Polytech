package ads.poo2.lab5.sorting;

class MergeSortTest extends AbstractSortTest{

    @Override
    protected <T extends Comparable> void sort(T[] array) {
        MergeSort.sort(array);
    }

}
