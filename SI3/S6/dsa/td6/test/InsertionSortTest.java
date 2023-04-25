package ads.poo2.lab5.sorting;

class InsertionSortTest extends AbstractSortTest{

    @Override
    protected <T extends Comparable> void sort(T[] array) {
        SimpleSorting.insertion(array);
    }


}
