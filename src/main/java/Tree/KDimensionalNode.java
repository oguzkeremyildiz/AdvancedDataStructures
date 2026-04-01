package Tree;

import General.Node;

public class KDimensionalNode<T> extends Node<T> {

    private final T[] point;

    public KDimensionalNode(T[] point) {
        this.point = point;
        left = right = null;
    }

    public T[] getPoints() {
        return point;
    }

    public T getDimension(int dimension) {
        return point[dimension];
    }
}
