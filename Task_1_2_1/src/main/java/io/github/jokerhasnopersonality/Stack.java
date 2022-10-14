package io.github.jokerhasnopersonality;

import java.util.Arrays;
import java.util.EmptyStackException;

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
        this.array = (T[]) new Object[INIT_LENGTH];
        this.capacity = INIT_LENGTH;
        this.count = 0;
    }

    /**
     * Changes the size of Stack object's array.
     */
    private void resize(int newSize) {
        capacity = newSize;
        array = Arrays.copyOf(array, capacity);
    }

    /**
     * Adds the element to the end of the stack,
     * increments the count of elements in stack,
     * then checks if the object's array needs resizing.
     */
    public void push(T element) throws NullPointerException{
        if (element == null) {
            throw new NullPointerException("Cannot push \"null\" element to stack.");
        }
        array[count++] = element;
        if (count >= capacity) {
            resize((int) (count * 1.5));
        }
    }

    /**
     * Pushes the array of the given stack to the end of the source stack array,
     * increments the count of elements in source stack,
     * checks if the source stack array needs resizing.
     *
     * @param stack the given stack
     */
    public void pushStack(Stack<T> stack) throws NullPointerException{
        if (stack == null) {
            throw new NullPointerException("Cannot push \"null\" elements to stack.");
        }
        if (stack.count == 0) {
            return;
        }
        count += stack.count;
        if (count >= capacity) {
            resize((int) (count * 1.5));
        }
        for (int i = stack.count; i >= 0; i--) {
            array[count - i] = stack.array[stack.count - i];
        }
    }

    /**
     * Extracts the element from the top of the stack.
     *
     * @return the top element
     */
    public T pop() throws EmptyStackException {
        if (count == 0) {
            throw new EmptyStackException();
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
     *
     * @param popCount number of elements to be extracted from the top of the stack
     * @return a new Stack object with extracted elements
     */
    public Stack<T> popStack(int popCount) {
        if (count < popCount || count == 0) {
            return null;
        }
        Stack<T> newStack = new Stack<T>();
        newStack.count = popCount;
        newStack.resize((int) (popCount * 1.5));
        this.count -= popCount;
        for (int i = 0; i < popCount; i++) {
            newStack.array[i] = this.array[this.count + i];
            this.array[this.count + i] = null;
        }
        if (count < capacity / 4) {
            resize(capacity / 2);
        }
        return newStack;
    }

    public int count() {
        return count;
    }
}