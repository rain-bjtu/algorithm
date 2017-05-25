package com.rain.learn.algorithm.cache;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Test;

public class ConcurrentLRUCacheTest {
	private int i;

	@Test
	public void testDistribution() {
		int size = 2000;
		int base = 1;
		int max = base + size;
		ConcurrentLRUCache<String, String> cache = new ConcurrentLRUCache<>(
				size);
		Executor e = Executors.newFixedThreadPool(20);
		CountDownLatch end = new CountDownLatch(size);
		for (i = 0; i < max; i++) {
			e.execute(new Runnable() {

				@Override
				public void run() {
					cache.put(String.valueOf(i), String.valueOf(i));
					System.out.println(String.valueOf(i));
					end.countDown();
				}
			});
			try {
				Thread.sleep(1);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		try {
			end.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		cache.printSize();
	}

	@Test
	public void testPutAndGet() {
		ConcurrentLRUCache<String, String> cache = new ConcurrentLRUCache<>();
		cache.put("1", "1");
		cache.put("2", "2");
		Assert.assertEquals("1", cache.get("1"));
		Assert.assertEquals("2", cache.get("2"));
	}

	@Test
	public void testHash() {
		final int n = 16;
		final int m = 128;
		int base = 1;
		int size = base + 16 * 128;
		int[][] info = new int[n][m];
		int outer;
		int inner;
		String key;
		for (int i = base; i < size; i++) {
			key = Integer.toString(i);
			outer = outerHash(key);
			inner = innerHash(key);
			info[outer & (n - 1)][inner & (m - 1)]++;
		}
		for (int i = 0; i < n; i++) {
			System.out.print(i + "--> ");
			for (int j = 0; j < m; j++) {
				System.out.print(j + ":" + info[i][j] + "; ");
			}
			System.out.println();
			System.out.print(i + "--> ");
			int ssize = 0;
			for (int j = 0; j < m; j++) {
				if (info[i][j] != 0) {
					ssize++;
				}
			}
			System.out.print(ssize + "--> ");
			for (int j = 0; j < m; j++) {
				if (info[i][j] != 0) {
					System.out.print(j + " ");
				}
			}
			System.out.println();
		}
	}

	private int innerHash(Object key) {
		int h;
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}

	private int outerHash(Object key) {
		int h;
		return (key == null) ? 0 : ((h = key.hashCode()) ^ (h >>> 16)) >>> 8;
	}
}
