package io.github.jokerhasnopersonality;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NonPrimeCheckTest {
    @Test
    public void nullTest() {
        Assertions.assertThrows(NullPointerException.class,
                () -> new SequentialNonPrimeCheck(null).checkArray());

        Assertions.assertThrows(NullPointerException.class,
                () -> new ParallelStreamNonPrimeCheck(null));

        Assertions.assertThrows(NullPointerException.class,
                () -> new ParallelNonPrimeCheck(null, 2));
        Assertions.assertThrows(IllegalStateException.class,
                () -> new ParallelNonPrimeCheck(new int[] { 1, 2 }, 17));
    }

    @Test
    public void simpleTest() {
        int[] arr = { 11, 3, 5, 7, 10 };
        boolean result = new SequentialNonPrimeCheck(arr).checkArray();
        Assertions.assertTrue(result);

        arr = new int[] { 1, 3, 5, 7, 10 };
        result = new ParallelNonPrimeCheck(arr, 2).checkArray();
        Assertions.assertTrue(result);

        arr = new int[] { 6997901, 6997927, 6997937, 6997967,
                6998009, 6998029, 6998039, 6998051, 6998053 };
        result = new ParallelStreamNonPrimeCheck(arr).checkArray();
        Assertions.assertFalse(result);
    }

    @Test
    public void largeTest() {
        String path = "/numbers.txt";
        InputStream in = SequentialNonPrimeCheck.class.getResourceAsStream(path);
        Scanner scanner = new Scanner(new InputStreamReader(in, StandardCharsets.UTF_8));
        ArrayList<Integer> numbers = new ArrayList<>();
        while (scanner.hasNextInt()) {
            numbers.add(scanner.nextInt());
        }
        int[] test = new int[100000];
        for (int i = 0; i < 100000; i++) {
            test[i] = numbers.get((int) (Math.random() * 100000));
        }

        Assertions.assertFalse(new SequentialNonPrimeCheck(test).checkArray());
        Assertions.assertFalse(new ParallelStreamNonPrimeCheck(test).checkArray());

        for (int j = 2; j < 7; j++) {
            Assertions.assertFalse(new ParallelNonPrimeCheck(test, j).checkArray());
        }
    }
}