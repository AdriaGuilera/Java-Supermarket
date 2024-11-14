package Exepcions;

public class InvalidHuecosException extends RuntimeException {
    public InvalidHuecosException() {
        super("Huecos has to be an integer greater than 0 and inside the range of the prestatgeria. Origin must be different than destination");
    }
}
