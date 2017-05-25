package com.rain.learn.algorithm.select;

import com.rain.learn.algorithm.basic.Basic;
import com.rain.learn.algorithm.exception.InvalidInputException;

public class KSelect {

	// the kth max value of a[]
	public int select(int[] a, int k) throws InvalidInputException {
		if (a == null || a.length < 1 || k > a.length || k < 1) {
			throw new InvalidInputException();
		}
		return select(a, 0, a.length - 1, k - 1);
	}

	private int select(int[] a, int left, int right, int k) {
		if (left == right) {
			return a[left];
		}
		int pivot = a[left];
		int i = left + 1;
		int j = right;
		while (i <= j) {
			if (a[i] > pivot) {
				i++;
			} else if (a[j] <= pivot) {
				j--;
			} else {
				Basic.swap(a, i, j);
				i++;
			}
		}
		if (k == j) {
			return pivot;
		}
		Basic.swap(a, left, j);
		if (k < j) {
			return select(a, left, j - 1, k);
		} else {
			return select(a, j + 1, right, k);
		}
	}
}
