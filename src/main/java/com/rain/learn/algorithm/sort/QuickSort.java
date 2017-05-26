package com.rain.learn.algorithm.sort;

import java.util.Deque;
import java.util.LinkedList;

import com.rain.learn.algorithm.basic.Basic;

public class QuickSort {

    public void ascendSort(int[] a, int left, int right) {
        if (right <= left) {
            return;
        }
        int j = partition(a, left, right);
        ascendSort(a, left, j - 1);
        ascendSort(a, j + 1, right);
    }

    public void iterativeAscendSort(int[] a, int left, int right) {
        if (right <= left) {
            return;
        }
        int j;
        Deque<Integer> stack = new LinkedList<>();
        stack.push(left);
        stack.push(right);
        while (!stack.isEmpty()) {
            right = stack.pop();
            left = stack.pop();
            j = partition(a, left, right);
            if (j - 1 > left) {
                stack.push(left);
                stack.push(j - 1);
            }
            if (right > j + 1) {
                stack.push(j + 1);
                stack.push(right);
            }
        }
    }

    private int partition(int[] a, int left, int right) {
        int pivot = a[left];
        int i = left + 1;
        int j = right;
        while (i <= j) {
            if (a[i] < pivot) {
                i++;
            } else if (a[j] >= pivot) {
                j--;
            } else {
                Basic.swap(a, i, j);
                i++;
            }
        }
        Basic.swap(a, left, j);
        return j;
    }

}
