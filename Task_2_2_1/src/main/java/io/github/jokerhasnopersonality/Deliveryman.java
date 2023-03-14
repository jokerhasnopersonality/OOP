package io.github.jokerhasnopersonality;

import java.util.ArrayList;
import java.util.List;

public class Deliveryman implements Runnable {
    private int trunkCapacity;
    List<Order> orderList;

    public Deliveryman(int trunkCapacity) throws IllegalArgumentException {
        if (trunkCapacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.trunkCapacity = trunkCapacity;
        orderList = new ArrayList<>();
    }

    public void getOrders() {
    }

    public void deliver() {
    }

    public int getTrunkCapacity() {
        return trunkCapacity;
    }

    @Override
    public void run() {
    }
}
