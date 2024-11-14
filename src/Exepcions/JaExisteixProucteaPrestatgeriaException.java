package Exepcions;

public class JaExisteixProucteaPrestatgeriaException extends RuntimeException {
    public JaExisteixProucteaPrestatgeriaException(String id_producte, String id_prestatgeria) {
        super("Producte " + id_producte + " already exists in prestatgeria " + id_prestatgeria);
    }
}
