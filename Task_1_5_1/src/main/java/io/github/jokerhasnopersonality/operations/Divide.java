package io.github.jokerhasnopersonality.operations;

import java.util.List;

/**
 * Divide function.
 */
public class Divide implements Operation {
    private final int required = 2;

    @Override
    public Double calculate(List<Double> args) throws NullPointerException, IllegalStateException {
        if (args == null) {
            throw new NullPointerException();
        }
        if (args.size() != required) {
            throw new IllegalStateException();
        }
        return (args.get(0) / args.get(1));
    }

    @Override
    public int getRequiredCount() {
        return required;
    }
}
