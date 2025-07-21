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
                assertEquals(true, tree.search("bananxbananabbananxbananab".substring(0, i + 1)));
                assertEquals(true, tree.search("bananxbananabbananxbananab".substring(i + 1, j)));
                assertEquals(true, tree.search("bananxbananabbananxbananab".substring(j)));
            }
        }
        assertEquals(false, tree.search("bananxbananabka"));
        assertEquals(false, tree.search("bak"));
        assertEquals(true, tree.search("anxbananabbana"));
        assertEquals(true, tree.search("abba"));
        assertEquals(false, tree.search("xbannana"));
    }
}
