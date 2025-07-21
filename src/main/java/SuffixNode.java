import java.util.*;

public class SuffixNode {

    private final int indexInText;
    private int startChar;
    private int endChar;
    private SuffixNode parent;
    private final ArrayList<SuffixNode> children;
    private final HashMap<Character, Integer> characterMap;

    public SuffixNode(int indexInText, int currentChar, int endChar, SuffixNode parent) {
        this.indexInText = indexInText;
        this.startChar = currentChar;
        this.endChar = endChar;
        this.children = new ArrayList<>();
        this.characterMap = new HashMap<>();
        this.parent = parent;
    }

    public SuffixNode() {
        this.indexInText = -1;
        this.startChar = -1;
        this.endChar = -1;
        this.parent = null;
        this.children = new ArrayList<>();
        this.characterMap = new HashMap<>();
    }

    public SuffixNode getParent() {
        return parent;
    }

    public void setParent(SuffixNode parent) {
        this.parent = parent;
    }

    public int index() {
        return indexInText;
    }

    public int startChar() {
        return startChar;
    }

    public void setStartChar(int startChar) {
        this.startChar = startChar;
    }

    public int endChar() {
        return endChar;
    }

    public void setEndChar(int endChar) {
        this.endChar = endChar;
    }

    public void addChild(SuffixNode child, String text) {
        children.add(child);
        if (child.index() + child.startChar() == text.length()) {
            characterMap.put('$', characterMap.size());
        } else {
            characterMap.put(text.charAt(child.index() + child.startChar()), characterMap.size());
        }
    }

    public void setChild(char character, SuffixNode child, String text) {
        int index = characterMap.get(character);
        this.children.set(index, child);
        characterMap.remove(character);
        characterMap.put(text.charAt(child.index() + child.startChar()), index);
    }

    public SuffixNode getChild(char currentChar) {
        if (!characterMap.containsKey(currentChar)) {
            return null;
        }
        return getChild(characterMap.get(currentChar));
    }

    public SuffixNode getChild(int index) {
        return children.get(index);
    }

    public int childCount() {
        return children.size();
    }
}
