package io.github.jokerhasnopersonality;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;

/**
 * Tests to check class Stack.
 */
public class StackTest {
    @Test
    public void test0() {
        Stack<Integer> stack0 = new Stack<Integer>();
        assertEquals(0, stack0.count());
    }

    @Test
    public void simple_test() {
        Stack<Integer> stack1 = new Stack<Integer>();
        stack1.push(1);
        stack1.push(3);
        Stack<Integer> stack2 = new Stack<Integer>();
        stack2.push(5);
        stack2.push(9);

        stack1.pushStack(stack2);
        assertEquals(4, stack1.count());
        assertEquals(9, stack1.pop());

        Stack<Integer> testStack = stack1.popStack(3);
        assertEquals(5, testStack.pop());
        assertEquals(2, testStack.count());
        testStack.push(0);
        testStack.push(12);
        assertEquals(4, testStack.count());
        assertEquals(null, testStack.popStack(5));
    }

    @Test
    public void test_exception() {
        Stack<Integer> stack = new Stack<Integer>();
        try {
            stack.push(null);
        } catch (NullPointerException thrown) {
            assertEquals(0, stack.count());
        }
        try {
            stack.pushStack(null);
        } catch (NullPointerException thrown) {
            assertEquals(0, stack.count());
        }
        try {
            stack.pop();
        } catch (EmptyStackException thrown) {
            assertEquals(0, stack.count());
        }
    }
}