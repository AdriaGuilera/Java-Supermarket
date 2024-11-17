package Exepcions;

public class PrestatgeriaNotFoundException extends RuntimeException {
    public PrestatgeriaNotFoundException(String id_prestatgeria) {
        super("Prestatgeria " + id_prestatgeria + " not found");
    }
}
