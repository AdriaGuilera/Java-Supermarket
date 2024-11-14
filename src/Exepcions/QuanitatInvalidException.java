package Exepcions;

public class QuanitatInvalidException extends RuntimeException {
    public QuanitatInvalidException() {
      super("Quanitat has to be an integer greater than 0");
    }
}
