package Exepcions;

public class ZeroStockMagatzemWarning extends RuntimeException {
    public ZeroStockMagatzemWarning(String id_producte) {
        super("Producte " + id_producte + " quantitiy is now 0" );
    }
}
