import java.util.HashMap;
import java.util.Map;

public class CtrlComandes {

    private static Map<String, Comanda> comandes_creades; // Mapa de comandes

    // Constructor
    public CtrlComandes() {
        this.comandes_creades = new HashMap<>();
    }

    // Método para crear una nueva comanda
    public static String crearComanda(String nomComanda) {
        if (nomComanda == null || nomComanda.isEmpty()) {
            return "Error: El nom de la comanda no pot estar buit.";
        }
        if (comandes_creades.containsKey(nomComanda)) {
            return "Error: Ja existeix una comanda amb aquest nom.";
        }
        comandes_creades.put(nomComanda, new Comanda(nomComanda));
        return "Comanda creada correctament.";
    }

    // Método para añadir un producto a una comanda
    public static String afegirProducteComanda(String nomComanda, String nomProducte, int quantitat) {
        Comanda comanda = comandes_creades.get(nomComanda);
        if (comanda == null) {
            return "Error: No existeix la comanda amb aquest nom.";
        }
        comanda.afegirProducte(nomProducte, quantitat);
        return "Producte afegit correctament a la comanda.";
    }

    // Método para eliminar una comanda
    public static String eliminarComanda(String nomComanda) {
        if (comandes_creades.remove(nomComanda) != null) {
            return "Comanda eliminada correctament.";
        }
        return "Error: No s'ha trobat cap comanda amb aquest nom.";
    }

    // Método para obtener una lista de comandas a partir de nombres
    public static Map<String, Comanda> obtenirComandes(String[] nomsComandes) {
        Map<String, Comanda> comandesAExecutar = new HashMap<>();
        for (String nomComanda : nomsComandes) {
            Comanda comanda = comandes_creades.get(nomComanda);
            if (comanda != null) {
                comandesAExecutar.put(nomComanda, comanda);
            }
        }
        return comandesAExecutar;
    }

}
