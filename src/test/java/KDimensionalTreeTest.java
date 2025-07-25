import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class KDimensionalTreeTest {

    @Test
    public void kDimensionalTreeTest() {
        KDimensionalTree<Integer> tree = new KDimensionalTree<>(2, new DimensionComparator<Integer>() {
            @Override
            public int compare(Integer val1, Integer val2) {
                return val1.compareTo(val2);
            }

            @Override
            public KDimensionalNode<Integer> compare(KDimensionalNode<Integer> n1, KDimensionalNode<Integer> n2, KDimensionalNode<Integer> n3, int k) {
                if (n1 != null && (n2 == null || n1.getDimension(k) < n2.getDimension(k))) {
                    if (n3 == null || n1.getDimension(k) < n3.getDimension(k)) {
                        return n1;
                    } else {
                        return n3;
                    }
                }
                if (n2 != null && (n3 == null || n2.getDimension(k) < n3.getDimension(k))) {
                    if (n1 != null && n1.getDimension(k) < n2.getDimension(k)) {
                        return n1;
                    } else {
                        return n2;
                    }
                }
                if (n3 != null && (n1 == null || n3.getDimension(k) < n1.getDimension(k))) {
                    if (n2 != null && n3.getDimension(k) < n2.getDimension(k)) {
                        return n3;
                    } else {
                        return n2;
                    }
                }
                return n1;
            }
        });
        tree.insert(new Integer[]{3, 6});
        tree.insert(new Integer[]{2, 7});
        tree.insert(new Integer[]{17, 15});
        tree.insert(new Integer[]{6, 12});
        tree.insert(new Integer[]{13, 15});
        tree.insert(new Integer[]{9, 1});
        tree.insert(new Integer[]{10, 19});
        assertEquals(Arrays.toString(new Integer[]{9, 1}), Arrays.toString(tree.min(1).getPoints()));
        assertEquals(Arrays.toString(new Integer[]{2, 7}), Arrays.toString(tree.min(0).getPoints()));
        assertTrue(tree.contains(new Integer[]{6, 12}));
        assertFalse(tree.contains(new Integer[]{6, 11}));
        assertFalse(tree.contains(new Integer[]{13, 14}));
        assertTrue(tree.contains(new Integer[]{13, 15}));
        assertTrue(tree.contains(new Integer[]{3, 6}));
        tree.remove(new Integer[]{3, 6});
        assertFalse(tree.contains(new Integer[]{3, 6}));
        tree.remove(new Integer[]{13, 15});
        assertFalse(tree.contains(new Integer[]{13, 15}));
    }
}
