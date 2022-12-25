package io.github.jokerhasnopersonality.operations;

import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Operation interface for supported functions.
 */
public interface Operation {
    public Double calculate(List<Double> args) throws NullPointerException, IllegalStateException;

    public int getRequiredCount();
}
