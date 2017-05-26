package com.rain.learn.algorithm.cache;

/**
 * 
 * @author rain
 *
 * @param <K>
 *            can't be null
 * @param <V>
 */
public class ConcurrentLRUCache<K, V> {

    private LRUCache<K, V>[] caches;

    private final int size;

    private final float loadFactor;

    private final int cacheSize;

    // must be power of 2
    private final int concurrencyLevel;

    private int shift;

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int DEFAULT_CONCURRENCY_LEVEL = 1 << 4;
    private static final int MAX_CONCURRENCY_LEVEL = 1 << 9;
    private static final int DEFAULT_SIZE = 10000;
    private static final int MIN_SIZE = 1000;

    public ConcurrentLRUCache(int capacity, float loadFactor, int concurrencyLevel) {
        this.size = capacity < MIN_SIZE ? MIN_SIZE : capacity;
        this.loadFactor = loadFactor <= 0 || loadFactor > 1 ? DEFAULT_LOAD_FACTOR : loadFactor;
        int number = leastPower2(concurrencyLevel);
        this.concurrencyLevel = number > MAX_CONCURRENCY_LEVEL ? MAX_CONCURRENCY_LEVEL : number;
        this.cacheSize = this.size / this.concurrencyLevel + 1;
        init();
    }

    public ConcurrentLRUCache(int capacity, float loadFactor) {
        this(capacity, loadFactor, DEFAULT_CONCURRENCY_LEVEL);
    }

    public ConcurrentLRUCache(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR, DEFAULT_CONCURRENCY_LEVEL);
    }

    public ConcurrentLRUCache() {
        this.size = DEFAULT_SIZE;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.concurrencyLevel = DEFAULT_CONCURRENCY_LEVEL;
        this.cacheSize = this.size / this.concurrencyLevel + 1;
        init();
    }

    private final int leastPower2(int number) {
        int n = number - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : n + 1;
    }

    @SuppressWarnings("unchecked")
    private final void init() {
        shift = Integer.numberOfTrailingZeros(leastPower2(cacheSize));
        caches = (LRUCache<K, V>[]) new LRUCache<?, ?>[concurrencyLevel];
        for (int i = 0; i < concurrencyLevel; i++) {
            caches[i] = new LRUCache<>(cacheSize, loadFactor);
        }
    }

    private final LRUCache<K, V> cacheAt(K key) {
        int k = key.hashCode();
        k = k ^ (k >>> 16) >>> shift;
        return caches[k & (concurrencyLevel - 1)];
    }

    public V put(K key, V value) {
        LRUCache<K, V> cache = cacheAt(key);
        synchronized (cache) {
            return cache.put(key, value);
        }
    }

    public V get(K key) {
        LRUCache<K, V> cache = cacheAt(key);
        synchronized (cache) {
            return cache.get(key);
        }
    }

    // this is just for test
    void printSize() {
        for (int i = 0; i < concurrencyLevel; i++) {
            synchronized (caches[i]) {
                System.out.println(i + ": " + caches[i].size());
            }
        }
    }
}
