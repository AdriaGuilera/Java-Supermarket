package Exepcions;

public class QuanitatInvalidException extends RuntimeException {
    public QuanitatInvalidException() {
      super("Quantitat ha de ser més gran que 0");
    }
}
