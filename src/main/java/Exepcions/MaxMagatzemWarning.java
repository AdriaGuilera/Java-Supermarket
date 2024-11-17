package Exepcions;

public class MaxMagatzemWarning extends RuntimeException {
    public MaxMagatzemWarning(String id_producte) {
      super("Producte " + id_producte + " quantitiy to add exceeds the maximum capacity of the magatzem. " +
              "Stock set to maximum, excess deleted" );
    }
}
