package Exepcions;

public class ProducteJaExisteixException extends RuntimeException {
    public ProducteJaExisteixException(String nomProducte) {
        super("El producte '" + nomProducte + "' ja existeix.");
    }
}

