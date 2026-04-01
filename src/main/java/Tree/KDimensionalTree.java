package Tree;

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
                insert((KDimensionalNode<T>) node.getRight(), points, dimension + 1);
            } else {
                node.setRight(new KDimensionalNode<>(points));
            }
        } else {
            if (node.getLeft() != null) {
                insert((KDimensionalNode<T>)node.getLeft(), points, dimension + 1);
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
            return min((KDimensionalNode<T>) node.getLeft(), d, dimension + 1);
        }
        return comparator.compare(node, min((KDimensionalNode<T>) node.getLeft(), d, dimension + 1), min((KDimensionalNode<T>) node.getRight(), d, dimension + 1), d);
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
            return contains((KDimensionalNode<T>) node.getRight(), points, dimension + 1);
        } else {
            return contains((KDimensionalNode<T>) node.getLeft(), points, dimension + 1);
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
                KDimensionalNode<T> rightMin = min((KDimensionalNode<T>) node.getRight(), currentDimension, dimension + 1);
                copyPoint(node.getPoints(), rightMin.getPoints());
                node.setRight(remove((KDimensionalNode<T>) node.getRight(), rightMin.getPoints(), dimension + 1));
            } else {
                KDimensionalNode<T> leftMin = min((KDimensionalNode<T>) node.getLeft(), currentDimension, dimension + 1);
                copyPoint(node.getPoints(), leftMin.getPoints());
                node.setRight(remove((KDimensionalNode<T>) node.getLeft(), leftMin.getPoints(), dimension + 1));
                node.setLeft(null);
            }
        } else if (comparator.compare(points[currentDimension], node.getDimension(currentDimension)) >= 0) {
            node.setRight(remove((KDimensionalNode<T>) node.getRight(), points, dimension + 1));
        } else {
            node.setLeft(remove((KDimensionalNode<T>) node.getLeft(), points, dimension + 1));
        }
        return node;
    }

    public void remove(T[] points) {
        if (contains(points)) {
            remove(root, points, 0);
        }
    }
}
