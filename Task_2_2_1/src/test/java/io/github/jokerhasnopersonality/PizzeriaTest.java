package io.github.jokerhasnopersonality;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for Pizzeria class.
 */
class PizzeriaTest {
    @Test
    public void testLoader() throws IOException {
        PizzeriaProperties properties = PizzeriaPropertiesManager
                .loadProperties(Pizzeria.class.getResourceAsStream("/properties.json"));
        Assertions.assertEquals(5, properties.pizzaiolos);
        Assertions.assertEquals(6, properties.deliverymen);
        Assertions.assertEquals(10, properties.storageCapacity);
        Assertions.assertEquals(14, properties.cookingTime[3]);
        Assertions.assertEquals(6, properties.trunkCapacity[2]);

        properties.storageCapacity = 12;
        PizzeriaPropertiesManager.saveProperties("save.json", properties);
        properties = PizzeriaPropertiesManager.loadProperties(
                new FileInputStream("save.json"));
        Assertions.assertEquals(12, properties.storageCapacity);
    }

    @Test
    public void testWithJson() throws IOException {
        PizzeriaProperties properties = PizzeriaPropertiesManager
                .loadProperties(Pizzeria.class.getResourceAsStream("/properties.json"));
        Pizzeria pizzeria = new Pizzeria(properties.storageCapacity);
        for (int i = 0; i < properties.pizzaiolos; i++) {
            pizzeria.addPizzaiolo(properties.cookingTime[i], properties.maxCountAtOnce[i]);
        }
        Assertions.assertThrows(IllegalStateException.class, pizzeria::start);
        for (int i = 0; i < properties.deliverymen; i++) {
            pizzeria.addDeliveryman(properties.trunkCapacity[i]);
        }

        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        for (int i = 0; i < 11; i++) {
            pizzeria.getStorage().placeOrder(new Order(i+20, i));
        }

        pizzeria.start();

        InputStream in = new FileInputStream("output.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        List<Object> result = List.of(reader.lines()
                .filter(x -> x.contains("DELIVERED")).toArray());
        Assertions.assertEquals(11, result.size());
        in.close();
        reader.close();
    }

    @Test
    public void testWorkers() throws IOException {
        Storage storage = new Storage(12);
        Pizzaiolo[] pizzaiolos = new Pizzaiolo[3];
        pizzaiolos[0] = new Pizzaiolo(11, 2, storage);
        pizzaiolos[1] = new Pizzaiolo(10, 3, storage);
        pizzaiolos[2] = new Pizzaiolo(14, 4, storage);

        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        for (int i = 0; i < 12; i++) {
            storage.placeOrder(new Order(1, i));
        }
        Thread[] bakingThreads = new Thread[pizzaiolos.length];
        for (int i = 0; i < bakingThreads.length; i++) {
            bakingThreads[i] = new Thread(pizzaiolos[i]);
            bakingThreads[i].start();
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

        InputStream in = new FileInputStream("output.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        List<Object> result = List.of(reader.lines()
                .filter(x -> x.contains("READY")).toArray());
        Assertions.assertEquals(12, result.size());
        in.close();
        reader.close();

        Deliveryman[] deliverymen = new Deliveryman[2];
        deliverymen[0] = new Deliveryman(4, storage);
        deliverymen[1] = new Deliveryman(2, storage);
        Thread[] deliveryThreads = new Thread[2];
        for (int i = 0; i < 2; i++) {
            deliveryThreads[i] = new Thread(deliverymen[i]);
            deliveryThreads[i].start();
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

        in = new FileInputStream("output.txt");
        reader = new BufferedReader(new InputStreamReader(in));
        result = List.of(reader.lines()
                .filter(x -> x.contains("DELIVERED")).toArray());
        Assertions.assertEquals(12, result.size());
        in.close();
        reader.close();
    }
}