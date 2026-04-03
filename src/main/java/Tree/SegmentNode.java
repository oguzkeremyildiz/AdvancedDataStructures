package Tree;

public class SegmentNode<T> extends TreeNode<T> {

    private T lazyValue;

    public SegmentNode(T value) {
        super(value);
        this.lazyValue = null;
    }

    public SegmentNode() {
        super(null);
        this.lazyValue = null;
    }

    public T getLazyValue() {
        return lazyValue;
    }

    public void setLazyValue(T lazyValue) {
        this.lazyValue = lazyValue;
    }
}
