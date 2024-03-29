package fr.uca.progfonc;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TD5Test {
    @Test
    void testLength() {
        Optional<LSTO<Integer>> list1 = Optional.of(new LSTO<>(1, Optional.of(new LSTO<>(2, Optional.of(new LSTO<>(3, Optional.empty()))))));
        Optional<LSTO<Integer>> list2 = Optional.empty();
        assertEquals(3, TD5.length(list1));
        assertEquals(0, TD5.length(list2));
    }

    @Test
    void testMember() {
        Optional<LSTO<Integer>> list1 = Optional.of(new LSTO<>(1, Optional.of(new LSTO<>(2, Optional.of(new LSTO<>(3, Optional.empty()))))));

        assertTrue(TD5.member(list1, 1));
        assertTrue(TD5.member(list1, 2));
        assertTrue(TD5.member(list1, 3));
        assertFalse(TD5.member(list1, 4));
    }

    @Test
    void testAppend() {
        Optional<LSTO<Integer>> list1 = Optional.of(new LSTO<>(1, Optional.of(new LSTO<>(2, Optional.of(new LSTO<>(3, Optional.empty()))))));
        Optional<LSTO<Integer>> list2 = Optional.of(new LSTO<>(4, Optional.of(new LSTO<>(5, Optional.empty()))));
        Optional<LSTO<Integer>> expected = Optional.of(new LSTO<>(1, Optional.of(new LSTO<>(2, Optional.of(new LSTO<>(3, Optional.of(new LSTO<>(4, Optional.of(new LSTO<>(5, Optional.empty()))))))))));

        assertEquals(expected, TD5.append(list1, list2));
    }

    @Test
    void testMap() {
        Optional<LSTO<Integer>> list1 = Optional.of(new LSTO<>(1, Optional.of(new LSTO<>(2, Optional.of(new LSTO<>(3, Optional.empty()))))));
        Optional<LSTO<String>> expected = Optional.of(new LSTO<>("1", Optional.of(new LSTO<>("2", Optional.of(new LSTO<>("3", Optional.empty()))))));

        assertEquals(expected, TD5.map(list1, String::valueOf));
    }

    @Test
    void testFilter() {
        Optional<LSTO<Integer>> list1 = Optional.of(new LSTO<>(1, Optional.of(new LSTO<>(2, Optional.of(new LSTO<>(3, Optional.empty()))))));
        Optional<LSTO<Integer>> expected = Optional.of(new LSTO<>(1, Optional.of(new LSTO<>(3, Optional.empty()))));

        assertEquals(expected, TD5.filter(list1, x -> x % 2 != 0));
    }

    @Test
    void testGroupByInitial() {
        Stream<String> stringStream = Stream.of("chat", "ane", "hibou", "chien", "cheval");
        Map<Character, List<String>> groupedByInitial = TD5.groupByInitial(stringStream);

        assertEquals(3, groupedByInitial.size()); // Il devrait y avoir 4 groupes distincts
        assertEquals(3, groupedByInitial.get('c').size()); // Il devrait y avoir 3 éléments commençant par 'c'
        assertEquals(1, groupedByInitial.get('h').size()); // Il devrait y avoir 1 élément commençant par 'h'
        assertEquals(1, groupedByInitial.get('a').size()); // Il devrait y avoir 1 élément commençant par 'a'
    }

    @Test
    void testGroupByClass() {
        Stream<Object> objectStream = Stream.of("chat", 5, 3.14, new Object(), "chien", 7);
        Map<Class<?>, Set<Object>> groupedByClass = TD5.groupByClass(objectStream);

        assertEquals(4, groupedByClass.size()); // Il devrait y avoir 4 groupes distincts
        assertEquals(2, groupedByClass.get(String.class).size()); // Il devrait y avoir 2 chaînes
        assertEquals(2, groupedByClass.get(Integer.class).size()); // Il devrait y avoir 1 entier
        assertEquals(1, groupedByClass.get(Double.class).size()); // Il devrait y avoir 1 double
        assertEquals(1, groupedByClass.get(Object.class).size()); // Il devrait y avoir 1 objet
    }
}

