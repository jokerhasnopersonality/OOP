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
        pizzeria.addToQueue(new Order(2, 20));
        pizzeria.addToQueue(new Order(4, 25));
        pizzeria.addToQueue(new Order(6, 23));
        pizzeria.addToQueue(new Order(3, 26));
        pizzeria.addToQueue(new Order(2, 30));
        pizzeria.addToQueue(new Order(5, 40));
        pizzeria.addToQueue(new Order(5, 43));
        pizzeria.addToQueue(new Order(6, 15));
        pizzeria.addToQueue(new Order(4, 45));
        pizzeria.addToQueue(new Order(5, 34));
        pizzeria.addToQueue(new Order(3, 32));
        pizzeria.start();
    }
}