package Exepcions;

public class MidaPrestatgeriaInvalidException extends RuntimeException {
    public MidaPrestatgeriaInvalidException() {

      super("La mida del prestatge ha de ser un múltiple major que 0 de la mida del prestatge.");
    }
}
