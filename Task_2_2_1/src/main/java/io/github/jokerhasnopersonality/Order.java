package io.github.jokerhasnopersonality;

/**
 * Class representing a complex order. May contain one or more regular pizzas orders.
 */
public class Order {
    private final int orderNumber;
    private final int deliveryTime;

    /**
     * Order constructor.
     * Initializes an order with given number of pizzas and delivery time.
     */
    public Order(int deliveryTime, int orderNumber) throws IllegalArgumentException {
        if (deliveryTime <= 0 || orderNumber < 0) {
            throw new IllegalArgumentException();
        }
        this.deliveryTime = deliveryTime;
        this.orderNumber = orderNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }
}
