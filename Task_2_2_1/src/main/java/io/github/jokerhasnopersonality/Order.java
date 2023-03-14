package io.github.jokerhasnopersonality;

public class Order {
    private int pizzaCount;
    private int orderNumber;
    private int deliveryTime;
    private int[] pizzaNumbers;

    public Order(int pizzaCount, int deliveryTime) throws IllegalArgumentException {
        if (pizzaCount <= 0 || deliveryTime <= 0) {
            throw new IllegalArgumentException();
        }
        this.pizzaCount = pizzaCount;
        this.deliveryTime = deliveryTime;
        orderNumber = Pizzeria.newOrderNumber();
        pizzaNumbers = new int[pizzaCount];
        for (int number : pizzaNumbers) {
            number = Pizzeria.newPizzaNumber();
        }
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getPizzaCount() {
        return pizzaCount;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public int[] getPizzaNumbers() {
        return pizzaNumbers;
    }
}
