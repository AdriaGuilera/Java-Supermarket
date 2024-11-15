package Exepcions;

public class QuanitatInvalidException extends RuntimeException {
    public QuanitatInvalidException(int num) {
      super("Quantitat ha de ser >="+num);
    }
}
