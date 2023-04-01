package io.github.jokerhasnopersonality;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for Pizzeria class.
 */
class PizzeriaTest {
    @Test
    public void testWithJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PizzeriaProperties properties = mapper.readValue(
                Pizzeria.class.getResourceAsStream("/properties.json"),
                PizzeriaProperties.class);
        Assertions.assertEquals(5, properties.pizzaiolos);
        Assertions.assertEquals(6, properties.deliverymen);
        Assertions.assertEquals(10, properties.storageCapacity);
        Pizzeria pizzeria = new Pizzeria(properties.storageCapacity);
        for (int i = 0; i < properties.pizzaiolos; i++) {
            pizzeria.addPizzaiolo(properties.cookingTime[i], properties.maxCountAtOnce[i]);
        }
        for (int i = 0; i < properties.deliverymen; i++) {
            pizzeria.addDeliveryman(properties.trunkCapacity[i]);
        }
        pizzeria.getStorage().placeOrder(new Order(20));
        pizzeria.getStorage().placeOrder(new Order(25));
        pizzeria.getStorage().placeOrder(new Order(23));
        pizzeria.getStorage().placeOrder(new Order(26));
        pizzeria.getStorage().placeOrder(new Order(30));
        pizzeria.getStorage().placeOrder(new Order(40));
        pizzeria.getStorage().placeOrder(new Order(43));
        pizzeria.getStorage().placeOrder(new Order(15));
        pizzeria.getStorage().placeOrder(new Order(45));
        pizzeria.getStorage().placeOrder(new Order(34));
        pizzeria.getStorage().placeOrder(new Order(32));
        pizzeria.start();
    }
}