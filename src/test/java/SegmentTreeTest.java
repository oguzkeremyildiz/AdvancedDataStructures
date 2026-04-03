import Tree.SegmentTree;
import Tree.TypeInterface;
import org.junit.Test;

import static org.junit.Assert.*;

public class SegmentTreeTest {

    @Test
    public void segmentTreeTest() {
        SegmentTree<Double> tree = new SegmentTree<>(new TypeInterface<Double>() {
            @Override
            public Double add(Double o1, Double o2) {
                return o1 + o2;
            }

            @Override
            public Double zeroValue() {
                return 0.0;
            }

            @Override
            public Double multiply(int o1, Double o2) {
                return o1 * o2;
            }
        }, new Double[]{3.8, 4.7, 5.2, 2.9, 1.6, 3.0, 9.8});
        assertEquals(12.8, tree.getSumInRange(1, 3), 0.01);
        assertEquals(12.7, tree.getSumInRange(2, 5), 0.01);
        tree.addValue(4, 4.0);
        assertEquals(16.7, tree.getSumInRange(2, 5), 0.01);
        assertEquals(31.2, tree.getSumInRange(1, 6), 0.01);
        tree.addValue(6, -4.1);
        assertEquals(27.1, tree.getSumInRange(1, 6), 0.01);
        assertEquals(25.2, tree.getSumInRange(0, 5), 0.01);
        tree.addValue(1, 4, 3.0);
        assertEquals(37.2, tree.getSumInRange(0, 5), 0.01);
        assertEquals(8.2, tree.getSumInRange(2, 2), 0.01);
        tree.addValue(2, 5, -1.0);
        assertEquals(33.2, tree.getSumInRange(0, 5), 0.01);
        assertEquals(7.2, tree.getSumInRange(2, 2), 0.01);
    }
}
