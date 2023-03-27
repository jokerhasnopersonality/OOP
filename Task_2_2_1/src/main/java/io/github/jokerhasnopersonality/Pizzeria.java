package io.github.jokerhasnopersonality;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Pizzeria class for simulating work of pizzaiolos, deliverymen and functioning of storage.
 */
public class Pizzeria {
    private static Queue<OrderPair> queue;
    private final Storage storage;
    private List<Pizzaiolo> pizzaiolos;
    private List<Deliveryman> deliverymen;
    private static int pizzaCount;
    private static int orderCount;
    private final Object lock;

    /**
     * Pizzeria constructor. Creates a storage with the specified capacity.
     */
    public Pizzeria(int storageCapacity) {
        queue = new ArrayDeque<>();
        storage = new Storage(storageCapacity);
        pizzaiolos = new ArrayList<>();
        deliverymen = new ArrayList<>();
        pizzaCount = -1;
        orderCount = -1;
        lock = new Object();
    }

    /**
     * Method for starting pizzaiolos and deliverymen as threads.
     * Order queue should not be empty.
     */
    public void start() {
        Thread[] bakingThreads = new Thread[pizzaiolos.size()];
        Thread[] deliveryThreads = new Thread[deliverymen.size()];
        for (int i = 0; i < bakingThreads.length; i++) {
            bakingThreads[i] = new Thread(pizzaiolos.get(i));
            bakingThreads[i].start();
        }
        for (int i = 0; i < deliveryThreads.length; i++) {
            deliveryThreads[i] = new Thread(deliverymen.get(i));
            deliveryThreads[i].start();
        }
        for (Thread thread : bakingThreads) {
            if (thread.isAlive()) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        for (Thread thread : deliveryThreads) {
            if (thread.isAlive()) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void addPizzaiolo(int cookingTime, int maxCountAtOnce) {
        pizzaiolos.add(new Pizzaiolo(cookingTime, maxCountAtOnce, this));
    }

    public void addDeliveryman(int trunkCapacity) {
        deliverymen.add(new Deliveryman(trunkCapacity, this));
    }

    public static int newPizzaNumber() {
        return ++pizzaCount;
    }

    public static int newOrderNumber() {
        return ++orderCount;
    }

    /**
     * Adds an order to the order queue and displays a message [ QUEUE ]
     * for every pizza order.
     */
    public void addToQueue(Order order) {
        storage.placeOrder(order);
        for (int i = 0; i < order.getPizzaNumbers().length; i++) {
            queue.add(new OrderPair(order, order.getPizzaNumbers()[i]));
            System.out.println("ORDER [" + order.getOrderNumber() + "] - PIZZA ["
                    + order.getPizzaNumbers()[i] + "]: [ QUEUE ]");
        }
    }

    /**
     * Method for pizzaiolos to take pizza orders for baking.
     * Displays a message [ BAKING ] about every pizza order taken by pizzaiolo.

     * @param maxCount maximum number of pizzas that a pizzaiolo can bake at once.
     * @return List of individual pizza orders from different pizzeria orders.
     */
    public List<OrderPair> getFromQueue(int maxCount) {
        List<OrderPair> orders = new ArrayList<>();
        int count = 0;
        while (count < maxCount && !queue.isEmpty()) {
            synchronized (lock) {
                System.out.println("ORDER [" + queue.peek().getOrder().getOrderNumber()
                        + "] - PIZZA [" + queue.peek().getPizzaNumber() + "]: [ BAKING ]");
                orders.add(queue.poll());
            }
            count++;
        }
        return orders;
    }

    public Storage getStorage() {
        return storage;
    }
}
