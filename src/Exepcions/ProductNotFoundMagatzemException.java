package Exepcions;

public class ProductNotFoundMagatzemException extends RuntimeException {
  public ProductNotFoundMagatzemException(String nomProducte) {
    super("Producte no trobat en el magatzem: " + nomProducte);
  }
}
