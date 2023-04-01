package io.github.jokerhasnopersonality;

import java.util.ArrayList;
import java.util.List;

/**
 * Pizzeria class for simulating work of pizzaiolos, deliverymen and functioning of storage.
 */
public class Pizzeria {
    private final Storage storage;
    private final List<Pizzaiolo> pizzaiolos;
    private final List<Deliveryman> deliverymen;
    private static int orderCount;

    /**
     * Pizzeria constructor. Creates a storage with the specified capacity.
     */
    public Pizzeria(int storageCapacity) {
        storage = new Storage(storageCapacity);
        pizzaiolos = new ArrayList<>();
        deliverymen = new ArrayList<>();
        orderCount = -1;
    }

    /**
     * Method for starting pizzaiolos and deliverymen as threads.
     * Order queue should not be empty.
     */
    public void start() throws IllegalStateException {
        if (pizzaiolos.size() == 0 || deliverymen.size() == 0) {
            throw new IllegalStateException(
                    "Cannot start without pizzaiolos and deliverymen.");
        }
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
        pizzaiolos.add(new Pizzaiolo(cookingTime, maxCountAtOnce, storage));
    }

    public void addDeliveryman(int trunkCapacity) {
        deliverymen.add(new Deliveryman(trunkCapacity, storage));
    }

    public static int newOrderNumber() {
        return ++orderCount;
    }

    public Storage getStorage() {
        return storage;
    }
}
