package General;

public class Node<T> {

    protected Node<T> left;
    protected Node<T> right;

    public Node() {
        this.left = null;
        this.right = null;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }
}
