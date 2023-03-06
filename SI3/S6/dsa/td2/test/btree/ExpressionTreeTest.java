package btree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpressionTreeTest {

    @Test
    void testEvalExpressionSimple() {
        ExpressionTree e = new ExpressionTree("2");
        assertEquals(2.0, e.eval(), 0.0);
    }

    @Test
    void testEvalAddition() {
        ExpressionTree e = new ExpressionTree("+", new ExpressionTree("2"), new ExpressionTree("3"));
        assertEquals(5.0, e.eval(), 0.0);
    }

    @Test
    void testEvalExposant() {
        ExpressionTree e = new ExpressionTree("^", new ExpressionTree("2"), new ExpressionTree("3"));
        assertEquals(8.0, e.eval(), 0.0);
    }

    @Test
    void testEval() {
        ExpressionTree e = ExpressionTree.read("- * 2$ 5$ ^ 3$ 2$");
        assertEquals(1.0, e.eval(), 0.0);

        e = ExpressionTree.read("+ 5$ * 2$ - 7$ 3$");
        assertEquals(13.0, e.eval(), 0.0);

        e = ExpressionTree.read("- * / - 10$ 4$ 2$ 5$ + 2$ * 3$ 4$");
        assertEquals(1.0, e.eval(), 0.0);
        }
    }
