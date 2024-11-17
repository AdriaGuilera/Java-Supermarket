package Exepcions;

public class MidaPrestatgeriaMinException extends RuntimeException {
    public MidaPrestatgeriaMinException(String id) {
        super("La prestatgeria " + id + " ha de tenir com a mínim un prestatge.");
    }
}
