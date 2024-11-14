package Exepcions;

public class ProductNotFoundComandaException extends RuntimeException {
    public ProductNotFoundComandaException(String nomProducte) {
        super("Producte no trobat en la comanda: " + nomProducte);
    }
}
