package io.github.jokerhasnopersonality;

import java.util.List;

/**
 * Deliveryman class.
 */
public class Deliveryman implements Runnable {
    private final int trunkCapacity;
    private final Pizzeria pizzeria;

    /**
     * Deliveryman constructor.
     * Initializes a deliveryman with specified characteristics.

     * @param trunkCapacity maximum number of pizzas that can fit inside a delivery car.
     * @param pizzeria pizzeria from where a deliveryman should take orders to deliver.
     */
    public Deliveryman(int trunkCapacity, Pizzeria pizzeria)
            throws IllegalArgumentException, NullPointerException {
        if (pizzeria == null) {
            throw new NumberFormatException();
        }
        if (trunkCapacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.trunkCapacity = trunkCapacity;
        this.pizzeria = pizzeria;
    }

    public int getTrunkCapacity() {
        return trunkCapacity;
    }

    @Override
    public void run() {
        List<Order> orders;
        while (!pizzeria.getStorage().isEmpty()) {
            try {
                orders = pizzeria.getStorage().getFromStorage(trunkCapacity);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (Order o : orders) {
                try {
                    Thread.sleep(o.getDeliveryTime() * 100L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("ORDER [" + o.getOrderNumber() + "] : [ DELIVERED ]");
            }
        }
    }
}
