/*
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpressionTreeWithFunctionTest {

    @Test
    void testEvalExpressionSimple() {
        ExpressionTreeWithFunction e = new ExpressionTreeWithFunction("2");
        assertEquals(2.0, e.evaluate(), 0.0);
    }

    @Test
    void testEvalAddition() {
        ExpressionTreeWithFunction e = new ExpressionTreeWithFunction("+", new ExpressionTreeWithFunction("2"), new ExpressionTreeWithFunction("3"));
        assertEquals(5.0, e.evaluate(), 0.0);
    }

    @Test
    void testEvalExposant() {
        ExpressionTreeWithFunction e = new ExpressionTreeWithFunction("^", new ExpressionTreeWithFunction("2"), new ExpressionTreeWithFunction("3"));
        assertEquals(8.0, e.evaluate(), 0.0);
    }

        @Test
        void testEvalWithRead() {
            ExpressionTreeWithFunction e = ExpressionTreeWithFunction.read("- * 2$ 5$ ^ 3$ 2$");
            assertEquals(1.0, e.evaluate(), 0.0);

            e = ExpressionTreeWithFunction.read("+ 5$ * 2$ - 7$ 3$");
            assertEquals(13.0, e.evaluate(), 0.0);

            e =  ExpressionTreeWithFunction.read("- * / - 10$ 4$ 2$ 5$ + 2$ * 3$ 4$");
            assertEquals(1.0, e.evaluate(), 0.0);
        }

    @Test
    void testEvalWithExpression() {
        ExpressionTreeWithFunction e = ExpressionTreeWithFunction.fromExpression("( 2 * 5 ) ");
        assertEquals(10.0, e.evaluate(), 0.0);

        e = ExpressionTreeWithFunction.fromExpression("( 2 * 5 ) - ( 3 ^ 2 )");
        assertEquals(1.0, e.evaluate(), 0.0);

        e = ExpressionTreeWithFunction.fromExpression(" 5 * 2 +  7 - 3");
        assertEquals(14.0, e.evaluate(), 0.0);

        e =  ExpressionTreeWithFunction.fromExpression("( 10 * 4 ) / ( 2 * 5 ) - ( 2 +  ( 3 * 4 ) )");
        assertEquals(-10 , e.evaluate(), 0.0);
    }
    }

 */
