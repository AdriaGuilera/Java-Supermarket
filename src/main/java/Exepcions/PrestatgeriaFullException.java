package Exepcions;

public class PrestatgeriaFullException extends RuntimeException {
    public PrestatgeriaFullException(String id_prestatgeria) {
        super("Prestatgeria " + id_prestatgeria + " is full");
    }
}
