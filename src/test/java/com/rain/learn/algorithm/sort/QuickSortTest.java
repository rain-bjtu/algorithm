package com.rain.learn.algorithm.sort;

import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class QuickSortTest {

	@Test
	public void testAscendSort() {
		Random r = new Random();
		QuickSort q = new QuickSort();
		for (int times = 0; times < 1000; times++) {
			for (int i = 100; i > 0; i--) {
				int[] a = new int[i];
				int[] b = new int[i];
				for (int j = 0; j < i; j++) {
					a[j] = r.nextInt(90);
					b[j] = a[j];
				}
				q.ascendSort(a, 0, i - 1);
				Arrays.sort(b);
				Assert.assertArrayEquals(a, b);
			}
		}
	}

	@Test
	public void testIterativeAscendSort() {
		Random r = new Random();
		QuickSort q = new QuickSort();
		for (int times = 0; times < 1000; times++) {
			for (int i = 100; i > 0; i--) {
				int[] a = new int[i];
				int[] b = new int[i];
				for (int j = 0; j < i; j++) {
					a[j] = r.nextInt(90);
					b[j] = a[j];
				}
				q.iterativeAscendSort(a, 0, i - 1);
				Arrays.sort(b);
				Assert.assertArrayEquals(a, b);
			}
		}
	}
}
