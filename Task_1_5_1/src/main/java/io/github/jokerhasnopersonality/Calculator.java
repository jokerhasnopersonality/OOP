package io.github.jokerhasnopersonality;

import java.util.Scanner;
import java.util.Stack;

public class Calculator {
    public enum Operations {
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE,
        LOG,
        POW,
        SQRT,
        SIN,
        COS
    }

    private int required;
    private Stack<Double> numbers;
    private Stack<Operations> stack;

    public Double calc(String expression) throws AssertionError {
        required = 0;
        numbers = new Stack<>();
        stack = new Stack<>();
        Scanner scanner = new Scanner(expression);
        String curr;

        while(scanner.hasNext()) {
            if (scanner.hasNextDouble()) {
                numbers.push(scanner.nextDouble());
            } else {
                curr = scanner.next();
                switch (curr) {
                    case ("+"):
                        stack.push(Operations.PLUS);
                        required = 2;
                        break;
                    case ("-"):
                        stack.push(Operations.MINUS);
                        required = 2;
                        break;
                    case ("*"):
                        stack.push(Operations.MULTIPLY);
                        required = 2;
                        break;
                    case ("/"):
                        stack.push(Operations.DIVIDE);
                        required = 2;
                        break;
                    case ("pow"):
                        stack.push(Operations.POW);
                        required = 2;
                        break;
                    case ("log"):
                        stack.push(Operations.LOG);
                        required = 1;
                        break;
                    case ("sqrt"):
                        stack.push(Operations.SQRT);
                        required = 1;
                        break;
                    case ("sin"):
                        stack.push(Operations.SIN);
                        required = 1;
                        break;
                    case ("cos"):
                        stack.push(Operations.COS);
                        required = 1;
                        break;
                    default:
                        throw new AssertionError();
                }
            }

            if (required == numbers.size()) {
                execute(required);
            }
        }

        scanner.close();
        return numbers.pop();
    }

    private void execute(int operands) {
        Double b = numbers.pop();
        Operations operation = stack.pop();
        if (operands == 2) {
            Double a = numbers.pop();
            switch (operation) {
                case PLUS:
                    b = a + b;
                    break;
                case MINUS:
                    b = a - b;
                    break;
                case MULTIPLY:
                    b = a * b;
                    break;
                case DIVIDE:
                    b = a / b;
                    break;
                case POW:
                    b = Math.pow(a, b);
                    break;
            }
        } else if (operands == 1) {
            switch (operation) {
                case LOG:
                    b = Math.log(b);
                    break;
                case SQRT:
                    b = Math.sqrt(b);
                    break;
                case SIN:
                    b = Math.sin(b);
                    break;
                case COS:
                    b = Math.cos(b);
                    break;
            }
        }
        operation = stack.peek();
        if (operation==Operations.LOG || operation==Operations.SQRT
                || operation==Operations.SIN || operation == Operations.COS) {
            required = 1;
        } else {
            required = 2;
        }

        numbers.push(b);
    }
}