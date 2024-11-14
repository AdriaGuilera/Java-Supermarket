package Exepcions;

public class MaxHuecoWarning extends RuntimeException {
    public MaxHuecoWarning(String id_producte) {
      super("Producte " + id_producte + " quantitiy to add exceeds the maximum capacity of the magatzem. " +
              "Stock set to max" );
    }
}
