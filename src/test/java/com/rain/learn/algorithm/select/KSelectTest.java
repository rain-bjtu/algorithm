package com.rain.learn.algorithm.select;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.rain.learn.algorithm.exception.InvalidInputException;

public class KSelectTest {

	@Test
	public void testSelect() throws InvalidInputException {
		KSelect ks = new KSelect();
		Random r = new Random();
		int size = 500;
		int base = 1000;
		int kvalue;
		int kbase;
		for (int asize = 1; asize < size; asize++) {
			for (int k = 1; k <= asize; k++) {
				int[] a = new int[asize];
				kvalue = base + asize + 1 - k;
				int i = 0;
				while (i < k - 1) {
					a[i] = r.nextInt(k) + kvalue;
					i++;
				}
				kbase = asize + 2 - k;
				while (i < asize - 1) {
					a[i] = r.nextInt(kbase) + base;
					i++;
				}
				a[i] = kvalue;
				disturb(a);
				Assert.assertEquals(kvalue, ks.select(a, k));
			}
		}
	}

	private void disturb(int[] a) {
		if (a == null || a.length < 3) {
			return;
		}
		Random r = new Random(90000);
		int length = a.length;
		int times = length / 3;
		int temp;
		int m, n;
		for (int i = 0; i < times; i++) {
			m = r.nextInt(length);
			n = r.nextInt(length);
			temp = a[m];
			a[m] = a[n];
			a[n] = temp;
		}
	}

	@Test
	public void testSelectBasic() throws InvalidInputException {
		KSelect ks = new KSelect();
		int[] a = new int[] { 10, 5, 9, 20, 5, 9, 1, 30 };
		Assert.assertEquals(9, ks.select(a, 5));
	}
}
