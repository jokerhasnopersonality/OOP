package io.github.jokerhasnopersonality;

/**
 * Class for pairs of complex orders and single pizza order numbers.
 */
public class OrderPair {
    private final Order order;
    private final int pizzaNumber;

    /**
     * OrderPair constructor.
     * Creates a pair of a complex order and pizza order number.
     */
    public OrderPair(Order order, int pizzaNumber)
            throws NullPointerException, IllegalArgumentException {
        if (order == null) {
            throw new NullPointerException();
        }
        if (pizzaNumber < 0) {
            throw new IllegalStateException();
        }
        this.order = order;
        this.pizzaNumber = pizzaNumber;
    }

    public Order getOrder() {
        return order;
    }

    public int getPizzaNumber() {
        return pizzaNumber;
    }
}
