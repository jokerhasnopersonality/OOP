package io.github.jokerhasnopersonality.operations;

import java.util.Stack;

/**
 * Plus function.
 */
public class Plus implements Operation {
    private final int required = 2;

    @Override
    public Double calculate(Stack<Double> args) throws NullPointerException, IllegalStateException {
        if (args == null) {
            throw new NullPointerException();
        }
        if (args.size() < required) {
            throw new IllegalStateException();
        }
        return (args.pop() + args.pop());
    }
}
