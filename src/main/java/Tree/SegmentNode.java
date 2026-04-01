package Tree;

import General.Node;

public class SegmentNode<T> extends Node<T> {

    private T sum;

    public SegmentNode() {
        super();
        this.sum = null;
    }

    public SegmentNode(T value) {
        super();
        this.sum = value;
    }

    public T getSum() {
        return sum;
    }

    public void setSum(T sum) {
        this.sum = sum;
    }
}
