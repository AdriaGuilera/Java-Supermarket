package Exepcions;

public class NotEnoughQuantityPrestatgeriaWarning extends RuntimeException {
    public NotEnoughQuantityPrestatgeriaWarning(String id_producte, String id_prestatgeria) {
        super("Producte " + id_producte + " does not have enough quantity in prestatgeria " + id_prestatgeria
                + ". All stock added");
    }
}
