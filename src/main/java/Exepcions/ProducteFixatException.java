package Exepcions;

public class ProducteFixatException extends RuntimeException {
  public ProducteFixatException(String id_producte) {
    super("Producte " + id_producte + " esta fixat");
  }
}
