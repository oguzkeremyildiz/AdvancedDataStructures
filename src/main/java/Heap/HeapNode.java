package Heap;

import General.Node;

public class HeapNode<T> extends Node<T> {

    private final T value;

    public HeapNode(T value) {
        super();
        this.value = value;
    }

    public void add(HeapNode<T> heap) {
        if (left != null) {
            heap.setRight(this.left);
        }
        this.left = heap;
    }

    public T getValue() {
        return value;
    }
}
