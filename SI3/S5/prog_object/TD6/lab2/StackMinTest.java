package lab2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackMinTest {

    StackMin<String> stackSmall;
    StackMin<String> stackOne;
    StackMin<String> stackThree;
    final String firstValue = "8";
    final String infValue = "5";
    final String lastValue = "9";

    @BeforeEach
    void setUp() {
        stackSmall = new StackMin<>();
        stackOne = new StackMin<>();
        stackThree = new StackMin<>();
        stackOne.push(firstValue);
        fillStackWith3values(stackThree);
    }

    private void fillStackWith3values(StackMin<String> stack) {
        stack.push(firstValue);
        stack.push(infValue);
        stack.push(lastValue);
    }
    @Test
    void isEmptyTest() {
        assertTrue(stackSmall.isEmpty());
        assertFalse(stackOne.isEmpty());
    }


    @org.junit.jupiter.api.Test
    void peekInEmptyStackTest()  {
        assertThrows(EmptyStackException.class, () -> {
            stackSmall.peek();
        });
    }

    @org.junit.jupiter.api.Test
    void peekTest() throws EmptyStackException {
        assertEquals(firstValue,
                stackOne.peek());
        assertEquals(firstValue,
                stackOne.findMin());

        assertEquals(infValue,
                stackThree.findMin());
        assertEquals(lastValue,
                stackThree.peek());
        assertEquals(infValue,
                stackThree.findMin());
    }
    @org.junit.jupiter.api.Test
    void testFindMin() throws EmptyStackException {
        assertEquals(firstValue,
                stackOne.findMin());
        assertEquals(infValue,
                stackThree.findMin());
        assertThrows(EmptyStackException.class, () -> {
            stackSmall.findMin();
        });
    }

        @org.junit.jupiter.api.Test
    void pushInEmptyStackTest() throws EmptyStackException {
        stackSmall.push(firstValue);
        assertFalse(stackSmall.isEmpty());
        assertEquals(firstValue,
                stackSmall.peek());
        assertEquals(firstValue,
                    stackSmall.findMin());
    }

    @org.junit.jupiter.api.Test
    void pushInStackTest() throws EmptyStackException {
        stackOne.push(infValue);
        assertEquals(infValue,
                stackOne.peek());
        assertEquals(infValue,
                stackOne.findMin());
        stackOne.push(lastValue);
        assertEquals(infValue,
                stackOne.findMin());
    }

    @org.junit.jupiter.api.Test
    void pushInFullStackTest() throws EmptyStackException {
        fillStackWith3values(stackSmall);
        assertFalse(stackSmall.isEmpty());
        assertEquals(infValue,
                stackSmall.findMin());
    }

    @org.junit.jupiter.api.Test
    void popInEmptyStackTest() {
        assertThrows(EmptyStackException.class, () -> {
            stackSmall.pop();
        });
    }

    @org.junit.jupiter.api.Test
    void popInStackTest() throws EmptyStackException {
        assertEquals(firstValue,
                stackOne.pop());

        assertEquals(infValue,
                stackThree.findMin());
        assertEquals(lastValue,
                stackThree.pop());
        assertEquals(infValue,
                stackThree.findMin());
        assertEquals(infValue,
                stackThree.pop());
        assertEquals(firstValue,
                stackThree.findMin());
    }

}