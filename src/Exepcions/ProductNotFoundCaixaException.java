package Exepcions;

public class ProductNotFoundCaixaException extends RuntimeException {
    public ProductNotFoundCaixaException(String id_producte) {
        super("Producte " + id_producte + " not found in Caixa");
    }
}
