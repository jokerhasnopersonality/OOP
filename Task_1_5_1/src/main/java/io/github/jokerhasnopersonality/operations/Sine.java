package io.github.jokerhasnopersonality.operations;

import java.util.Stack;

/**
 * Sine function.
 */
public class Sine implements Operation {
    private final int required = 1;

    @Override
    public Double calculate(Stack<Double> args) throws NullPointerException, IllegalStateException {
        if (args == null) {
            throw new NullPointerException();
        }
        if (args.size() < required) {
            throw new IllegalStateException();
        }
        return (Math.sin(args.pop()));
    }
}
