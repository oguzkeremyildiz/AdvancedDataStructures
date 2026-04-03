package Tree;

public class SegmentTree<T> {

    private final TypeInterface<T> typeInterface;
    private final SegmentNode<T> root;
    private final T[] array;

    public SegmentTree(TypeInterface<T> typeInterface, T[] array) {
        this.typeInterface = typeInterface;
        this.array = array;
        this.root = buildSegmentTree(0, array.length - 1, array, typeInterface);
    }

    private SegmentNode<T> buildSegmentTree(int min, int max, T[] array, TypeInterface<T> typeInterface) {
        if (min == max) {
            return new SegmentNode<>(array[min]);
        }
        SegmentNode<T> parent = new SegmentNode<>();
        int mid = (min + max) / 2;
        SegmentNode<T> left = buildSegmentTree(min, mid, array, typeInterface);
        SegmentNode<T> right = buildSegmentTree(mid + 1, max, array, typeInterface);
        parent.setLeft(left);
        parent.setRight(right);
        parent.setValue(typeInterface.add(left.getValue(), right.getValue()));
        return parent;
    }

    private T getSumInRange(SegmentNode<T> node, int min, int max, int curMin, int curMax) {
        if (min <= curMin && max >= curMax) {
            return node.getValue();
        }
        if (curMax < min || curMin > max) {
            return this.typeInterface.zeroValue();
        }
        pushDown(node, curMin, curMax);
        int mid = (curMax + curMin) / 2;
        T leftValue = getSumInRange((SegmentNode<T>) node.getLeft(), min, max, curMin, mid);
        T rightValue = getSumInRange((SegmentNode<T>) node.getRight(), min, max, mid + 1, curMax);
        return this.typeInterface.add(leftValue, rightValue);
    }

    public T getSumInRange(int min, int max) {
        if (min < 0 || max >= this.array.length || min > max) {
            throw new IllegalArgumentException("Index out of range");
        }
        return getSumInRange(root, min, max, 0, this.array.length - 1);
    }

    private void updateValues(SegmentNode<T> node, T value, int curMin, int curMax) {
        int rangeLength = curMax - curMin + 1;
        node.setValue(typeInterface.add(node.getValue(), typeInterface.multiply(rangeLength, value)));
        if (node.getLazyValue() != null) {
            node.setLazyValue(typeInterface.add(node.getLazyValue(), value));
        } else {
            node.setLazyValue(value);
        }
    }

    private void pushDown(SegmentNode<T> node, int curMin, int curMax) {
        if (node.getLazyValue() != null) {
            int mid = (curMin + curMax) / 2;
            if (node.getLeft() != null) {
                updateValues((SegmentNode<T>) node.getLeft(), node.getLazyValue(), curMin, mid);
            }
            if (node.getRight() != null) {
                updateValues((SegmentNode<T>) node.getRight(), node.getLazyValue(), mid + 1, curMax);
            }
            node.setLazyValue(null);
        }
    }

    private void addValue(SegmentNode<T> node, int curMin, int curMax, int leftIndex, int rightIndex, T value) {
        if (leftIndex > curMax || rightIndex < curMin) {
            return;
        }
        if (leftIndex <= curMin && rightIndex >= curMax) {
            updateValues(node, value, curMin, curMax);
            return;
        }
        pushDown(node, curMin, curMax);
        int mid = (curMin + curMax) / 2;
        addValue((SegmentNode<T>) node.getLeft(), curMin, mid, leftIndex, rightIndex, value);
        addValue((SegmentNode<T>) node.getRight(), mid + 1, curMax, leftIndex, rightIndex, value);
        node.setValue(typeInterface.add(((SegmentNode<T>) node.getLeft()).getValue(), ((SegmentNode<T>) node.getRight()).getValue()));
    }

    public void addValue(int leftIndex, int rightIndex, T value) {
        if (leftIndex < 0 || rightIndex >= this.array.length || leftIndex > rightIndex) {
            throw new IllegalArgumentException("Index out of range");
        }
        addValue(root, 0, this.array.length - 1, leftIndex, rightIndex, value);
    }

    public void addValue(int index, T value) {
        addValue(index, index, value);
    }
}