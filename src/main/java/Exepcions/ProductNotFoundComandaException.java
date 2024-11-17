package Exepcions;

public class ProductNotFoundComandaException extends RuntimeException {
    public ProductNotFoundComandaException(String nomProducte) {
        super("Producte "+nomProducte+ " no trobat en la comanda: ");
    }
}
