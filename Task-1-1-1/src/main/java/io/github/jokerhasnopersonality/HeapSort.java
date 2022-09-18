package io.github.jokerhasnopersonality;

import java.util.Arrays;

/**
 * HeapSort wrapper.
 */
public class HeapSort {
    /**
     * Internal function to switch values a, b in given array.
     * */
    private static void swap(int[] arr, int a, int b) {
        int t = arr[a];
        arr[a] = arr[b];
        arr[b] = t;
    }

    /**
     * The classic pyramid sorting algorithm (HeapSort) sorts the given array.
     *
     * @param arr array that is to be sorted by the algorithm
     */
    public static void heapsort(int[] arr) {
        int l = arr.length;
        if (l == 0) {
            return;
        }
        int cnt = 0;
        for (int i = 0; i < l; i++) {
            siftup(arr, cnt);
            cnt++;
        }
        for (int i = 0; i < l; i++) {
            swap(arr, 0, cnt - 1);
            cnt--;
            siftdown(arr, cnt, 0);
        }
        l = l - 1;
        for (int i = 0; i <= l / 2; i++) {
            swap(arr, i, l - i);
        }
    }

    /**
     * Internal function. Checks the heap after new element is added.
     * If parent element is greater than the element - switch values.
     */
    private static void siftup(int[] arr, int index) {
        int parent = (index - 1) / 2;
        if (arr[parent] > arr[index]) {
            swap(arr, index, parent);
            siftup(arr, parent);
        }
    }

    /**
     * Internal function. Checks the heap after the root is extracted.
     * If son element is less than the element - switch values.
     */
    private static void siftdown(int[] arr, int size, int index) {
        int son;
        if (index * 2 + 2 >= size) {
            if (index * 2 + 1 >= size) {
                //the end of heap
                return;
            } else {
                son = index * 2 + 1;
            }
        } else {
            son = arr[index * 2 + 1] < arr[index * 2 + 2] ? index * 2 + 1 : index * 2 + 2;
        }
        if (arr[index] > arr[son]) {
            swap(arr, index, son);
            siftdown(arr, size, son);
        }
    }
}
