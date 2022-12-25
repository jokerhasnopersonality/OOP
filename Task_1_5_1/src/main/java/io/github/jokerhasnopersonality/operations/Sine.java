package io.github.jokerhasnopersonality.operations;

import java.util.List;

/**
 * Sine function.
 */
public class Sine implements Operation {
    private final int required = 1;

    @Override
    public Double calculate(List<Double> args) throws NullPointerException, IllegalStateException {
        if (args == null) {
            throw new NullPointerException();
        }
        if (args.size() != required) {
            throw new IllegalStateException();
        }
        return (Math.sin(args.get(0)));
    }

    @Override
    public int getRequiredCount() {
        return required;
    }
}
