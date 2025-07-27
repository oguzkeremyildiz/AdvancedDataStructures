import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class PairingHeapTest {

    @Test
    public void pairingHeapTest() {
        PairingHeap<Integer> heap1 = new PairingHeap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2) {
                    return -1;
                } else if (o1.equals(o2)) {
                    return 0;
                }
                return 1;
            }
        });
        heap1.insert(5);
        heap1.insert(2);
        heap1.insert(6);
        PairingHeap<Integer> heap2 = new PairingHeap<>(new  Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2) {
                    return -1;
                } else if (o1.equals(o2)) {
                    return 0;
                }
                return 1;
            }
        });
        heap2.insert(1);
        heap2.insert(3);
        heap2.merge(heap1.get());
        heap2.insert(4);
        heap2.insert(7);
        heap2.insert(8);
        assertEquals(1, heap2.get().getValue(), 0.01);
        heap2.remove();
        assertEquals(2, heap2.get().getValue(), 0.01);
        heap2.remove();
        assertEquals(3, heap2.get().getValue(), 0.01);
        heap2.insert(1);
        assertEquals(1, heap2.get().getValue(), 0.01);
        heap2.remove();
        heap2.remove();
        assertEquals(4, heap2.get().getValue(), 0.01);
    }
}
