import Tree.LinkCutTree;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinkCutTreeTest {

    @Test
    public void linkCutTreeTest() {
        LinkCutTree<Integer> tree = new LinkCutTree<>();
        tree.link(1, 2);
        tree.link(1, 5);
        tree.link(1, 6);
        tree.link(2, 3);
        tree.link(3, 4);
        tree.link(6, 7);
        tree.link(7, 8);
        tree.link(7, 10);
        tree.link(8, 9);
        tree.link(11, 12);
        tree.link(12, 13);
        tree.link(11, 14);
        tree.link(10, 15);
        tree.link(10, 16);
        tree.link(10, 17);
        tree.link(16, 18);
        tree.link(16, 19);
        tree.link(7, 20);
        tree.link(20, 21);
        tree.link(20, 22);
        tree.link(20, 23);
        tree.link(21, 24);
        assertEquals(1, tree.lowestCommonAncestor(3, 7).getValue(), 0.01);
        assertEquals(7, tree.lowestCommonAncestor(9, 10).getValue(), 0.01);
        assertEquals(7, tree.lowestCommonAncestor(24, 18).getValue(), 0.01);
        assertEquals(11, tree.lowestCommonAncestor(13, 14).getValue(), 0.01);
        assertEquals(7, tree.lowestCommonAncestor(9, 19).getValue(), 0.01);
        assertEquals(8, tree.lowestCommonAncestor(9, 8).getValue(), 0.01);
        tree.cut(20);
        assertNull(tree.lowestCommonAncestor(24, 18));
        tree.link(7, 20);
        assertEquals(7, tree.lowestCommonAncestor(24, 18).getValue(), 0.01);
        assertNull(tree.lowestCommonAncestor(21, 11));
        assertEquals(1, tree.lowestCommonAncestor(4, 18).getValue(), 0.01);
        tree.cut(7);
        assertNull(tree.lowestCommonAncestor(4, 18));
        tree.link(6, 7);
        assertEquals(1, tree.lowestCommonAncestor(4, 18).getValue(), 0.01);
    }
}
