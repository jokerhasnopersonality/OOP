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
                () -> StringSearch.search("logo", null));
    }

    @Test
    public void test1() throws IOException {
        String path = "/Test1.txt";
        InputStream in = StringSearch.class.getResourceAsStream(path);
        List<Integer> indexes = StringSearch.search("logo", in);
        List<Integer> test = List.of(352, 582, 935, 1164, 1308, 1375);
        Assertions.assertEquals(6, indexes.size());
        Assertions.assertEquals(test, indexes);
        in.close();
        InputStream in2 = in;
        Assertions.assertThrows(IOException.class, () -> StringSearch.search("dreams", in2));

        in = StringSearch.class.getResourceAsStream(path);
        indexes = StringSearch.search("eye", in);
        test = List.of(88, 165, 478, 1031, 1072);
        assert indexes != null;
        Assertions.assertEquals(5, indexes.size());
        Assertions.assertEquals(test, indexes);
        in.close();

        in = StringSearch.class.getResourceAsStream(path);
        Assertions.assertNull(StringSearch.search("", in));
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