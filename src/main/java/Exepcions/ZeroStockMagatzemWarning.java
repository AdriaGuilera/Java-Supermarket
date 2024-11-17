package Exepcions;

public class ZeroStockMagatzemWarning extends RuntimeException {
    public ZeroStockMagatzemWarning(String nomProducte) {
        super("Producte " + nomProducte + " quantitiy is now 0" );
    }
}
