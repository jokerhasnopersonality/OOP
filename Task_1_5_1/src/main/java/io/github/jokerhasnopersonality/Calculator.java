package io.github.jokerhasnopersonality;

import io.github.jokerhasnopersonality.operations.Operation;
import java.util.List;
import java.util.Scanner;
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
        Stack<Operation> stack = new Stack<>();
        int required = 0;
        Scanner scanner = new Scanner(expression);

        Operation operation = null;
        Double number;
        List<Double> sublist;
        int cnt = 0;                 //for counting numbers after the last read operation
        while (scanner.hasNext()) {
            cnt++;
            if (scanner.hasNext("e")) {
                numbers.push(Math.E);
                scanner.next();
            } else if (scanner.hasNext("pi")) {
                numbers.push(Math.PI);
                scanner.next();
            } else if (scanner.hasNextDouble()) {
                numbers.push(scanner.nextDouble());
            } else {
                operation = OperationFactory.getOperation(scanner.next());
                stack.push(operation);
                required = operation.getRequiredCount();
                cnt = 0;
            }
            while (stack.size() > 1 && required <= numbers.size() && cnt >= required) {
                sublist = numbers.subList(numbers.size() - required, numbers.size());
                number = operation.calculate(sublist);
                sublist.clear();
                numbers.push(number);
                stack.pop();
                required = stack.peek().getRequiredCount();
                operation = stack.peek();
            }
        }
        if (stack.size() > 0 && numbers.size() > 0) {
            if (required <= numbers.size() && cnt >= required) {
                sublist = numbers.subList(numbers.size() - required, numbers.size());
                number = operation.calculate(sublist);
                sublist.clear();
                numbers.push(number);
                stack.pop();
            }
        }

        scanner.close();
        if (numbers.size() != 1) {
            throw new IllegalStateException("Expression is not valid.");
        }
        return numbers.pop();
    }
}