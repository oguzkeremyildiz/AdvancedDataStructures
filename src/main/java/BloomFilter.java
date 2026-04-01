public class BloomFilter<T> {

    private final int[] bloomFilter;
    private final int m;
    private final int k;

    public BloomFilter(int m, int k) {
        this.m = m;
        this.k = k;
        this.bloomFilter = new int[m];
    }

    public BloomFilter(int expectedElements, double falsePositiveRate) {
        this.m = (int) (-expectedElements * Math.log(falsePositiveRate) / Math.pow(Math.log(2), 2));
        this.k = (int) (((m + 0.0) / expectedElements) * Math.log(2));
        this.bloomFilter = new int[m];
    }

    public void add(T item) {
        long itemHash = item.hashCode();
        long h1 = fmix64(itemHash);
        long h2 = fmix64(h1 + 0x9e3779b9L);
        for (int i = 0; i < k; i++) {
            bloomFilter[getIndex(h1, h2, i)]++;
        }
    }

    public boolean contains(T item) {
        long itemHash = item.hashCode();
        long h1 = fmix64(itemHash);
        long h2 = fmix64(h1 + 0x9e3779b9L);
        for (int i = 0; i < k; i++) {
            if (bloomFilter[getIndex(h1, h2, i)] == 0) {
                return false;
            }
        }
        return true;
    }

    public void remove(T item) {
        if (!contains(item)) {
            return;
        }
        long itemHash = item.hashCode();
        long h1 = fmix64(itemHash);
        long h2 = fmix64(h1 + 0x9e3779b9L);
        for (int i = 0; i < k; i++) {
            bloomFilter[getIndex(h1, h2, i)]--;
        }
    }

    private int getIndex(long h1, long h2, int i) {
        return ((int) (((h1 + (long) i * h2) & Long.MAX_VALUE) % m));
    }

    private long fmix64(long k) {
        k ^= k >>> 33;
        k *= 0xff51afd7ed558ccdL;
        k ^= k >>> 33;
        k *= 0xc4ceb9fe1a85ec53L;
        k ^= k >>> 33;
        return k;
    }
}
