package io.github.jokerhasnopersonality;

import java.util.Arrays;
public class HeapSort {
    private static void swap(int arr[], int a, int b) {
        int t = arr[a];
        arr[a] = arr[b];
        arr[b] = t;
    }
    public static void heapsort(int arr[]) {
        if (arr == null)
            return;
        int l = arr.length;
        int new_arr[] = new int[l];
        int cnt = 0;
        for(int i=0;i<l;i++){
            new_arr[cnt] = arr[i];
            siftup(new_arr, cnt);
            cnt++;
        }
        int res;
        for (int i = 0; i < l; i++) {
            res = new_arr[0];
            swap(new_arr, 0, cnt - 1);
            cnt--;
            siftdown(new_arr, cnt, 0);
            arr[i] = res;
        }
    }
    private static void siftup(int arr[], int index) {
        int p = (index - 1) / 2;
        if (arr[p] > arr[index]) {
            swap(arr, index, p);
            siftup(arr, p);
        }
    }
    private static void siftdown(int arr[], int size, int index) {
        int s;
        if (index * 2 + 2 >= size) {
            if (index * 2 + 1 >= size)
                return;
            else
                s = index * 2 + 1;
        }
        else
            s = arr[index * 2 + 1] < arr[index * 2 + 2] ? index * 2 + 1 : index * 2 + 2;
        if (arr[index] > arr[s]) {
            swap(arr, index, s);
            siftdown(arr, size, s);
        }
    }
}
