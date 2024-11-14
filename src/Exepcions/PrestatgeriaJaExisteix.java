package Exepcions;

public class PrestatgeriaJaExisteix extends RuntimeException {
    public PrestatgeriaJaExisteix(String id_prestatgeria) {
        super("Prestatgeria " + id_prestatgeria + " already exists");
    }
}
