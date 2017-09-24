package com.rain.learn.algorithm.sort;

public class MergeSort {

    public void ascendSort(int[] a) {
        int length;
        if (a == null || (length = a.length) < 2) {
            return;
        }
        int[] b = new int[length];
        sort(a, b, 0, length - 1);
    }

    private void sort(int[] from, int[] to, int left, int right) {
        if (right <= left) {
            return;
        }
        int half = (right + left) >> 1;
        sort(from, to, left, half);
        sort(from, to, half + 1, right);
        adjust(from, left, right, half, to);
    }

    private void adjust(int[] a, int left, int right, int segment, int[] auxiliary) {
        int i = left;
        int j = segment + 1;
        int k = left;
        while (i <= segment && j <= right) {
            if (a[i] < a[j]) {
                auxiliary[k++] = a[i++];
            } else {
                auxiliary[k++] = a[j++];
            }
        }
        if (i <= segment) {
            int ii = segment;
            while (ii >= i) {
                a[right - segment + ii] = a[ii--];
            }
        }
        k--;
        while (k >= left) {
            a[k] = auxiliary[k--];
        }
    }
}
