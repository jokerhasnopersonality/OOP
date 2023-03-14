package io.github.jokerhasnopersonality;

import java.util.*;
import java.util.stream.Collectors;

public class Storage {
    private Map<Order, List<Pizza>> storage;
    private int pizzaCount;
    private final int capacity;
    private final Object lockPizzaiolo;
    private final Object lockDeliveryman;

    public enum StorageStatus {
        EMPTY, FEW, FULL
    }

    public Storage(int capacity) {
        storage = new HashMap<>(capacity);
        pizzaCount = 0;
        this.capacity = capacity;
        lockPizzaiolo = new Object();
        lockDeliveryman = new Object();
    }

    public void placeOrder(Order order) {
        storage.putIfAbsent(order, new ArrayList<>());
    }

    public void passToStorage(List<Pizza> pizzas) throws InterruptedException {
        for (Pizza p : pizzas) {
            synchronized (lockPizzaiolo) {
                while (pizzaCount == capacity) {
                    wait();
                }
                storage.get(p.getOrder()).add(p);
                pizzaCount++;
                notify();
            }
        }
    }

    public List<Pizza> getFromStorage(int trunkCapacity) throws InterruptedException {
        List<Pizza> take = new ArrayList<>();
        int capacity = trunkCapacity;
        List<Order> ready = storage.keySet().stream().filter(
                        x -> x.getPizzaCount() == storage.get(x).size()).
                collect(Collectors.toList());
        synchronized (lockDeliveryman) {
            while (ready.size() == 0) {
                wait();
            }
            for (Order o : ready) {
                if (o.getPizzaCount() <= capacity) {
                    capacity -= o.getPizzaCount();
                    take.addAll(storage.remove(ready.get(0)));
                    notify();
                }
            }
        }
        return take;
    }
}
