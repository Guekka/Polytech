package bst;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeWithInnerclassTest {

    BinarySearchTreeWithInnerClass<Integer> tree;

    @BeforeEach
    public void setUp() {
        tree = new BinarySearchTreeWithInnerClass<>();
    }

    @Test
    void testIsEmpty() {
        BinarySearchTreeWithInnerClass<Integer> bst = new BinarySearchTreeWithInnerClass<>();
        assertTrue(bst.isEmpty());
        bst.insert(5);
        assertFalse(bst.isEmpty());
    }

    @Test
    void testMakeEmpty() {
        BinarySearchTreeWithInnerClass<Integer> bst = new BinarySearchTreeWithInnerClass<>();
        bst.insert(5);
        bst.makeEmpty();
        assertTrue(bst.isEmpty());
    }

    @Test
    void testContains() {
        BinarySearchTreeWithInnerClass<Integer> bst = new BinarySearchTreeWithInnerClass<>();
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);
        assertTrue(bst.contains(5));
        assertTrue(bst.contains(3));
        assertTrue(bst.contains(7));
        assertFalse(bst.contains(4));
    }

    @Test
    void testInsert() {
        BinarySearchTreeWithInnerClass<Integer> bst = new BinarySearchTreeWithInnerClass<>();
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);
        assertTrue(bst.contains(5));
        assertTrue(bst.contains(3));
        assertTrue(bst.contains(7));
        assertEquals(5, bst.getElement());
        List<Integer> list = bst.toSortedList();
        assertEquals(Arrays.asList(3, 5, 7), list);
    }

    @Test
    void testFindMin() {
        BinarySearchTreeWithInnerClass<Integer> bst = new BinarySearchTreeWithInnerClass<>();
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);
        assertEquals((Integer) 3, bst.findMin());
    }

    @Test
    void testFindMinInEmptyTree() {
        BinarySearchTreeWithInnerClass<Integer> bst = new BinarySearchTreeWithInnerClass<>();
        assertNull(bst.findMin());
    }

    @Test
    void testFindMax() {
        BinarySearchTreeWithInnerClass<Integer> bst = new BinarySearchTreeWithInnerClass<>();
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);
        assertEquals(7, bst.findMax());
    }

    @Test
    void testFindMaxInEmptyTree() {
        BinarySearchTreeWithInnerClass<Integer> bst = new BinarySearchTreeWithInnerClass<>();
        assertNull(bst.findMax());
    }


    @Test
    void testSize() {
        BinarySearchTreeWithInnerClass<Integer> bst = new BinarySearchTreeWithInnerClass<>();
        bst.insert(5);
        assertEquals(1, bst.getSize());
        bst.insert(3);
        assertEquals(2, bst.getSize());
        bst.insert(7);
        assertEquals(3, bst.getSize());
    }


    @Test
    void testRemoveLessThan() {
        BinarySearchTreeWithInnerClass<Integer> bst = new BinarySearchTreeWithInnerClass<>();
        bst.insert(10);
        bst.insert(20);
        bst.insert(5);
        bst.insert(15);
        bst.insert(30);

        bst.removeLessThan(15);
        //expose the root, not so good
        assertEquals(3, bst.getSize());
        assertEquals(20, bst.getElement());
        assertEquals(Arrays.asList(15, 20, 30), bst.toSortedList());
    }

    @Test
    void testRemoveLessThanInEmptyTree() {
        BinarySearchTreeWithInnerClass<Integer> bst = new BinarySearchTreeWithInnerClass<>();
        bst.removeLessThan(15);
        assertEquals(0, bst.getSize());
    }

    //non-empty tree with no node less than the given value
    @Test
    void testRemoveNoLessThan() {
        BinarySearchTreeWithInnerClass<Integer> bst = new BinarySearchTreeWithInnerClass<>();
        bst.insert(5);
        bst.insert(7);
        bst.insert(3);

        bst.removeLessThan(2);
        assertEquals(3, bst.getSize());
    }


    @Test
    void testRemoveGreaterThan() {
        BinarySearchTreeWithInnerClass<Integer> bst = new BinarySearchTreeWithInnerClass<>();
        bst.insert(5);
        bst.insert(7);
        bst.removeGreaterThan(5);
        assertEquals(1, bst.getSize());
        bst.insert(7);
        bst.insert(3);
        bst.removeGreaterThan(2);
        assertEquals(0, bst.getSize());
    }

    @Test
    void testToSortedList() {
        BinarySearchTreeWithInnerClass<Integer> bst = new BinarySearchTreeWithInnerClass<>();
        bst.insert(10);
        bst.insert(20);
        bst.insert(5);
        bst.insert(15);
        bst.insert(30);
        List<Integer> list = bst.toSortedList();
        assertEquals(Arrays.asList(5, 10, 15, 20, 30), list);
    }


    //---------------------
    //  Test Remove
    //---------------------

    @Test
    void testRemove() {
        BinarySearchTreeWithInnerClass<Integer> bst = new BinarySearchTreeWithInnerClass<>();
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);
        bst.remove(3);
        assertFalse(bst.contains(3));
        assertTrue(bst.contains(5));
        assertTrue(bst.contains(7));
    }

    @Test
    void testRemoveLeaf() {
        tree.insert(5);
        tree.remove(5);
        assertTrue(tree.isEmpty());
    }

    @Test
    void testRemoveNodeWithOneChild() {
        tree.insert(5);
        tree.insert(3);
        tree.remove(5);
        assertEquals(3, tree.getElement());
        assertTrue(tree.isLeaf());
    }

    @Test
    void testRemoveNodeWithTwoChildren() {
        tree.insert(5);
        tree.insert(3);
        tree.insert(7);
        tree.remove(5);
        //assertEquals(3, tree.getElement());
        //assertEquals(7, tree.getRight().getElement());
        assertEquals(Arrays.asList(3, 7), tree.toSortedList());
    }

    @Test
    void testRemoveRootWithTwoChildren() {
        tree.insert(5);
        tree.insert(3);
        tree.insert(7);
        tree.insert(1);
        tree.remove(5);
        //assertEquals(3, tree.getElement());
        //assertEquals(Integer.valueOf(7), tree.getRight().getElement());
        //assertEquals(Integer.valueOf(1), tree.getLeft().getElement());
        assertEquals(Arrays.asList(1, 3, 7), tree.toSortedList());
    }

    @Test
    void testRemoveInTreeFromLesson() {
        //Ensure that the tree is the same as the one in the lesson
        createTreeFromLesson();
        int size = tree.getSize();
        List<Integer> list = new ArrayList<>(tree.toSortedList());
        assertEquals(Arrays.asList(2, 5, 7, 9, 10, 12, 15, 17, 20, 30), list);

        //Begin tests removing 17
        tree.remove(17);
        list.removeIf(i -> i == 17);
        assertEquals(list, tree.toSortedList());
        //tree.display();
        assertEquals(size - 1, tree.getSize());
        //assertEquals(15, tree.getRight().getElement());

        //Begin tests removing 15
        tree.remove(15);
        list.removeIf(i -> i == 15);
        assertEquals(list, tree.toSortedList());
        //tree.display();
        //assertEquals(20, tree.getRight().getElement());
        assertEquals(size - 2, tree.getSize());

        tree.remove(12);
        list.removeIf(i -> i == 12);
        assertEquals(list, tree.toSortedList());
        //tree.display();
        //assertEquals(10, tree.getElement());
        assertEquals(size - 3, tree.getSize());
        //assertEquals(5, tree.getLeft().getElement());
        //assertEquals(9, tree.getLeft().getRight().getElement());
    }

    private void createTreeFromLesson() {
        tree.insert(12);
        tree.insert(5);
        tree.insert(2);
        tree.insert(9);
        tree.insert(7);
        tree.insert(10);
        tree.insert(15);
        tree.insert(20);
        tree.insert(17);
        tree.insert(30);

    }


    @Test
    void testRemoveNonExistentElement() {
        tree.insert(5);
        tree.remove(3);
        assertEquals(5, tree.getElement());
    }

}
