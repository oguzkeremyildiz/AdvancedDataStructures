import java.util.HashMap;

public class VanEmdeBoasNode {

    private final int bound;
    private int minimum;
    private int maximum;
    private final VanEmdeBoasNode summary;
    private final HashMap<Integer, VanEmdeBoasNode> children;

    public VanEmdeBoasNode(int bound) {
        this.bound = bound;
        this.minimum = -1;
        this.maximum = -1;
        if (bound <= 2) {
            this.summary = null;
            this.children = null;
        } else {
            this.summary = new VanEmdeBoasNode((int) Math.ceil(Math.sqrt(bound)));
            this.children = new HashMap<>();
        }
    }

    public int getBound() {
        return bound;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public VanEmdeBoasNode getSummary() {
        return summary;
    }

    public VanEmdeBoasNode getChildren(int index) {
        return children.get(index);
    }

    public void addChild(int index) {
        children.put(index, new VanEmdeBoasNode((int) Math.ceil(Math.sqrt(bound))));
    }

    public void removeChild(int index) {
        children.remove(index);
    }

    public int getIndexInCurrentNode(int value) {
        return value / ((int) Math.ceil(Math.sqrt(bound)));
    }

    public int getIndexInChildNode(int value) {
        return value % ((int) Math.ceil(Math.sqrt(bound)));
    }

    public int getValue(int x, int y) {
        return x * ((int) Math.ceil(Math.sqrt(bound))) + y;
    }
}
