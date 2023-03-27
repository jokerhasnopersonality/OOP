package io.github.jokerhasnopersonality;

/**
 * Pizza class.
 */
public class Pizza {
    private final int pizzaNumber;
    private final Order order;

    /**
     * Pizza constructor.
     * Initializes a pizza with specified registry number and order.
     */
    public Pizza(int pizzaNumber, Order order)
            throws IllegalArgumentException, NullPointerException {
        if (order == null) {
            throw new NumberFormatException();
        }
        if (pizzaNumber < 0) {
            throw new IllegalArgumentException();
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
