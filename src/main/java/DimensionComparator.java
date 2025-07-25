public interface DimensionComparator<T> {
    int compare(T val1, T val2);
    KDimensionalNode<T> compare(KDimensionalNode<T> n1, KDimensionalNode<T> n2, KDimensionalNode<T> n3, int k);
}
