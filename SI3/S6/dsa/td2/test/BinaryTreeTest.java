
import binarypk.BinaryTree;
import org.junit.jupiter.api.Test;


import java.util.function.Function;
import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeTest {

       BinaryTree<Integer> root = new BinaryTree<>(1);
        BinaryTree<Integer> leaf = new BinaryTree<>(10);
        BinaryTree<Integer> node3 = new BinaryTree<>(3);

        @Test
         void testLownessForOneNode() {
            // Construire un arbre simple avec un seul nœud
            root = new BinaryTree<>(1);
            assertEquals(0, root.lowness());
        }

    @Test
    void testLownessForTwoNode() {
        // Construire un arbre simple avec un seul nœud
        root = new BinaryTree<>(1);
        root.setLeftBT(leaf);
        assertEquals(1, root.lowness());
    }
        @Test
         void testLownessForOneTree() {
            // Construire un arbre binaire complet avec 7 nœuds
            initBN();
            assertEquals(2, root.lowness());
            BinaryTree<String> tree = initTreeFromLesson();
            assertEquals(2, tree.lowness());
        }
    @Test
    void testLownessForOneBranch() {
        // Construire un arbre binaire complet avec 7 nœuds
        BinaryTree<Integer> node2 = new BinaryTree<>(2);
        BinaryTree<Integer> node3 = new BinaryTree<>(3);
        BinaryTree<Integer> node4 = new BinaryTree<>(4);
        BinaryTree<Integer> node5 = new BinaryTree<>(5);
        BinaryTree<Integer> node6 = new BinaryTree<>(6);
        root.setLeftBT(node2);
        node2.setRightBT(node3);
        node3.setLeftBT(node4);
        node4.setRightBT(node5);
        node5.setLeftBT(node6);
        node6.setRightBT(leaf);
        System.out.println(root);
        assertEquals(6, root.lowness());
    }

        private void initBN() {
           root = new BinaryTree<>(1);

            // Construire un arbre binaire complet avec 7 nœuds
            BinaryTree<Integer> node2 = new BinaryTree<>(2);
            node3 = new BinaryTree<>(3);
            BinaryTree<Integer> node4 = new BinaryTree<>(4);
            BinaryTree<Integer> node5 = new BinaryTree<>(5);
            BinaryTree<Integer> node6 = new BinaryTree<>(6);
            root.setLeftBT(node2);
            root.setRightBT(node3);
            node2.setLeftBT(node4);
            node2.setRightBT(node5);
            node3.setLeftBT(node6);
            node3.setRightBT(leaf);
            //System.out.println(root);
        }


    @Test
    void testData() {
        BinaryTree<Integer> node2 = new BinaryTree<>(2);
        assertEquals(2, node2.getData());
    }

    @Test
    void left() {
        root = new BinaryTree<>(1);
        // Construire un arbre binaire complet avec 7 nœuds
        BinaryTree<Integer> node2 = new BinaryTree<>(2);
        BinaryTree<Integer> node3 = new BinaryTree<>(3);
        root.setLeftBT(node2);
        root.setRightBT(node3);
        assertEquals(node2,root.left());
    }

    @Test
    void right() {
            root = new BinaryTree<>(1);
            // Construire un arbre binaire complet avec 7 nœuds
            BinaryTree<Integer> node2 = new BinaryTree<>(2);
            BinaryTree<Integer> node3 = new BinaryTree<>(3);
            root.setLeftBT(node2);
            root.setRightBT(node3);
            assertEquals(node3,root.right());
    }

    @Test
    void isLeaf() {
        root = new BinaryTree<>(1);
        assertTrue(root.isLeaf());
        initBN();
        assertFalse(root.isLeaf());
        assertTrue(leaf.isLeaf());
    }

    @Test
    void testHeight() {
        root = new BinaryTree<>(1);
        assertEquals(0, root.height());
        BinaryTree<Integer> node3Local = new BinaryTree<>(3);
        root.setRightBT(node3Local);
        assertEquals(1, root.height());
        BinaryTree<Integer> node2 = new BinaryTree<>(2);
        root.setLeftBT(node2);
        assertEquals(1, root.height());
        initBN();
        assertEquals(2, root.height());
        node3Local = new BinaryTree<>(100);
        leaf.setLeftBT(node3Local);
        node3Local.setRightBT(new BinaryTree<>(200));
        assertEquals(4, root.height());
        BinaryTree<String> tree = initTreeFromLesson();
        assertEquals(4, tree.height());
    }





    @Test
    void testSize() {
        root = new BinaryTree<>(1);
        assertEquals(1, root.size());
        initBN();
        assertEquals(7, root.size());
        BinaryTree<String> tree = initTreeFromLesson();
        assertEquals(10, tree.size());
    }

    @Test
    void testLeaves() {
        root = new BinaryTree<>(1);
        assertEquals(1, root.leaves());
        initBN();
        assertEquals(4, root.leaves());
        BinaryTree<String> tree = initTreeFromLesson();
        assertEquals(5, tree.leaves());
    }

    @Test
    void testIsomorphicOneNode() {
        root = new BinaryTree<>(1);
        BinaryTree<Integer> node = new BinaryTree<>(1000);
        assertTrue(root.isomorphic(node));
    }

        @Test
        void testIsomorphic() {
            initBN();

            BinaryTree<Integer> node = new BinaryTree<>(1000);
            // Construire un arbre binaire complet avec 7 nœuds
            BinaryTree<Integer> node2 = new BinaryTree<>(2000);
            BinaryTree<Integer> node3Local = new BinaryTree<>(3000);
            BinaryTree<Integer> node4 = new BinaryTree<>(40000);
            BinaryTree<Integer> node5 = new BinaryTree<>(50);
            BinaryTree<Integer> node6 = new BinaryTree<>(6);
            BinaryTree<Integer> node7 = new BinaryTree<>(70);
            node.setLeftBT(node2);
            node.setRightBT(node3Local);
            node2.setLeftBT(node4);
            node2.setRightBT(node5);
            node3Local.setLeftBT(node6);
            node3Local.setRightBT(node7);
            assertTrue(root.isomorphic(node));
            node7.setRightBT( new BinaryTree<>(700));
            assertFalse(root.isomorphic(node));
            assertFalse(root.isomorphic(null));
        }

    @Test
    void testBalanced1() {
        root = new BinaryTree<>(1);
        assertTrue(root.balanced1());
        initBN();
        assertTrue(root.balanced1());
    }

        @Test
        void testNotBalanced1Simple() {
            root = new BinaryTree<>(1);
            root.setLeftBT(leaf);
            assertTrue(root.balanced1());
            leaf.setLeftBT(new BinaryTree<>(10));
            assertFalse(root.balanced1());
        }
        @Test
        void testNotBalanced1() {
            initBN();
            assertTrue(root.balanced1());
            BinaryTree<Integer> node = new BinaryTree<>(100);
            leaf.setRightBT(node);
            assertTrue(root.balanced1());
            node.setLeftBT(new BinaryTree<>(500));
            assertFalse(root.balanced1());
            BinaryTree<String> tree = initTreeFromLesson();
            assertEquals(1, tree.left().height());
            assertEquals(3, tree.right().height());
            assertFalse(tree.balanced1());
        }

    @Test
    void testBalanced2fn() {
        balanced(BinaryTree::balanced2);
    }

    @Test
    void testBalanced1fn() {
        balanced(BinaryTree::balanced1);
    }
    void balanced(Function<BinaryTree<Integer>,Boolean> balancedFunction ) {
        root = new BinaryTree<>(1);
        assertTrue(balancedFunction.apply(root));
        initBN();
        assertTrue(balancedFunction.apply(root));
    }

    @Test
    void testShapely1() {
            isShapelyTest(BinaryTree::shapely1);
            isNotShapelyTest(BinaryTree::shapely1);
    }

    @Test
    void testShapely2() {
        isShapelyTest(BinaryTree::shapely2);
        isNotShapelyTest(BinaryTree::shapely2);
    }
    @Test
    void testShapely2OnTreeFromLesson() {
        BinaryTree<String> tree = initTreeFromLesson();
        assertEquals(2, tree.lowness());
        assertEquals(4, tree.height());
        assertTrue(tree.shapely2());
        tree.right().setLeftBT(new BinaryTree<>("KO"));
        assertEquals(1, tree.right().lowness());
        assertEquals(3, tree.right().height());
        assertFalse(tree.shapely2());
    }

    void isShapelyTest(Function<BinaryTree<Integer>,Boolean> fn ) {
        root = new BinaryTree<>(1);
        assertTrue(fn.apply(root));
        initBN();
        assertTrue(fn.apply(root));
    }

    void isNotShapelyTest(Function<BinaryTree<Integer>,Boolean> fn ) {
            // Construire un arbre binaire complet avec 7 nœuds
        initBN();
        assertTrue(fn.apply(root));
        //Transformer l'arbre en un arbre binaire non complet
        BinaryTree<Integer> node100 = new BinaryTree<>(100);
        leaf.setLeftBT(node100);
        assertTrue(fn.apply(root));
        assertTrue(root.height()<=root.lowness()*2);
        //Augmenter encore sa hauteur de 1
        node100.setRightBT(new BinaryTree<>(200));
        System.out.println(root);
        System.out.println("at level of the root : height =" + root.height()+" against lowness ="+root.lowness());
        System.out.println("but for node3 : height =" + node3.height()+" against lowness ="+node3.lowness());
        assertFalse(fn.apply(root));
    }


    BinaryTree<String> initTreeFromLesson(){
            BinaryTree<String> root = new BinaryTree<>("A");
            BinaryTree<String> nodeB = new BinaryTree<>("B");
            BinaryTree<String> nodeC = new BinaryTree<>("C");
            BinaryTree<String> nodeD = new BinaryTree<>("D");
            BinaryTree<String> nodeE = new BinaryTree<>("E");
            BinaryTree<String> nodeF = new BinaryTree<>("F");
            BinaryTree<String> nodeG = new BinaryTree<>("G");
            BinaryTree<String> nodeH = new BinaryTree<>("H");
            BinaryTree<String> nodeI = new BinaryTree<>("I");
            BinaryTree<String> nodeJ = new BinaryTree<>("J");
            root.setLeftBT(nodeB);
            root.setRightBT(nodeC);
            nodeB.setLeftBT(nodeD);
            nodeB.setRightBT(nodeE);
            nodeC.setRightBT(nodeF);
            nodeF.setLeftBT(nodeG);
            nodeF.setRightBT(nodeH);
            nodeG.setLeftBT(nodeI);
            nodeG.setRightBT(nodeJ);
            return root;
    }
}