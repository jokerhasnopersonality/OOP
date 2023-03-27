package io.github.jokerhasnopersonality;

import java.util.ArrayList;
import java.util.List;

/**
 * Pizzaiolo class representing a pizza baker.
 */
public class Pizzaiolo implements Runnable {
    private final int cookingTime;
    private final int maxCountAtOnce;
    private final Pizzeria pizzeria;

    /**
     * Pizzaiolo constructor. Initializes a baker with specified characteristics.

     * @param cookingTime time of one baking session
     * @param maxCountAtOnce maximum number of pizza that pizzaiolo can bake per session
     * @param pizzeria pizzeria pizzeria where pizzaiolo should pass baked pizzas
     */
    public Pizzaiolo(int cookingTime, int maxCountAtOnce, Pizzeria pizzeria)
            throws IllegalArgumentException, NullPointerException {
        if (pizzeria == null) {
            throw new NumberFormatException();
        }
        if (cookingTime <= 0 || maxCountAtOnce <= 0) {
            throw new IllegalArgumentException();
        }
        this.cookingTime = cookingTime;
        this.maxCountAtOnce = maxCountAtOnce;
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {
        List<OrderPair> pizzaOrders = pizzeria.getFromQueue(maxCountAtOnce);
        while (!pizzaOrders.isEmpty()) {
            try {
                Thread.sleep(cookingTime * 100L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            List<Pizza> pizzas = new ArrayList<>(pizzaOrders.size());
            for (OrderPair p : pizzaOrders) {
                pizzas.add(new Pizza(p.getPizzaNumber(), p.getOrder()));
                System.out.println("ORDER [" + p.getOrder().getOrderNumber()
                        + "] - PIZZA [" + p.getPizzaNumber() + "]: [ READY ]");
            }
            try {
                pizzeria.getStorage().passToStorage(pizzas);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            pizzaOrders = pizzeria.getFromQueue(maxCountAtOnce);
        }
    }
}