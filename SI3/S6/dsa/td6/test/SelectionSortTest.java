package ads.poo2.lab5.sorting;

class SelectionSortTest extends AbstractSortTest{

    @Override
    protected <T extends Comparable> void sort(T[] array) {
        SimpleSorting.selection(array);
    }


}
