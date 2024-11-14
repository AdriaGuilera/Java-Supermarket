package Exepcions;

public class ComandaNoExisteix extends RuntimeException {
    public ComandaNoExisteix(String nomComanda) {
        super("No existeix la comanda " + nomComanda);
    }
}
