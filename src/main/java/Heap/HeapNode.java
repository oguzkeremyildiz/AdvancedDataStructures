package Heap;

import Tree.TreeNode;

public class HeapNode<T> extends TreeNode<T> {

    public HeapNode(T value) {
        super(value);
    }

    public void add(HeapNode<T> heap) {
        if (left != null) {
            heap.setRight(this.left);
        }
        this.left = heap;
    }
}
