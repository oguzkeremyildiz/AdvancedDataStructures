import java.util.Comparator;

public class PairingHeap<T> {

    private final Comparator<T> comparator;
    private HeapNode<T> root;

    public PairingHeap(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
    }

    public void insert(T value) {
        this.root = merge(root, new HeapNode<>(value));
    }

    private HeapNode<T> merge(HeapNode<T> node1, HeapNode<T> node2) {
        if (node1 == null) {
            return node2;
        } else if (node2 == null) {
            return node1;
        }
        if (comparator.compare(node1.getValue(), node2.getValue()) > 0) {
            node1.add(node2);
            return node1;
        } else {
            node2.add(node1);
            return node2;
        }
    }

    public void merge(HeapNode<T> node2) {
        this.root = merge(root, node2);
    }

    private HeapNode<T> remove(HeapNode<T> node) {
        if (node == null || node.getNext() == null) {
            return node;
        }
        HeapNode<T> second = node.getNext();
        HeapNode<T> third = second.getNext();
        node.setNext(null);
        second.setNext(null);
        second = merge(node, second);
        second.setNext(third);
        return remove(second);
    }

    public void remove() {
        if (!isEmpty()) {
            this.root = remove(this.root.getLeftChild());
        }
    }

    public HeapNode<T> get() {
        return this.root;
    }

    public boolean isEmpty() {
        return this.root == null;
    }
}
