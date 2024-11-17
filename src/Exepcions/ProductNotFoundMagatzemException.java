package Exepcions;

public class ProductNotFoundMagatzemException extends RuntimeException {
  public ProductNotFoundMagatzemException(String nomProducte) {
    super("Producte " + nomProducte + " no existeix");
  }
}
