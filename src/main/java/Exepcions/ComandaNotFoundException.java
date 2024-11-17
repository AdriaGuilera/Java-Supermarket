package Exepcions;

public class ComandaNotFoundException extends RuntimeException {
    public ComandaNotFoundException(String nomComanda) {
        super("No existeix la comanda " + nomComanda);
    }
}
