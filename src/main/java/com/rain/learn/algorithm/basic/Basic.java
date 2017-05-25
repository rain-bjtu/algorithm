package com.rain.learn.algorithm.basic;

public class Basic {

	private Basic() {
	}

	// more efficient then xor method
	public static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	public static void swapByXor(int[] a, int i, int j) {
		a[i] = a[i] ^ a[j];
		a[j] = a[i] ^ a[j];
		a[i] = a[i] ^ a[j];
	}

	public static void validSwap(int[] a, int i, int j) {
		if (a == null || a.length < 2 || i < 0 || j < 0 || i >= a.length
				|| j >= a.length || i == j) {
			return;
		}
		swap(a, i, j);
	}
}
