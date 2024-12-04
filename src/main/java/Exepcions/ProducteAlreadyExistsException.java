package Exepcions;

public class ProducteAlreadyExistsException extends RuntimeException {
    public ProducteAlreadyExistsException(String nomProducte) {
        super("El producte '" + nomProducte + "' ja existeix.");
    }
}

