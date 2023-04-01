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
        List<Order> orders;
        List<Pizza> pizzas;
        while (!pizzeria.getStorage().noOrders()) {
            orders = pizzeria.getStorage().getOrders(maxCountAtOnce);
            try {
                Thread.sleep(cookingTime * 100L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            pizzas = new ArrayList<>(orders.size());
            for (Order p : orders) {
                pizzas.add(new Pizza(p));
                System.out.println("ORDER [" + p.getOrderNumber()
                        + "]: [ READY ]");
            }
            try {
                pizzeria.getStorage().passPizzas(pizzas);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}