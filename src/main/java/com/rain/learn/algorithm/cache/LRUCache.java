package com.rain.learn.algorithm.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = 6645880050283931233L;

	private static final int DEFAULT_SIZE = 1000;

	private static final float DEFUALT_LOAD_FACTOR = 0.75f;

	private final int size;

	public LRUCache() {
		super((int) Math.ceil(DEFAULT_SIZE / DEFUALT_LOAD_FACTOR) + 1, 0.75f,
				true);
		size = DEFAULT_SIZE;
	}

	public LRUCache(int capacity) {
		super((int) Math.ceil(capacity / DEFUALT_LOAD_FACTOR) + 1, 0.75f, true);
		size = capacity;
	}

	public LRUCache(int capacity, float loadFactor) {
		super((int) Math.ceil(capacity / loadFactor) + 1, loadFactor, true);
		size = capacity;
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return size() > size;
	}
}
