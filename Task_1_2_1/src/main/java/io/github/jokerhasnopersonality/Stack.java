package io.github.jokerhasnopersonality;

import java.util.Arrays;

/**
 * Stack wrapper.
 *
 * @param <T> type of Stack elements.
 */
public class Stack<T> {
    private static final int INIT_LENGTH = 2;
    private int count;
    private int capacity;
    private T[] array;

    /**
     * Creates a new Stack object, which contains an empty array for 2 elements initially.
     */
    @SuppressWarnings("unchecked")
    public Stack() {
        this.array = (T[])new Object[INIT_LENGTH];
        this.capacity = INIT_LENGTH;
        this.count = 0;
    }

    /**
     * Changes the size of Stack object's array.
     */
    private void resize(int new_size) {
        capacity = new_size;
        array = Arrays.copyOf(array, capacity);
    }

    /**
     * Adds the element to the end of the stack,
     * increments the count of elements in stack,
     * then checks if the object's array needs resizing.
     */
    public void push(T element) {
        if (element == null) {
            return;
        }
        array[count++] = element;
        if (count >= capacity) {
            resize((int)(count * 1.5));
        }
    }

    /**
     * Pushes the array of the given stack to the end of the source stack array,
     * increments the count of elements in source stack,
     * checks if the source stack array needs resizing.
     * @param stack the given stack
     */
    public void pushStack(Stack<T> stack) {
        if (stack == null || stack.count == 0) {
            return;
        }
        count += stack.count;
        if (count >= capacity) {
            resize((int)(count * 1.5));
        }
        for (int i = stack.count; i >= 0; i--) {
            array[count - i] = stack.array[stack.count - i];
        }
    }

    /**
     * Extracts the element from the top of the stack.
     * @return the top element
     */
    public T pop() {
        if (count == 0) {
            return null;
        }
        count--;
        T popped = array[count];
        array[count] = null;
        if (count < capacity / 4) {
            resize(capacity / 2);
        }
        return popped;
    }

    /**
     * Extracts the assigned number of elements from the source stack.
     * @param pop_count number of elements to be extracted from the top of the stack
     * @return a new Stack object with extracted elements
     */
    public Stack<T> popStack(int pop_count) {
        if (count < pop_count || count == 0) {
            return null;
        }
        Stack<T> new_stack = new Stack<T>();
        new_stack.count = pop_count;
        new_stack.resize((int)(pop_count * 1.5));
        this.count -= pop_count;
        for (int i = 0; i < pop_count; i++) {
            new_stack.array[i] = this.array[this.count + i];
            this.array[this.count + i] = null;
        }
        if (count < capacity / 4) {
            resize(capacity / 2);
        }
        return new_stack;
    }

    public int count() {
        return count;
    }
}