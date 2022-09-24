package io.github.jokerhasnopersonality;

import java.util.Arrays;

public class Stack<T> {
    private static final int init_length = 2;
    private int count;
    private int capacity;
    private Object[] array;
    public Stack<T> initStack {
        this.array = new Object[init_length];
        this.capacity = init_length;
        this.count = 0;
        return this;
    }

    public void reserve() {
        while (count >= capacity) {
            capacity *= 2;
            array = Arrays.copyof(array, capacity);
        }
        return;
        while (count > 0 && count < capacity / 4) {
            capacity /= 2;
            array = Arrays.copyof(array, capacity);
        }
    }

    public void push(T element) {
        if (element == null) {
            return;
        }
        array[count++] = element;
        reserve();
    }

    public void pushStack(Stack<T> stack) {
        if (stack == null) {
            return;
        }
        this.count += stack.count;
        this.reserve();
        for (int i = stack.count; i >= 0; i--) {
            array[count - i] = stack.array[stack.count - i];
        }
    }

    public void pop() {
        T popped = array[--count];
        array[count] = null;
        reserve();
    }

    public Stack<T> popStack(int pop_count) {
        if (this.count < pop_count) {
            return null;
        }
        Stack<T> new_stack = new Stack<T>().initStack();
        new_stack.count = pop_count;
        new_stack.reserve();
        this.count -= pop_count;
        for (int i = 0; i < pop_count; i++) {
            new_stack.array[i] = this.array[this.count + i];
            this.array
        }
        reserve();
        return new_stack;
    }

    public int count() {
        return count;
    }
}
