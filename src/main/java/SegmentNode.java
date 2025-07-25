public class SegmentNode<T> {

    private T sum;
    private SegmentNode<T> left;
    private SegmentNode<T> right;

    public SegmentNode() {
        this.sum = null;
        this.left = null;
        this.right = null;
    }

    public SegmentNode(T value) {
        this.sum = value;
        this.left = null;
        this.right = null;
    }

    public T getSum() {
        return sum;
    }

    public void setSum(T sum) {
        this.sum = sum;
    }

    public SegmentNode<T> getLeft() {
        return left;
    }

    public void setLeft(SegmentNode<T> left) {
        this.left = left;
    }

    public SegmentNode<T> getRight() {
        return right;
    }

    public void setRight(SegmentNode<T> right) {
        this.right = right;
    }
}
