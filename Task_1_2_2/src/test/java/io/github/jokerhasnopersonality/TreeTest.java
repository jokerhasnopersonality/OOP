package io.github.jokerhasnopersonality;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Tests to check HeapSort algorithm.
 */
public class TreeTest {
    @Test
    public void test0() {
        Tree<Integer> t = new Tree<>();
        Assertions.assertEquals(null, t.getParent());
        Assertions.assertThrows(NullPointerException.class, () -> t.value(null));
        Assertions.assertThrows(NullPointerException.class, () -> t.add(null));
        Assertions.assertThrows(NullPointerException.class, () -> t.add(null, 3));
        t.value(1);
        Tree<Integer> tt = t.add(2).add(3).add(4);
        Assertions.assertEquals(1, tt.getParent().getParent().getParent().getValue());
    }

    /*
                   1
                /  |  \
               3   6   7
              /    |   |  \
             4     9   8   12
                  /
                 10
     */
    public Tree<Integer> A;

    @BeforeEach
    public void init() {
        A = new Tree<>();
        A.value(1);
        Tree<Integer> B = A.add(3);
        B.add(4);
        Tree<Integer> C = A.add(6);
        C.add(9).add(10);
        Tree<Integer> D = A.add(7);
        A.add(D, 8);
        A.add(D, 12);
    }

    @Test
    public void testBFS() {
        ArrayList<Integer> test = new ArrayList<>();
        Integer[] l = {1, 3, 6, 7, 4, 9, 8, 12, 10};
        Collections.addAll(test, l);
        Assertions.assertEquals(test, A.treeList());
        Tree<Integer> B = A.getChildren().get(1);
        Iterator<Integer> iterator = B.iterator();
        Integer next = iterator.next();
        Assertions.assertEquals(6, next);
        next = iterator.next();
        Assertions.assertEquals(9, next);
    }

    @Test
    public void testDFS() {
        ArrayList<Integer> test = new ArrayList<>();
        Integer[] l = {1, 3, 4, 6, 9, 10, 7, 8, 12};
        Collections.addAll(test, l);
        A.search = Tree.Search.DFS;
        Assertions.assertEquals(test, A.treeList());
    }

    @Test
    public void simple_test() {
        Tree<Integer> B = A.getChildren().get(1);
        A.remove(B.getValue());
        Assertions.assertEquals(2, A.getChildren().size());
        ArrayList<Integer> test = new ArrayList<>();
        Integer[] l = {1, 3, 7, 4, 8, 12};
        Collections.addAll(test, l);
        Assertions.assertEquals(test, A.treeList());
    }

    @Test
    public void testConcurrentModificationException() {
        try {
            for (Object t : A) {
                A.getChildren().get(2).value(143);
            }
        } catch (ConcurrentModificationException thrown) {}
    }

    @Test
    public void testSearchType() {
        A.search = null;
        try {
            Iterator<Integer> iterator = A.iterator();
        } catch (IllegalStateException thrown) {}
    }
}