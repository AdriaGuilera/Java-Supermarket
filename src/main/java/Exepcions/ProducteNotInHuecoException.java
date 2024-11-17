package Exepcions;

public class ProducteNotInHuecoException extends RuntimeException {
    public ProducteNotInHuecoException(Integer id_hueco) {
        super("No product in Hueco " + id_hueco);
    }
}
