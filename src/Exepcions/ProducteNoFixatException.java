package Exepcions;

public class ProducteNoFixatException extends RuntimeException {
  public ProducteNoFixatException(String id_producte) {
    super("Producte " + id_producte + " no esta fixat");
  }
}
