package io.github.jokerhasnopersonality;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests to check Calculator realization.
 */
public class CalculatorTest {
    @Test
    public void test0() {
        Assertions.assertThrows(NullPointerException.class, () -> Calculator.calculate(null));
        Assertions.assertThrows(IllegalStateException.class, () -> Calculator.calculate(""));
        Assertions.assertThrows(IllegalStateException.class, () -> Calculator.calculate("sin"));
        Assertions.assertThrows(IllegalStateException.class, () -> Calculator.calculate("hehe"));
        Assertions.assertThrows(IllegalStateException.class, () -> Calculator.calculate("3 2"));
        Assertions.assertThrows(IllegalStateException.class, () -> Calculator.calculate(
                "+ 1 2 cos 0 1"));
    }

    @Test
    public void simpleTest() {
        Assertions.assertEquals(1, Calculator.calculate("1"));

        Assertions.assertEquals(1, Calculator.calculate("log e"));
        Assertions.assertEquals(1, Calculator.calculate("sin / pi 2"));

        String expr = "sin + - 1 2 1";
        Double res = Calculator.calculate(expr);
        Assertions.assertEquals(0, res);

        expr = "log sqrt * 128 / cos 0 pow 2 6";
        res = Calculator.calculate(expr);
        Assertions.assertEquals(Math.log(Math.sqrt(2)), res);
    }
}