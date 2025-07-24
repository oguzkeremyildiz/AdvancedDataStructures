public class BoundIsNotValidException extends Exception {

   private int bound;

    public BoundIsNotValidException(int bound) {
        this.bound = bound;
    }

    public String toString() {
        return "Bound is not a power of 2: " + bound;
    }
}
