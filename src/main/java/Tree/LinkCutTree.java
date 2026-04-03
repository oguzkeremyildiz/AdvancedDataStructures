package Tree;

import java.util.HashMap;

public class LinkCutTree<T> {

    private final HashMap<T, LinkCutNode<T>> map;

    public LinkCutTree() {
        this.map = new HashMap<>();
    }

    private void detachChild(LinkCutNode<T> node) {
        LinkCutNode<T> rightChild = (LinkCutNode<T>) node.getRight();
        if (rightChild != null) {
            rightChild.setPathParent(node);
            rightChild.setParent(null);
        }
    }

    private LinkCutNode<T> access(LinkCutNode<T> node) {
        node.splay();
        detachChild(node);
        node.setRight(null);
        LinkCutNode<T> par = node;
        while (node.getPathParent() != null) {
            par = node.getPathParent();
            par.splay();
            detachChild(par);
            par.setRight(node);
            node.setParent(par);
            node.setPathParent(null);
            node.splay();
        }
        return par;
    }

    private LinkCutNode<T> findRoot(LinkCutNode<T> node) {
        access(node);
        while (node.getLeft() != null) {
            node = (LinkCutNode<T>) node.getLeft();
        }
        access(node);
        return node;
    }

    public LinkCutNode<T> findRoot(T n) {
        if (!map.containsKey(n)) {
            return null;
        }
        return findRoot(get(n));
    }

    public void cut(T n) {
        if (!map.containsKey(n)) {
            return;
        }
        LinkCutNode<T> node = get(n);
        access(node);
        LinkCutNode<T> leftChild = (LinkCutNode<T>) node.getLeft();
        if (leftChild != null) {
            leftChild.setParent(null);
            node.setLeft(null);
        }
    }

    public void link(T parent, T child) {
        LinkCutNode<T> parentNode = get(parent);
        LinkCutNode<T> childNode = get(child);
        access(childNode);
        access(parentNode);
        childNode.setLeft(parentNode);
        parentNode.setParent(childNode);
    }

    private LinkCutNode<T> get(T value) {
        if (!map.containsKey(value)) {
            map.put(value, new LinkCutNode<>(value));
        }
        return map.get(value);
    }

    public LinkCutNode<T> lowestCommonAncestor(T u, T v) {
        if (!map.containsKey(u) || !map.containsKey(v)) {
            return null;
        }
        LinkCutNode<T> uNode = get(u);
        LinkCutNode<T> vNode = get(v);
        if (findRoot(uNode) != findRoot(vNode)) {
            return null;
        }
        access(uNode);
        return access(vNode);
    }
}