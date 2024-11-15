package Exepcions;

public class ProductNotFoundPrestatgeriaException extends RuntimeException {
    public ProductNotFoundPrestatgeriaException(String id_prestatgeria, String id_producte) {
        super("Producte " + id_producte + " not found in prestatgeria " + id_prestatgeria);
    }
}
