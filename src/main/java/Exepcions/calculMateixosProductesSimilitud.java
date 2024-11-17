package Exepcions;

public class calculMateixosProductesSimilitud extends RuntimeException {
    public calculMateixosProductesSimilitud(String nomProducte) {
        super("No pots afegir similitud entre dos productes que es diuen igual: " + nomProducte);
    }
}
