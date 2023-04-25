package ads.poo2.lab5.sorting;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * Abstract class for sorting algorithms testing
 */
abstract class AbstractSortTest {
    @Test
    void testGeneralCaseSort() {
        Integer[] array = {5, 4, 3, 2, 1};
        sort(array);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, array);
    }

    @Test
    void testEmptyArraySort() {
        Integer[] array = {};
        sort(array);
        assertArrayEquals(new Integer[]{}, array);
    }

    @Test
    void testAlreadySortedArraySort() {
        Integer[] array = {1, 2, 3, 4, 5};
        sort(array);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, array);
    }


    @Test
    void testArrayWithRepeatedElementsSort() {
        Integer[] array = {1, 2, 3, 4, 5, 5, 4, 3, 2, 1};
        sort(array);
        assertArrayEquals(new Integer[]{1, 1, 2, 2, 3, 3, 4, 4, 5, 5}, array);
    }

    @Test
    void testArrayWithNegativeElementsSort() {
        Integer[] array = {-1, -2, -3, -4, -5, -5, -4, -3, -2, -1};
        sort(array);
        assertArrayEquals(new Integer[]{-5, -5, -4, -4, -3, -3, -2, -2, -1, -1}, array);
    }

    @Test
    void testPerformance() {
        Integer[] array = new Integer[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 1000);
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            sort(Arrays.copyOf(array, array.length));
        }
        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + "ms");
    }

    // Abstract method to be implemented in subclasses
    // protected abstract void sort(Integer[] array);
    protected abstract <T extends Comparable> void sort(T[] array);
    //protected abstract <T extends Comparable> void sort(List<T> list);

    @Test
    void testStability() {
        User[] users = new User[]{
                new User("ZZ", "Donald"),
                new User("AA", "Fred"),
                new User("BB", "Vlad"),
                new User("BB", "Pauline"),
                new User("AA", "Amir"),
                new User("CC", "Flo")};
        sort(users);
        //System.out.println(Arrays.toString(users));
        assertEquals("AA", users[0].name);
        assertEquals("Fred", users[0].firstname);
        assertEquals("BB", users[2].name);
        assertEquals("Vlad", users[2].firstname);
        assertEquals("BB", users[3].name);
        assertEquals("Pauline", users[3].firstname);

    }

    public class User implements Comparable<User> {
        String name;
        String firstname;

        public User(String name, String firstname) {
            this.name = name;
            this.firstname = firstname;
        }

        @Override
        public int compareTo(User other) {
            return this.name.compareTo(other.name);
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", firstname='" + firstname + '\'' +
                    '}';
        }
    }


}