package io.github.jokerhasnopersonality;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class representing a complex order. May contain one or more regular pizzas orders.
 */
public class Order {
    private final int pizzaCount;
    private final int orderNumber;
    private final int deliveryTime;
    private final int[] pizzaNumbers;
    private final List<Pizza> pizzas;

    /**
     * Order constructor.
     * Initializes an order with given number of pizzas and delivery time.
     */
    public Order(int pizzaCount, int deliveryTime) throws IllegalArgumentException {
        if (pizzaCount <= 0 || deliveryTime <= 0) {
            throw new IllegalArgumentException();
        }
        this.pizzaCount = pizzaCount;
        this.deliveryTime = deliveryTime;
        orderNumber = Pizzeria.newOrderNumber();
        pizzaNumbers = new int[pizzaCount];
        for (int i = 0; i < pizzaCount; i++) {
            pizzaNumbers[i] = Pizzeria.newPizzaNumber();
        }
        pizzas = new ArrayList<>();
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getPizzaCount() {
        return pizzaCount;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public int[] getPizzaNumbers() {
        return pizzaNumbers;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * Adds a pizza to order's pizza list if it has a relevant registry number.
     */
    public void addPizza(Pizza pizza) throws NullPointerException {
        if (pizza == null) {
            throw new NullPointerException();
        }
        if (Arrays.stream(pizzaNumbers).anyMatch(x -> x == pizza.getPizzaNumber())) {
            pizzas.add(pizza);
        }
    }
}
