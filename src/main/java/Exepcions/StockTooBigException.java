package Exepcions;

public class StockTooBigException extends RuntimeException {
    public StockTooBigException(int maxStock) {
        super("Stock ha de ser mes petit que la quantitat maxima del producte: " + maxStock);
    }
}
