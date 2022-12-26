package io.github.jokerhasnopersonality.operations;

import java.util.Stack;

/**
 * Multiply function.
 */
public class Multiply implements Operation {
    private final int required = 2;

    @Override
    public Double calculate(Stack<Double> args) throws NullPointerException, IllegalStateException {
        if (args == null) {
            throw new NullPointerException();
        }
        if (args.size() < required) {
            throw new IllegalStateException();
        }
        return (args.pop() * args.pop());
    }
}
