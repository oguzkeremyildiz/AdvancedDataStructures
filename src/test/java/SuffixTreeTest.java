import Tree.SuffixTree;
import org.junit.Test;

import static org.junit.Assert.*;

public class SuffixTreeTest {

    @Test
    public void suffixTreeTest() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < Math.pow(10, 5) / 13; i++) {
             str.append("bananxbananab");
        }
        SuffixTree tree = new SuffixTree(str.toString());
        for (int i = 0; i < 25; i++) {
            for (int j = i + 1; j < 26; j++) {
                assertTrue(tree.search("bananxbananabbananxbananab".substring(0, i + 1)));
                assertTrue(tree.search("bananxbananabbananxbananab".substring(i + 1, j)));
                assertTrue(tree.search("bananxbananabbananxbananab".substring(j)));
            }
        }
        assertEquals(str.substring(0, str.length() - 13), tree.findLongestRepeatedSubstring());
        assertFalse(tree.search("bananxbananabka"));
        assertFalse(tree.search("bak"));
        assertTrue(tree.search("anxbananabbana"));
        assertTrue(tree.search("abba"));
        assertFalse(tree.search("xbannana"));
        tree = new SuffixTree("dklhtişhldfzkhdhşdlkzhzdfkşlhkdfşlkhtişhldfzkhdhşdlkzhzdfkfşdzlzhfilbdrzeklbysbyeşbsyerşkberyklş");
        assertEquals("htişhldfzkhdhşdlkzhzdfk", tree.findLongestRepeatedSubstring());
        assertTrue(tree.search("dlkzhzdfkfşdzlzhfilbdrze"));
        assertFalse(tree.search("hşdlkzhzdfkfodzlzhfilbdrzekl"));
    }
}
