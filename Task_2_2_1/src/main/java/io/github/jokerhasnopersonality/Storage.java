package io.github.jokerhasnopersonality;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Storage class representing storage for pizza orders.
 */
public class Storage {
    private final Queue<Order> storage;
    private int pizzaCount;
    private final int capacity;
    private final Object lock;

    /**
     * Storage constructor. Initializes a storage with specified capacity.
     */
    public Storage(int capacity) throws IllegalArgumentException {
        if (capacity < 10) {
            throw new IllegalArgumentException(
                    "Storage capacity must be a positive integer >=10.");
        }
        storage = new ArrayDeque<>(capacity);
        pizzaCount = 0;
        this.capacity = capacity;
        lock = new Object();
    }

    /**
     * Places an order for pizzas at the storage.
     */
    public void placeOrder(Order order)
            throws NullPointerException, IllegalArgumentException {
        if (order == null) {
            throw new NullPointerException();
        }
        storage.offer(order);
    }

    /**
     * Adds baked pizzas to storage orders.
     * Displays a message [ STORAGE ] about every pizza passed to storage.
     */
    public void passToStorage(List<Pizza> pizzas) throws InterruptedException {
        for (Pizza p : pizzas) {
            synchronized (lock) {
                while (pizzaCount == capacity) {
                    lock.wait();
                }
                p.getOrder().addPizza(p);
                System.out.println("ORDER ["
                        + p.getOrder().getOrderNumber() + "] - PIZZA ["
                        + p.getPizzaNumber() + "]: [ STORAGE ]");
                pizzaCount++;
                lock.notifyAll();
            }
        }
    }

    /**
     * Retrieves list of orders that a deliveryman can take
     * according to specified trunk capacity. Displays a message
     * [ DELIVERY ] about every complex order taken by deliveryman.
     */
    public List<Order> getFromStorage(int trunkCapacity) throws InterruptedException {
        List<Order> orders = new ArrayList<>();
        int count = 0;
        Order take;
        List<Order> ready = storage.stream().filter(
                        x -> x.getPizzaCount() == x.getPizzas().size())
                        .collect(Collectors.toList());
        synchronized (lock) {
            while (!storage.isEmpty() && storage.stream().noneMatch(
                    x -> x.getPizzaCount() == x.getPizzas().size())) {
                lock.wait();
            }
            for (Order o : ready) {
                if (count < trunkCapacity) {
                    count += o.getPizzaCount();
                    orders.add(ready.get(0));
                    storage.remove(ready.get(0));
                    System.out.println("ORDER [" + o.getOrderNumber() + "] : [ DELIVERY ]");
                    pizzaCount += o.getPizzaCount();
                    lock.notifyAll();
                }
            }
        }
        return orders;
    }

    public boolean isEmpty() {
        return storage.isEmpty();
    }
}