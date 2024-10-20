
import java.util.HashMap;
import java.util.Map;


public class CtrlComandes {

    // Mapa donde se almacena el nombre de la comanda y la comanda en sí
    private Map<String, Comanda> comandes_creades;

    // Constructor
    public CtrlComandes() {
        comandes_creades = new HashMap<>(); // Inicializamos el mapa
    }

    // Método para añadir una comanda al mapa
    public void afegirComanda(String nomC, Comanda comanda) {
        comandes_creades.put(nomC, comanda);
    }

    // Método para obtener una comanda por su nombre
    public Comanda obtenirComanda(String nomC) {
        return comandes_creades.get(nomC);
    }

    // Método para eliminar una comanda por su nombre
    public void eliminarComanda(String nomC) {
        comandes_creades.remove(nomC);
    }

}
