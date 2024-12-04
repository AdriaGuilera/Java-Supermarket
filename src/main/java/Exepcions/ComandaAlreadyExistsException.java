package Exepcions;

public class ComandaAlreadyExistsException extends RuntimeException {
    public ComandaAlreadyExistsException(String id_comanda) {
        super("Comanda " + id_comanda + " already exists");
    }
}
