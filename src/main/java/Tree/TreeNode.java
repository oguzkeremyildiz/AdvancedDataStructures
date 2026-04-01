package Tree;

import General.Node;

public class TreeNode<T> extends Node<T> {

    private T value;

    public TreeNode() {
        super();
        this.value = null;
    }

    public TreeNode(T value) {
        super();
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
