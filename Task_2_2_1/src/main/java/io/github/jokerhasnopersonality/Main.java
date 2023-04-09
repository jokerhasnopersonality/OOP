package io.github.jokerhasnopersonality;

import java.io.IOException;

/**
 * Main class to start pizzeria.
 */
public class Main {
    /**
     * Main method.
     */
    public static void main(String[] args) {
        try {
            PizzeriaProperties properties = PizzeriaPropertiesManager
                    .loadProperties(Pizzeria.class.getResourceAsStream("/properties.json"));
            Pizzeria pizzeria = new Pizzeria(properties.storageCapacity);
            for (int i = 0; i < properties.pizzaiolos; i++) {
                pizzeria.addPizzaiolo(properties.cookingTime[i], properties.maxCountAtOnce[i]);
            }
            for (int i = 0; i < properties.deliverymen; i++) {
                pizzeria.addDeliveryman(properties.trunkCapacity[i]);
            }
            for (int i = 0; i < 11; i++) {
                pizzeria.getStorage().placeOrder(new Order(i + 20, i));
            }

            pizzeria.start();
        } catch (IOException thrown) {
            thrown.printStackTrace();
        }
    }
}
