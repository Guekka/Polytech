package btree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TraversalsTest {

    BinaryTreeInterface<String> stringBinaryTree;
    BinaryTreeInterface<String> simpleStringBinaryTree;
    BinaryTreeInterface<String> leafBinaryTree;
    List<String> labels = new ArrayList<>();

    @BeforeEach
    void setUp() {
        labels = new ArrayList<>();
        stringBinaryTree = BinaryTree.read("A B B1$ B2$ C C1 C11$ C12$ C2$");
        simpleStringBinaryTree = BinaryTree.read("A B$ C$ ");
        leafBinaryTree = BinaryTree.read("A$");

    }

    @Test
    void testPreOrderTraversalEmptyTree() {
        stringBinaryTree = null;
        Traversals.preOrderTraversal(stringBinaryTree, String::toLowerCase, labels);
        assertTrue(labels.isEmpty());
    }

    @Test
    void testPreOrderTraversalSimpleTree() {
        Traversals.preOrderTraversal(simpleStringBinaryTree, String::toLowerCase, labels);
        assertEquals(Arrays.asList("a", "b", "c"), labels);

    }

    @Test
    void testPreOrderTraversalLeafTree() {
        Traversals.preOrderTraversal(leafBinaryTree, String::toLowerCase, labels);
        assertEquals(List.of("a"), labels);
    }

    @Test
    void testPreOrderTraversalTree() {
        stringBinaryTree = BinaryTree.read("");
        Traversals.preOrderTraversal(stringBinaryTree, String::toLowerCase, labels);
        assertTrue(labels.isEmpty());
    }

    @Test
    void testPreOrderTraversal() {
        Traversals.preOrderTraversal(stringBinaryTree, String::toLowerCase, labels);
        assertEquals(Arrays.asList("a", "b", "b1", "b2", "c", "c1", "c11", "c12", "c2"), labels);
    }

    //To Show the difference between the three traversal methods
    @Test
    void testPreOrderTraversalOnlyToString() {
        System.out.println(stringBinaryTree);
        Traversals.preOrderTraversal(stringBinaryTree, String::toString, labels);
        System.out.println("PreOrder : " + labels);
        assertEquals(Arrays.asList("A", "B", "B1", "B2", "C", "C1", "C11", "C12", "C2"), labels);
    }


    @Test
    void testInOrderTraversal() {
        Traversals.inOrderTraversal(stringBinaryTree, String::toLowerCase, labels);
        assertEquals(Arrays.asList("b1", "b", "b2", "a", "c11", "c1", "c12", "c", "c2"), labels);
    }

    //To Show the difference between the three traversal methods
    @Test
    void testinOrderTraversalTraversalOnlyToString() {
        System.out.println(stringBinaryTree);
        Traversals.inOrderTraversal(stringBinaryTree, String::toString, labels);
        System.out.println("InOrder : " + labels);
        assertEquals(Arrays.asList("B1", "B", "B2", "A", "C11", "C1", "C12", "C", "C2"), labels);
    }

    @Test
    void testPostOrderTraversal() {
        Traversals.postOrderTraversal(stringBinaryTree, String::toLowerCase, labels);
        assertEquals(Arrays.asList("b1", "b2", "b", "c11", "c12", "c1", "c2", "c", "a"), labels);
    }

    //To Show the difference between the three traversal methods
    @Test
    void testpostOrderTraversalOnlyToString() {
        System.out.println(stringBinaryTree);
        Traversals.postOrderTraversal(stringBinaryTree, String::toString, labels);
        System.out.println("PostOrder : " + labels);
        assertEquals(Arrays.asList("B1", "B2", "B", "C11", "C12", "C1", "C2", "C", "A"), labels);
    }

}