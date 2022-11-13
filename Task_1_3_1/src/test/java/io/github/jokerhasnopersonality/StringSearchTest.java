package io.github.jokerhasnopersonality;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * Tests to check StringSearch realization.
 */
public class StringSearchTest {
    @Test
    public void test0() {
        String path = "/Test1.txt";
        Assertions.assertThrows(NullPointerException.class, () -> StringSearch.search(null,
                StringSearch.class.getResourceAsStream(path)));
        Assertions.assertThrows(NullPointerException.class,
                () -> StringSearch.search("dreams", null));
    }

    @Test
    public void test1() throws IOException {
        String path = "/Test1.txt";
        InputStream in = StringSearch.class.getResourceAsStream(path);
        List<Integer> indexes = StringSearch.search("dreams", in);
        List<Integer> test = List.of(6, 265);
        Assertions.assertEquals(2, indexes.size());
        Assertions.assertEquals(test, indexes);
        in.close();
        InputStream in2 = in;
        Assertions.assertThrows(IOException.class, () -> StringSearch.search("dreams", in2));

        in = StringSearch.class.getResourceAsStream(path);
        indexes = StringSearch.search("to", in);
        test = List.of(39, 148, 177, 214, 245, 298);
        assert indexes != null;
        Assertions.assertEquals(6, indexes.size());
        Assertions.assertEquals(test, indexes);
        in.close();
    }

    @Test
    public void test2() throws IOException {
        String path = "/Test2.txt";
        InputStream in = StringSearch.class.getResourceAsStream(path);
        List<Integer> indexes = StringSearch.search("abc", in);
        List<Integer> test = List.of(0, 3, 6, 9, 12, 40, 52);
        Assertions.assertEquals(7, indexes.size());
        Assertions.assertEquals(test, indexes);
        in.close();

        in = StringSearch.class.getResourceAsStream(path);
        indexes = StringSearch.search("abca", in);
        test = List.of(0, 3, 6, 9);
        assert indexes != null;
        Assertions.assertEquals(4, indexes.size());
        Assertions.assertEquals(test, indexes);
        in.close();
    }
}