import java.util.Comparator;

public interface DimensionComparator<T> extends Comparator<T> {
    KDimensionalNode<T> compare(KDimensionalNode<T> n1, KDimensionalNode<T> n2, KDimensionalNode<T> n3, int k);
}
