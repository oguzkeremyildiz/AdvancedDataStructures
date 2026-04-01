import org.junit.Test;

import static org.junit.Assert.*;

public class BloomFilterTest {

    @Test
    public void bloomFilterTest() {
        BloomFilter<Integer> filter = new BloomFilter<>(10, 3);
        filter.add(1263);
        filter.add(253);
        filter.add(3);
        filter.add(4);
        assertTrue(filter.contains(1263));
        filter.remove(1263);
        assertFalse(filter.contains(1263));
        assertTrue(filter.contains(253));
        assertTrue(filter.contains(3));
        assertFalse(filter.contains(1264));
        assertFalse(filter.contains(254));
        assertTrue(filter.contains(4));
        filter.remove(3);
        filter.remove(4);
        assertFalse(filter.contains(3));
        assertFalse(filter.contains(4));
    }
}
