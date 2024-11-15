package Exepcions;

public class PrestatgeriaJaExisteixException extends RuntimeException {
    public PrestatgeriaJaExisteixException(String id_prestatgeria) {
        super("Prestatgeria " + id_prestatgeria + " already exists");
    }
}
