package io.github.jokerhasnopersonality;

import static java.util.Map.entry;

import io.github.jokerhasnopersonality.operations.Cosine;
import io.github.jokerhasnopersonality.operations.Divide;
import io.github.jokerhasnopersonality.operations.Minus;
import io.github.jokerhasnopersonality.operations.Multiply;
import io.github.jokerhasnopersonality.operations.NaturalLogarithm;
import io.github.jokerhasnopersonality.operations.Operation;
import io.github.jokerhasnopersonality.operations.Plus;
import io.github.jokerhasnopersonality.operations.Power;
import io.github.jokerhasnopersonality.operations.Sine;
import io.github.jokerhasnopersonality.operations.SquareRoot;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory for identifying supported operations.
 */
public class OperationFactory {
    static HashMap<String, Operation> map = new HashMap<>(Map.ofEntries(
            entry("+", new Plus()),
            entry("-", new Minus()),
            entry("*", new Multiply()),
            entry("/", new Divide()),
            entry("log", new NaturalLogarithm()),
            entry("pow", new Power()),
            entry("sqrt", new SquareRoot()),
            entry("sin", new Sine()),
            entry("cos", new Cosine())
    ));

    /**
     * Identifies an operation based on the "database".

     * @throws IllegalStateException if an operation can't be identified
     */
    public static Operation getOperation(String op) throws IllegalStateException {
        if (map.get(op) == null) {
            throw new IllegalStateException("Operation is not supported.");
        }
        return map.get(op);
    }
}
