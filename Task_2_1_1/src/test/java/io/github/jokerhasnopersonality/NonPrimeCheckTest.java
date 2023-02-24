package io.github.jokerhasnopersonality;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class NonPrimeCheckTest {
    @BeforeAll
    public static void setupHeadlessMode() {
        System.setProperty("java.awt.headless", "false");
    }
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
    public void speedTest() throws IOException {
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

        long start;
        long end;
        Dataset dataset = new Dataset(new String[] {
                "Sequential", "ParallelStream", "Threads(2)",
                "Threads(3)", "Threads(4)", "Threads(5)", "Threads(6)"});
        for (int i = 10; i < 1000000; i *= 10) {
            int[] testSequential = Arrays.copyOfRange(test, 0, i);
            start = System.currentTimeMillis();
            Assertions.assertFalse(new SequentialNonPrimeCheck(testSequential).checkArray());
            end = System.currentTimeMillis();
            dataset.add(0, i, end - start);

            start = System.currentTimeMillis();
            Assertions.assertFalse(new ParallelStreamNonPrimeCheck(testSequential).checkArray());
            end = System.currentTimeMillis();
            dataset.add(1, i, end - start);

            for (int j = 2; j < 7; j++) {
                start = System.currentTimeMillis();
                Assertions.assertFalse(new ParallelNonPrimeCheck(testSequential, j).checkArray());
                end = System.currentTimeMillis();
                dataset.add(j, i, end - start);
            }
        }

        XYSplineRenderer renderer = new XYSplineRenderer();
        renderer.setPrecision(6);
        for (int i = 0; i < 7; i++) {
            renderer.setSeriesShapesVisible(i, false);
        }

        final Diagram demo = new Diagram(dataset.createDataset(), renderer);
        demo.pack();
        OutputStream out = new FileOutputStream("./src/main/resources/Diagram.png");
        ChartUtils.writeChartAsPNG(out, demo.getChart(), demo.getWidth(), demo.getHeight());
    }
}