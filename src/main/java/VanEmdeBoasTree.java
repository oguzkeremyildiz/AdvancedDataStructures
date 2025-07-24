public class VanEmdeBoasTree {

    private final VanEmdeBoasNode root;

    public VanEmdeBoasTree(int bound) throws BoundIsNotValidException {
        if (!(bound > 0 && ((bound) & (bound - 1)) == 0)) {
            throw new BoundIsNotValidException(bound);
        }
        this.root = new VanEmdeBoasNode(bound);
    }

    private void insert(VanEmdeBoasNode node, int value) {
        if (value > node.getBound()) {
            throw new IndexOutOfBoundsException();
        }
        if (node.getMinimum() == -1) {
            node.setMinimum(value);
            node.setMaximum(value);
        } else if (node.getMinimum() > value) {
            int temp = node.getMinimum();
            node.setMinimum(value);
            insert(node, temp);
        } else {
            if (node.getBound() > 2) {
                int nodeIndex = node.getIndexInCurrentNode(value);
                int childIndex = node.getIndexInChildNode(value);
                if (node.getChildren(nodeIndex) == null) {
                    node.addChild(nodeIndex);
                    insert(node.getSummary(), nodeIndex);
                }
                insert(node.getChildren(nodeIndex), childIndex);
            }
            if (value > node.getMaximum()) {
                node.setMaximum(value);
            }
        }
    }

    public void insert(int value) {
        insert(root, value);
    }

    private boolean contains(VanEmdeBoasNode node, int value) {
        if (node == null || value > node.getBound()) {
            return false;
        }
        if (node.getMaximum() == value || node.getMinimum() == value) {
            return true;
        } else {
            if (node.getBound() == 2) {
                return false;
            }
            int nodeIndex = node.getIndexInCurrentNode(value);
            int childIndex = node.getIndexInChildNode(value);
            return contains(node.getChildren(nodeIndex), childIndex);
        }
    }

    public boolean contains(int value) {
        return contains(root, value);
    }

    private int successor(VanEmdeBoasNode node, int value) {
        if (node.getBound() == 2) {
            if (node.getMinimum() != -1) {
                if (value < node.getMinimum()) {
                    return node.getMinimum();
                } else if (value < node.getMaximum()) {
                    return node.getMaximum();
                }
            }
        } else {
            if (value < node.getMinimum() && node.getMinimum() != -1) {
                return node.getMinimum();
            }
            int nodeIndex = node.getIndexInCurrentNode(value);
            int childIndex = node.getIndexInChildNode(value);
            if (node.getChildren(nodeIndex) != null && node.getChildren(nodeIndex).getMaximum() > childIndex) {
                return node.getValue(nodeIndex, successor(node.getChildren(nodeIndex), childIndex));
            } else {
                int childValue = successor(node.getSummary(), nodeIndex);
                if (node.getChildren(childValue) != null) {
                    return node.getValue(childValue, node.getChildren(childValue).getMinimum());
                }
            }
        }
        return -1;
    }

    public int successor(int value) {
        if (value < 0 || value >= root.getBound()) {
            return -1;
        }
        return successor(root, value);
    }

    private int predecessor(VanEmdeBoasNode node, int value) {
        if (node.getBound() == 2) {
            if (value > node.getMaximum()) {
                return node.getMaximum();
            }
            if (value > node.getMinimum()) {
                return node.getMinimum();
            }
        } else {
            if (value > node.getMaximum() && node.getMaximum() != -1) {
                return node.getMaximum();
            }
            int nodeIndex = node.getIndexInCurrentNode(value);
            int childIndex = node.getIndexInChildNode(value);
            if (node.getChildren(nodeIndex) != null && node.getChildren(nodeIndex).getMinimum() < childIndex) {
                return node.getValue(nodeIndex, predecessor(node.getChildren(nodeIndex), childIndex));
            } else {
                int childValue = predecessor(node.getSummary(), nodeIndex);
                if (node.getChildren(childValue) != null) {
                    return node.getValue(childValue, node.getChildren(childValue).getMaximum());
                } else {
                    if (value > node.getMinimum()) {
                        return node.getMinimum();
                    }
                }
            }
        }
        return -1;
    }

    public int predecessor(int value) {
        if (value < 0 || value >= root.getBound()) {
            return -1;
        }
        return predecessor(root, value);
    }

    private void remove(VanEmdeBoasNode node, int value) {
        if (node.getMaximum() == node.getMinimum()) {
            node.setMaximum(-1);
            node.setMinimum(-1);
        } else if (node.getBound() == 2) {
            if (value == 0) {
                node.setMinimum(node.getMaximum());
            } else {
                node.setMaximum(node.getMinimum());
            }
        } else {
            if (node.getMinimum() == value) {
                int nodeIndex = node.getSummary().getMinimum();
                value = node.getValue(nodeIndex, node.getChildren(nodeIndex).getMinimum());
                node.setMinimum(value);
            }
            int nodeIndex = node.getIndexInCurrentNode(value);
            int childIndex = node.getIndexInChildNode(value);
            remove(node.getChildren(nodeIndex), childIndex);
            if (node.getChildren(nodeIndex).getMinimum() == -1) {
                node.removeChild(nodeIndex);
                remove(node.getSummary(), nodeIndex);
                if (node.getMaximum() == value) {
                    nodeIndex = node.getSummary().getMaximum();
                    if (nodeIndex == -1) {
                        node.setMaximum(node.getMinimum());
                    } else {
                        node.setMaximum(node.getValue(nodeIndex, node.getChildren(nodeIndex).getMaximum()));
                    }
                }
            } else {
                if (node.getMaximum() == value) {
                    node.setMaximum(node.getValue(nodeIndex, node.getChildren(nodeIndex).getMaximum()));
                }
            }
        }
    }

    public void remove(int value) {
        if (contains(root, value)) {
            remove(root, value);
        }
    }
}
