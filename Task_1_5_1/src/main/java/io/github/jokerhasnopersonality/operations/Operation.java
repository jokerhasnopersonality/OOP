package io.github.jokerhasnopersonality.operations;

import java.util.Stack;

/**
 * Operation interface for supported functions.
 */
public interface Operation {
    public Double calculate(Stack<Double> args) throws NullPointerException, IllegalStateException;
}
