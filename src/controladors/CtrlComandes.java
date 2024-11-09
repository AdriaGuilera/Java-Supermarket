package controladors;

import java.util.HashMap;
import java.util.Map;
import classes.Comanda;

public class CtrlComandes {

    private Map<String, Comanda> comandes_creades; // Mapa de comandes

    // Constructor
    public CtrlComandes() {
        this.comandes_creades = new HashMap<>();
    }

    // Método para crear una nueva comanda
    public void crearComanda(String nomComanda) {
        if (nomComanda == null || nomComanda.isEmpty()) {
            System.out.println("Error: El nom de la comanda no pot estar buit.");
            return;
        }
        if (comandes_creades.containsKey(nomComanda)) {
            System.out.println("Error: Ja existeix una comanda amb aquest nom.");
            return;
        }

        comandes_creades.put(nomComanda, new Comanda(nomComanda));
        System.out.println("Comanda creada correctament.");
    }

    // Método para añadir un producto a una comanda
    public void afegirProducteComanda(String nomComanda, String nomProducte, int quantitat) {
        Comanda comanda = comandes_creades.get(nomComanda);
        if (comanda == null) {
            System.out.println("Error: No existeix la comanda amb aquest nom.");
            return;
        }
        comanda.afegirProducte(nomProducte, quantitat);
        System.out.println("Producte afegit correctament a la comanda.");
    }

    // Método para eliminar una comanda
    public void eliminarComanda(String nomComanda) {
        if (comandes_creades.remove(nomComanda) != null) {
            System.out.println("Comanda eliminada correctament.");
        } else {
            System.out.println("Error: No s'ha trobat cap comanda amb aquest nom.");
        }
    }

    // Método para obtener una lista de comandas a partir de nombres
    public Map<String, Comanda> obtenirComandes(String[] nomsComandes) {
        Map<String, Comanda> comandesAExecutar = new HashMap<>();
        for (String nomComanda : nomsComandes) {
            Comanda comanda = comandes_creades.get(nomComanda);
            if (comanda != null) {
                comandesAExecutar.put(nomComanda, comanda);
            }
        }
        return comandesAExecutar;
    }

    // Método para consultar todas las comandas creadas
    public void consultarComandes() {
        if (comandes_creades.isEmpty()) {
            System.out.println("No hi ha comandes creades.");
            return;
        }

        System.out.println("Llista de totes les comandes:");
        for (Map.Entry<String, Comanda> entry : comandes_creades.entrySet()) {
            String nomComanda = entry.getKey();
            Comanda comanda = entry.getValue();

            System.out.println("Comanda: " + nomComanda);
            System.out.println("Productes i quantitats: " + comanda.getOrdres());
        }
    }
}
