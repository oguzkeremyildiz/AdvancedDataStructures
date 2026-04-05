package Heap;

import Tree.TreeNode;

public class FibonacciNode<T> extends TreeNode<T> {

    private FibonacciNode<T> parent;
    private FibonacciNode<T> child;
    private int degree;
    private boolean mark;

    public FibonacciNode(T value) {
        super(value);
        this.parent = null;
        this.child = null;
        this.degree = 0;
        this.mark = false;
        this.setLeft(this);
        this.setRight(this);
    }

    public FibonacciNode<T> getParent() {
        return parent;
    }

    public void setParent(FibonacciNode<T> parent) {
        this.parent = parent;
    }

    public FibonacciNode<T> getChild() {
        return child;
    }

    public void setChild(FibonacciNode<T> child) {
        this.child = child;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public FibonacciNode<T> getLeftNode() {
        return (FibonacciNode<T>) super.getLeft();
    }

    public FibonacciNode<T> getRightNode() {
        return (FibonacciNode<T>) super.getRight();
    }
}