package ru.nsu.ktulegenova;

import java.util.Arrays;
public class HeapSort {
    static void Swap(int arr[], int a, int b) {
        int t = arr[a];
        arr[a] = arr[b];
        arr[b] = t;
    }

    static void HeapSort(int arr[]){
        int l = arr.length;
        int new_arr[] = new int[l];
        int cnt = 0;
        for(int i=0;i<l;i++){
            //Add(new_arr, arr[i]);
            new_arr[cnt] = arr[i];
            SiftUp(new_arr, cnt);
            cnt++;
        }
        int res;
        for (int i = 0; i < l; i++) {
            //arr[i] = ExtractMin(new_arr);
            res = new_arr[0];
            Swap(new_arr, 0, cnt - 1);
            cnt--;
            SiftDown(new_arr, cnt, 0);
            arr[i] = res;
        }
    }
    static void SiftUp(int arr[], int ind) {
        int p = (ind - 1) / 2;
        if (arr[p] > arr[ind]) {
            Swap(arr, ind, p);
            SiftUp(arr, p);
        }
    }
    static void SiftDown(int arr[], int size, int ind) {
        int s;
        if (ind * 2 + 2 >= size) {
            if (ind * 2 + 1 >= size)
                return;
            else
                s = ind * 2 + 1;
        }
        else
            s = arr[ind * 2 + 1] < arr[ind * 2 + 2] ? ind * 2 + 1 : ind * 2 + 2;
        if (arr[ind] > arr[s]) {
            Swap(arr, ind, s);
            SiftDown(arr, size, s);
        }
    }

    public static void main(String []args) {
        int arr[] = {1,-9,3,90,5};
        HeapSort(arr);
    }
}
