package io.github.jokerhasnopersonality;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Tests to check class Stack.
 */
public class StackTest {
    @Test
    public void test0() {
        Stack<Integer> stack0 = new Stack<Integer>();
        assertEquals(0, stack0.count());
        assertEquals(null, stack0.pop());
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

        Stack<Integer> test_stack = stack1.popStack(3);
        assertEquals(5, test_stack.pop());
        assertEquals(2, test_stack.count());
        test_stack.push(0);
        test_stack.push(12);
        assertEquals(4, test_stack.count());
    }
}