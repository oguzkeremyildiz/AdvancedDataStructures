package Heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class FibonacciHeap<T> {

    private final Comparator<T> comparator;
    private FibonacciNode<T> root;
    private int size;
    private final HashMap<T, FibonacciNode<T>> nodeMap;

    public FibonacciHeap(Comparator<T> comparator) {
        this.comparator = comparator;
        this.nodeMap = new HashMap<>();
        this.root = null;
        this.size = 0;
    }

    public FibonacciNode<T> get() {
        return this.root;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public int getSize() {
        return this.size;
    }

    public FibonacciNode<T> insert(T value) {
        FibonacciNode<T> node = new FibonacciNode<>(value);
        nodeMap.put(value, node);
        if (root == null) {
            root = node;
        } else {
            addNodeToRootList(node);
            if (comparator.compare(node.getValue(), root.getValue()) > 0) {
                root = node;
            }
        }
        size++;
        return node;
    }

    public void merge(FibonacciHeap<T> otherHeap) {
        if (otherHeap == null || otherHeap.root == null) {
            return;
        }
        if (this.root == null) {
            this.root = otherHeap.root;
            this.size = otherHeap.size;
        } else {
            FibonacciNode<T> thisRootRight = this.root.getRightNode();
            FibonacciNode<T> otherRootLeft = otherHeap.root.getLeftNode();
            this.root.setRight(otherHeap.root);
            otherHeap.root.setLeft(this.root);
            thisRootRight.setLeft(otherRootLeft);
            otherRootLeft.setRight(thisRootRight);
            if (comparator.compare(otherHeap.root.getValue(), this.root.getValue()) > 0) {
                this.root = otherHeap.root;
            }
            this.size += otherHeap.size;
        }
        otherHeap.root = null;
        otherHeap.size = 0;
    }

    public FibonacciNode<T> remove() {
        FibonacciNode<T> z = root;
        if (z != null) {
            nodeMap.remove(z.getValue());
            FibonacciNode<T> x = z.getChild();
            if (x != null) {
                List<FibonacciNode<T>> children = new ArrayList<>();
                FibonacciNode<T> curr = x;
                do {
                    children.add(curr);
                    curr = curr.getRightNode();
                } while (curr != x);
                for (FibonacciNode<T> child : children) {
                    addNodeToRootList(child);
                    child.setParent(null);
                }
            }
            removeNodeFromList(z);
            if (z == z.getRightNode()) {
                root = null;
            } else {
                root = z.getRightNode();
                consolidate();
            }
            size--;
        }
        return z;
    }

    private void consolidate() {
        int maxDegree = (int) Math.floor(Math.log(size) / Math.log((1.0 + Math.sqrt(5.0)) / 2.0)) + 1;
        ArrayList<FibonacciNode<T>> A = new ArrayList<>(maxDegree + 1);
        for (int i = 0; i <= maxDegree; i++) {
            A.add(null);
        }
        ArrayList<FibonacciNode<T>> rootNodes = new ArrayList<>();
        FibonacciNode<T> curr = root;
        if (curr != null) {
            do {
                rootNodes.add(curr);
                curr = curr.getRightNode();
            } while (curr != root);
        }
        for (FibonacciNode<T> w : rootNodes) {
            FibonacciNode<T> x = w;
            int d = x.getDegree();
            while (d >= A.size()) {
                A.add(null);
            }
            while (A.get(d) != null) {
                FibonacciNode<T> y = A.get(d);
                if (comparator.compare(x.getValue(), y.getValue()) < 0) {
                    FibonacciNode<T> temp = x;
                    x = y;
                    y = temp;
                }
                link(y, x);
                A.set(d, null);
                d++;
                while (d >= A.size()) {
                    A.add(null);
                }
            }
            A.set(d, x);
        }
        root = null;
        for (FibonacciNode<T> node : A) {
            if (node != null) {
                if (root == null) {
                    root = node;
                    root.setLeft(root);
                    root.setRight(root);
                } else {
                    addNodeToRootList(node);
                    if (comparator.compare(node.getValue(), root.getValue()) > 0) {
                        root = node;
                    }
                }
            }
        }
    }

    private void link(FibonacciNode<T> y, FibonacciNode<T> x) {
        removeNodeFromList(y);
        y.setParent(x);
        if (x.getChild() == null) {
            x.setChild(y);
            y.setLeft(y);
            y.setRight(y);
        } else {
            FibonacciNode<T> child = x.getChild();
            y.setLeft(child);
            y.setRight(child.getRightNode());
            child.getRightNode().setLeft(y);
            child.setRight(y);
        }
        x.setDegree(x.getDegree() + 1);
        y.setMark(false);
    }

    public void decreaseKey(T oldElement, T k) {
        FibonacciNode<T> x = nodeMap.get(oldElement);
        if (comparator.compare(k, x.getValue()) < 0) {
            throw new IllegalArgumentException("New key is greater than current key");
        }
        nodeMap.remove(oldElement);
        x.setValue(k);
        nodeMap.put(k, x);
        FibonacciNode<T> y = x.getParent();
        if (y != null && comparator.compare(x.getValue(), y.getValue()) > 0) {
            cut(x, y);
            cascadingCut(y);
        }
        if (comparator.compare(x.getValue(), root.getValue()) > 0) {
            root = x;
        }
    }

    private void cut(FibonacciNode<T> x, FibonacciNode<T> y) {
        if (x == x.getRightNode()) {
            y.setChild(null);
        } else {
            removeNodeFromList(x);
            if (y.getChild() == x) {
                y.setChild(x.getRightNode());
            }
        }
        y.setDegree(y.getDegree() - 1);
        addNodeToRootList(x);
        x.setParent(null);
        x.setMark(false);
    }

    private void cascadingCut(FibonacciNode<T> y) {
        FibonacciNode<T> z = y.getParent();
        if (z != null) {
            if (!y.isMark()) {
                y.setMark(true);
            } else {
                cut(y, z);
                cascadingCut(z);
            }
        }
    }

    private void addNodeToRootList(FibonacciNode<T> node) {
        FibonacciNode<T> rootRight = root.getRightNode();
        node.setLeft(root);
        node.setRight(rootRight);
        root.setRight(node);
        rootRight.setLeft(node);
    }

    private void removeNodeFromList(FibonacciNode<T> node) {
        FibonacciNode<T> left = node.getLeftNode();
        FibonacciNode<T> right = node.getRightNode();
        left.setRight(right);
        right.setLeft(left);
    }
}