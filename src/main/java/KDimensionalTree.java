public class KDimensionalTree<T> {

    private final int k;
    private KDimensionalNode<T> root;
    private final DimensionComparator<T> comparator;

    public KDimensionalTree(int k, DimensionComparator<T> comparator) {
        this.k = k;
        this.root = null;
        this.comparator = comparator;
    }

    private void insert(KDimensionalNode<T> node, T[] points, int dimension) {
        int currentDimension = dimension % k;
        T point = points[currentDimension];
        T currentPoint = node.getDimension(currentDimension);
        if (comparator.compare(point, currentPoint) >= 0) {
            if (node.getRight() != null) {
                insert(node.getRight(), points, dimension + 1);
            } else {
                node.setRight(new KDimensionalNode<>(points));
            }
        } else {
            if (node.getLeft() != null) {
                insert(node.getLeft(), points, dimension + 1);
            } else {
                node.setLeft(new KDimensionalNode<>(points));
            }
        }
    }

    public void insert(T[] points) {
        if (points.length != k) {
            throw new DimensionIsNotValidException("Dimensions must be of equal to k");
        }
        if (root == null) {
            root = new KDimensionalNode<>(points);
        } else {
            insert(root, points, 0);
        }
    }

    private KDimensionalNode<T> min(KDimensionalNode<T> node, int d, int dimension) {
        if (node == null) {
            return null;
        }
        int currentDimension = dimension % k;
        if (currentDimension == d) {
            if (node.getLeft() == null) {
                return node;
            }
            return min(node.getLeft(), d, dimension + 1);
        }
        return comparator.compare(node, min(node.getLeft(), d, dimension + 1), min(node.getRight(), d, dimension + 1), d);
    }

    public KDimensionalNode<T> min(int d) {
        if (d <= k) {
            return min(root, d, 0);
        }
        return null;
    }

    private boolean equals(T[] p1, T[] p2) {
        for (int i = 0; i < p1.length; i++) {
            if (comparator.compare(p1[i], p2[i]) != 0) {
                return false;
            }
        }
        return true;
    }

    private boolean contains(KDimensionalNode<T> node, T[] points, int dimension) {
        if (node == null) {
            return false;
        }
        int currentDimension = dimension % k;
        T point = points[currentDimension];
        T currentPoint = node.getDimension(currentDimension);
        if (equals(points, node.getPoints())) {
            return true;
        }
        if (comparator.compare(point, currentPoint) >= 0) {
            return contains(node.getRight(), points, dimension + 1);
        } else {
            return contains(node.getLeft(), points, dimension + 1);
        }
    }

    public boolean contains(T[] points) {
        return contains(root, points, 0);
    }

    private void copyPoint(T[] p1, T[] p2) {
        System.arraycopy(p2, 0, p1, 0, p1.length);
    }

    private KDimensionalNode<T> remove(KDimensionalNode<T> node, T[] points, int dimension) {
        if (node == null) {
            return null;
        }
        int currentDimension = dimension % k;
        if (equals(points, node.getPoints())) {
            if (node.getLeft() == null && node.getRight() == null) {
                node = null;
            } else if (node.getRight() != null) {
                KDimensionalNode<T> rightMin = min(node.getRight(), currentDimension, dimension + 1);
                copyPoint(node.getPoints(), rightMin.getPoints());
                node.setRight(remove(node.getRight(), rightMin.getPoints(), dimension + 1));
            } else {
                KDimensionalNode<T> leftMin = min(node.getLeft(), currentDimension, dimension + 1);
                copyPoint(node.getPoints(), leftMin.getPoints());
                node.setRight(remove(node.getLeft(), leftMin.getPoints(), dimension + 1));
            }
        } else if (comparator.compare(points[currentDimension], node.getDimension(currentDimension)) >= 0) {
            node.setRight(remove(node.getRight(), points, dimension + 1));
        } else {
            node.setLeft(remove(node.getLeft(), points, dimension + 1));
        }
        return node;
    }

    public void remove(T[] points) {
        if (contains(points)) {
            remove(root, points, 0);
        }
    }
}
