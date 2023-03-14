package io.github.jokerhasnopersonality;

import java.util.ArrayDeque;
import java.util.Queue;

public class Pizzeria {
    private Queue<Pizza> orderQueue;
    private Storage storage;
    private Pizzaiolo[] pizzaiolos;
    private Deliveryman[] deliverymen;
    private static int pizzaCount;
    private static int orderCount;

    public Pizzeria(int pizzaioloCount, int deliverymenCount, int storageCapacity) {
        orderQueue = new ArrayDeque<>();
        storage = new Storage(storageCapacity);
        pizzaiolos = new Pizzaiolo[pizzaioloCount];
        deliverymen = new Deliveryman[deliverymenCount];
        pizzaCount = 0;
        orderCount = 0;
    }

    public static int newPizzaNumber() {
        return pizzaCount++;
    }

    public static int newOrderNumber() {
        return orderCount++;
    }

    public void addToQueue(Pizza pizza) {
    }
}
