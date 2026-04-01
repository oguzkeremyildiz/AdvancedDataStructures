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
        SegmentNode<T> left = buildSegmentTree(min, (min + max) / 2, array, typeInterface);
        SegmentNode<T> right = buildSegmentTree((min + max) / 2 + 1, max, array, typeInterface);
        parent.setLeft(left);
        parent.setRight(right);
        parent.setSum(typeInterface.add(left.getSum(), right.getSum()));
        return parent;
    }

    private T getSumInRange(SegmentNode<T> node, int min, int max, int curMin, int curMax) {
        if (min <= curMin && max >= curMax) {
            return node.getSum();
        }
        if (curMax < min || curMin > max) {
            return this.typeInterface.zeroValue();
        }
        T leftValue = getSumInRange((SegmentNode<T>) node.getLeft(), min, max, curMin, (curMax + curMin) / 2);
        T rightValue = getSumInRange((SegmentNode<T>) node.getRight(), min, max, (curMax + curMin) / 2 + 1, curMax);
        return this.typeInterface.add(leftValue, rightValue);
    }

    public T getSumInRange(int min, int max) {
        return getSumInRange(root, min, max, 0, this.array.length - 1);
    }

    private void updateValue(SegmentNode<T> node, int curMin, int curMax, int index, T value, T difference) {
        if (curMin == curMax) {
            node.setSum(value);
            return;
        }
        int mid = (curMin + curMax) / 2;
        if (index <= mid && index >= curMin) {
            updateValue((SegmentNode<T>) node.getLeft(), curMin, mid, index, value, difference);
            ((SegmentNode<T>) node.getLeft()).setSum(typeInterface.add(((SegmentNode<T>) node.getLeft()).getSum(), difference));
        } else if (index >= mid && index <= curMax) {
            updateValue((SegmentNode<T>) node.getRight(), mid + 1, curMax, index, value, difference);
            ((SegmentNode<T>) node.getRight()).setSum(typeInterface.add(((SegmentNode<T>) node.getRight()).getSum(), difference));
        }
    }

    public void updateValue(int index, T value) {
        if (index < 0 || index >= this.array.length) {
            throw new IllegalArgumentException("index out of range");
        }
        T difference = this.typeInterface.subtract(value, this.array[index]);
        this.array[index] = value;
        updateValue(root, 0, this.array.length - 1, index, value, difference);
        root.setSum(typeInterface.add(root.getSum(), difference));
    }
}
