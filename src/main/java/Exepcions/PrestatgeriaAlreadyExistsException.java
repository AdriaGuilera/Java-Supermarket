package Exepcions;

public class PrestatgeriaAlreadyExistsException extends RuntimeException {
    public PrestatgeriaAlreadyExistsException(String id_prestatgeria) {
        super("Prestatgeria " + id_prestatgeria + " already exists");
    }
}
