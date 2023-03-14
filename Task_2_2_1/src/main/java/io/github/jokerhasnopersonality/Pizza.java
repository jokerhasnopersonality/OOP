package io.github.jokerhasnopersonality;

public class Pizza {
    private int pizzaNumber;
    private Order order;

    public Pizza(Order order) {
        this.order = order;
        this.pizzaNumber = Pizzeria.newPizzaNumber();
    }

    public Order getOrder() {
        return order;
    }

    public int getPizzaNumber() {
        return pizzaNumber;
    }
}
