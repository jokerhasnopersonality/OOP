package io.github.jokerhasnopersonality;

public class Pizza {
    private final int pizzaNumber;
    private final Order order;

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
