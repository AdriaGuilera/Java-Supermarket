package Exepcions;

public class QuanitatInvalidException extends RuntimeException {
    public QuanitatInvalidException() {
      super("Quantitat has to be greater than 0");
    }
}
