public class HeapNode<T> {

    private HeapNode<T> leftChild;
    private HeapNode<T> next;
    private T value;

    public HeapNode(T value) {
        this.value = value;
        this.leftChild = null;
        this.next = null;
    }

    public HeapNode<T> getLeftChild() {
        return leftChild;
    }

    public HeapNode<T> getNext() {
        return next;
    }

    public void add(HeapNode<T> heap) {
        if (leftChild != null) {
            heap.setNext(this.leftChild);
        }
        this.leftChild = heap;
    }

    public void setNext(HeapNode<T> heap) {
        this.next = heap;
    }

    public T getValue() {
        return value;
    }
}
