package io.github.jokerhasnopersonality;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Storage class representing storage for pizza orders.
 */
public class Storage {
    private static Queue<Order> queue;
    private final Queue<Pizza> storage;
    private final int capacity;
    private final Object lockQueue;
    private final Object lockStorage;
    private int countWork;

    /**
     * Storage constructor. Initializes a storage with specified capacity.
     */
    public Storage(int capacity) throws IllegalArgumentException {
        if (capacity < 10) {
            throw new IllegalArgumentException(
                    "Storage capacity must be a positive integer >=10.");
        }
        queue = new ArrayDeque<>();
        storage = new ArrayDeque<>(capacity);
        this.capacity = capacity;
        lockQueue = new Object();
        lockStorage = new Object();
        countWork = 0;
    }

    /**
     * Adds an order to the order queue and displays a message [ QUEUE ]
     * for every pizza order.
     */
    public void placeOrder(Order order) throws NullPointerException {
        if (order == null) {
            throw new NullPointerException();
        }
        synchronized (lockQueue) {
            queue.add(order);
        }
        System.out.println("ORDER [" + order.getOrderNumber() + "]: [ QUEUE ]");
    }

    /**
     * Method for pizzaiolos to take pizza orders for baking.
     * Displays a message [ BAKING ] about every pizza order taken by pizzaiolo.

     * @param maxCount maximum number of pizzas that a pizzaiolo can bake at once.
     * @return List of individual pizza orders from different pizzeria orders.
     */
    public List<Order> getOrders(int maxCount) {
        List<Order> orders = new ArrayList<>();
        int count = 0;
        Order take;
        synchronized (lockQueue) {
            while (count < maxCount && !queue.isEmpty()) {
                take = queue.poll();
                orders.add(take);
                System.out.println("ORDER [" + take.getOrderNumber()
                        + "]: [ BAKING ]");
                count++;
            }
        }
        countWork++;
        return orders;
    }

    /**
     * Adds baked pizzas to storage orders.
     * Displays a message [ STORAGE ] about every pizza passed to storage.
     */
    public void passPizzas(List<Pizza> pizzas)
            throws NullPointerException, InterruptedException {
        if (pizzas == null) {
            throw new NullPointerException();
        }
        for (Pizza p : pizzas) {
            synchronized (lockStorage) {
                while (storage.size() == capacity) {
                    lockStorage.wait();
                }
                storage.add(p);
                System.out.println("ORDER ["
                        + p.getOrder().getOrderNumber() + "]: [ STORAGE ]");
                lockStorage.notifyAll();
            }
        }
        countWork--;
    }

    /**
     * Retrieves list of orders that a deliveryman can take
     * according to specified trunk capacity. Displays a message
     * [ DELIVERY ] about every complex order taken by deliveryman.
     */
    public List<Pizza> getPizzas(int trunkCapacity) throws InterruptedException {
        List<Pizza> pizzas = new ArrayList<>();
        int count = 0;
        Pizza take;
        synchronized (lockStorage) {
            while (storage.isEmpty() && countWork > 0) {
                lockStorage.wait();
            }
            while (!storage.isEmpty() && count < trunkCapacity) {
                count++;
                take = storage.poll();
                pizzas.add(take);
                System.out.println("ORDER ["
                        + take.getOrder().getOrderNumber() + "]: [ DELIVERY ]");
                lockStorage.notifyAll();
            }
        }
        return pizzas;
    }

    public boolean noOrders() {
        return queue.isEmpty();
    }

    public boolean noPizzas() {
        return storage.isEmpty();
    }
}