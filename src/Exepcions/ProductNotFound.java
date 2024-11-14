package Exepcions;

public class ProductNotFound extends RuntimeException {
    public ProductNotFound(String id_producte) {
        super("Producte " + id_producte + " not found");
    }
}
