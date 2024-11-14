package Exepcions;

public class ProductNotFoundPrestatgeriaException extends RuntimeException {
    public ProductNotFoundPrestatgeriaException(String id_producte, String id_prestatgeria) {
        super("Producte " + id_producte + " not found in prestatgeria " + id_prestatgeria);
    }
}
