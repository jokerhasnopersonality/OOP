package io.github.jokerhasnopersonality;

import static io.github.jokerhasnopersonality.HeapSort.heapsort;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Tests to check HeapSort algorithm.
 */
public class HeapSortTest {
    @Test
    public void test0(){
        int arr[] = {};
        int test[] = {};
        HeapSort.heapsort(arr);
        assertArrayEquals(arr, test);
    }
    @Test
    public void test1(){
        int arr[] = {0};
        int test[] = {0};
        HeapSort.heapsort(arr);
        assertArrayEquals(arr, test);
    }
    @Test
    public void simple_test(){
        int arr[] = {5,3,-4,6,2};
        int test[] = {-4,2,3,5,6};
        HeapSort.heapsort(arr);
        assertArrayEquals(arr, test);
    }
    @Test
    public void sorted_test(){
        int arr[] = {-3,0,1,2};
        int test[] = {-3,0,1,2};
        HeapSort.heapsort(arr);
        assertArrayEquals(arr, test);
    }
    @Test
    public void part_sorted_test(){
        int arr[] = {7,8,9,2,4,5};
        int test[] = {2,4,5,7,8,9};
        HeapSort.heapsort(arr);
        assertArrayEquals(arr, test);
    }
    @Test
    public void equal_test(){
        int arr[] = {1,1,1,1,1};
        int test[] = {1,1,1,1,1};
        HeapSort.heapsort(arr);
        assertArrayEquals(arr, test);
    }
}