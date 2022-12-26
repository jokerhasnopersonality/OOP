package io.github.jokerhasnopersonality;

import io.github.jokerhasnopersonality.operations.Operation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Stack;


/**
 * Calculator class for mathematical expressions in prefix form.
 */
public class Calculator {
    /**
     * Method for calculating a given prefix-expression.
     * After operation is read and identified, method searches the required
     * amount of numbers to execute an operation(1 or 2 operands).
     */
    public static Double calculate(String expression) {
        Stack<Double> numbers = new Stack<>();

        List<String> list = new ArrayList<>(List.of(expression.split(" ")));
        Collections.reverse(list);
        Optional<Operation> operation = null;
        for (String token : list) {
            if (token.equals("")) {
                continue;
            }
            try {
                numbers.push(Double.parseDouble(token));
            } catch (NumberFormatException e) {
                if (token.equals("e")) {
                    numbers.push(Math.E);
                } else if (token.equals("pi")) {
                    numbers.push(Math.PI);
                } else {
                    try {
                        operation = OperationFactory.getOperation(token);
                        if (operation.isEmpty()) {
                            throw new NullPointerException(
                                    String.format("Operation %s is not supported.", token));
                        }
                        numbers.push(operation.get().calculate(numbers));
                    } catch (IllegalStateException ee) {
                        throw new IllegalStateException("Failed to identify expression.");
                    }
                }
            }
        }

        if (numbers.size() != 1) {
            throw new IllegalStateException("Expression is not valid.");
        }
        return numbers.pop();
    }
}