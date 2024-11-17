package Exepcions;

public class NotEnoughQuantityMagatzem extends RuntimeException {
    public NotEnoughQuantityMagatzem(String id_producte) {
        super("Not enough quantity of product " + id_producte + " in Magatzem.");
    }
}
