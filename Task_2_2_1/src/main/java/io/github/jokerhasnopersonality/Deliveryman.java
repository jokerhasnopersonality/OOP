package io.github.jokerhasnopersonality;

import java.util.List;

/**
 * Deliveryman class.
 */
public class Deliveryman implements Runnable {
    private final int trunkCapacity;
    private final Storage storage;

    /**
     * Deliveryman constructor.
     * Initializes a deliveryman with specified characteristics.

     * @param trunkCapacity maximum number of pizzas that can fit inside a delivery car.
     * @param storage pizzeria from where a deliveryman should take orders to deliver.
     */
    public Deliveryman(int trunkCapacity, Storage storage)
            throws IllegalArgumentException, NullPointerException {
        if (storage == null) {
            throw new NumberFormatException();
        }
        if (trunkCapacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.trunkCapacity = trunkCapacity;
        this.storage = storage;
    }

    public int getTrunkCapacity() {
        return trunkCapacity;
    }

    @Override
    public void run() {
        List<Pizza> pizzas;
        while (!storage.noOrders() || !storage.noPizzas()) {
            try {
                pizzas = storage.getPizzas(trunkCapacity);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (Pizza p : pizzas) {
                try {
                    Thread.sleep(p.getOrder().getDeliveryTime() * 100L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("ORDER [" + p.getOrder().getOrderNumber() + "]: [ DELIVERED ]");
            }
        }
    }
}
