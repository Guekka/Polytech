package lab2;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayStackTest {

    ArrayStack<String> stackSmall;
    ArrayStack<String> stackOne;
    ArrayStack<String> stackThree;

    ArrayStack<Integer> stack4Integer;
    final String firstValue = "10";
    final String infValue = "5";
    final String lastValue = "20";
    @BeforeEach
    void setUp() {
        stackSmall = new ArrayStack<>(1);
        stackOne = new ArrayStack<>();
        stackThree = new ArrayStack<>();
        stackOne.push(firstValue);
        fillStackWith3values(stackThree);
        stack4Integer = new ArrayStack<>();
    }


    private void fillStackWith3values(ArrayStack<String> stack) {
        assert stack != null;
        stack.push(firstValue);
        stack.push(infValue);
        stack.push(lastValue);
    }

    @org.junit.jupiter.api.Test
    void isEmptyTest() {
        assertTrue(stackSmall.isEmpty());
        assertFalse(stackOne.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void sizeTest() {
        assertEquals(0, stackSmall.size());
        assertEquals(1, stackOne.size());
    }

    @org.junit.jupiter.api.Test
    void peekInEmptyStackTest()  {
        assertThrows(EmptyStackException.class, () -> {
            stackSmall.peek();
        });
    }

    @org.junit.jupiter.api.Test
    void peekTest() throws EmptyStackException {
        int size = stackOne.size();
        assertEquals(firstValue,
                stackOne.peek());
        assertEquals(size,stackOne.size());

        assertEquals(lastValue,
                stackThree.peek());

    }


    @org.junit.jupiter.api.Test
    void pushInEmptyStackTest() throws EmptyStackException {
        stackSmall.push(firstValue);
        assertFalse(stackSmall.isEmpty());
        assertEquals(firstValue,
                stackSmall.peek());
    }

    @org.junit.jupiter.api.Test
    void pushInStackTest() throws EmptyStackException {
        stackOne.push(infValue);
        assertEquals(2, stackOne.size());
        assertEquals(infValue,
                stackOne.peek());
    }

    @org.junit.jupiter.api.Test
    void pushInIntegerStackTest() throws EmptyStackException {
        stack4Integer.push(3);
        assertEquals(1, stack4Integer.size());
       // int x = stack4Integer.peek();
        assertEquals(3,
                stack4Integer.peek());
    }

    @org.junit.jupiter.api.Test
    void pushInFullStackTest()  {
        fillStackWith3values(stackSmall);
        assertFalse(stackSmall.isEmpty());
        assertEquals(3,
                stackSmall.size());
    }

    @org.junit.jupiter.api.Test
    void popInEmptyStackTest() {
        assertThrows(EmptyStackException.class, () -> {
            stackSmall.pop();
        });
    }

    @org.junit.jupiter.api.Test
    void popInStackTest() throws EmptyStackException {
        int size = stackOne.size();
        //String value =stackOne.peek();
        assertEquals(firstValue,
                stackOne.pop());
        assertEquals(size-1, stackOne.size());

        size = stackThree.size();
        assertEquals(lastValue,
                    stackThree.pop());
        assertEquals(size-1, stackThree.size());
    }



    @org.junit.jupiter.api.Test
    void testToString() {
        assertEquals("[ ->", stackSmall.toString());
        assertEquals("[ 10 ->", stackOne.toString());
        assertEquals("[ 10 5 20 ->", stackThree.toString());
    }
}