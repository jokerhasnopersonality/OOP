package io.github.jokerhasnopersonality;

/**
 * Pizza class.
 */
public class Pizza {
    private final Order order;

    /**
     * Pizza constructor.
     * Initializes a pizza with specified registry number and order.
     */
    public Pizza(Order order)
            throws IllegalArgumentException, NullPointerException {
        if (order == null) {
            throw new NumberFormatException();
        }
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
