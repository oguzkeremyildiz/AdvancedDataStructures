public class KDimensionalNode<T> {

    private final T[] point;
    private KDimensionalNode<T> left;
    private KDimensionalNode<T> right;

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

    public KDimensionalNode<T> getLeft() {
        return left;
    }

    public void setLeft(KDimensionalNode<T> left) {
        this.left = left;
    }

    public KDimensionalNode<T> getRight() {
        return right;
    }

    public void setRight(KDimensionalNode<T> right) {
        this.right = right;
    }
}
