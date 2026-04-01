package Tree;

public class LinkCutNode<T> extends TreeNode<T> {

    private LinkCutNode<T> parent;
    private LinkCutNode<T> pathParent;

    public LinkCutNode(T value) {
        super(value);
    }

    public void setParent(LinkCutNode<T> parent) {
        this.parent = parent;
    }

    public LinkCutNode<T> getPathParent() {
        return pathParent;
    }

    public void setPathParent(LinkCutNode<T> pathParent) {
        this.pathParent = pathParent;
    }

    public void rotate() {
        LinkCutNode<T> p = this.parent;
        if (p == null) return;
        LinkCutNode<T> g = p.parent;
        if (g != null) {
            if (p.equals(g.left)) {
                g.left = this;
            } else {
                g.right = this;
            }
        }
        this.parent = g;
        if (this.equals(p.right)) {
            LinkCutNode<T> tempLeft = (LinkCutNode<T>) this.left;
            p.right = tempLeft;
            if (tempLeft != null) {
                tempLeft.parent = p;
            }
            this.left = p;
            p.parent = this;
        } else {
            LinkCutNode<T> tempRight = (LinkCutNode<T>) this.right;
            p.left = tempRight;
            if (tempRight != null) {
                tempRight.parent = p;
            }
            this.right = p;
            p.parent = this;
        }
        this.pathParent = p.pathParent;
    }

    public void splay() {
        while (this.parent != null) {
            LinkCutNode<T> p = this.parent;
            LinkCutNode<T> g = p.parent;
            if (g != null) {
                if (this.equals(p.right) == p.equals(g.right)) {
                    p.rotate();
                } else {
                    this.rotate();
                }
            }
            this.rotate();
        }
    }
}