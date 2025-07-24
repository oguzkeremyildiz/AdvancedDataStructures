import org.junit.Test;

import static org.junit.Assert.*;

public class VanEmdeBoasTreeTest {

    @Test
    public void vanEmdeBoasTreeTest() throws BoundIsNotValidException {
        int bound = 16;
        VanEmdeBoasTree tree = new VanEmdeBoasTree(bound);
        for (int i = 0; i < bound; i++) {
            tree.insert(i);
            assertTrue(tree.contains(i));
        }
        for (int i = 0; i < bound; i++) {
            tree.remove(bound - 1 - i);
            assertFalse(tree.contains(bound - 1 - i));
        }
        tree.insert(3);
        tree.insert(2);
        tree.insert(7);
        tree.insert(12);
        assertEquals(3, tree.predecessor(7));
        assertEquals(2, tree.predecessor(3));
        assertEquals(7, tree.predecessor(12));
        assertEquals(12, tree.predecessor(15));
        assertEquals(3, tree.successor(2));
        assertEquals(2, tree.successor(1));
        assertEquals(7, tree.successor(4));
        assertEquals(12, tree.successor(8));
    }
}
