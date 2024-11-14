package controladors;

import java.util.HashMap;
import java.util.Map;

import Exepcions.ComandaNotFoundException;
import Exepcions.ComandaNotFoundException;
import Exepcions.QuanitatInvalidException;
import classes.Comanda;
import Exepcions.ProducteJaExisteixException;
import Exepcions.ProductNotFoundComandaException;

public class CtrlComandes {

    private Map<String, Comanda> comandes_creades; // Mapa de comandes

    // Constructor
    public CtrlComandes() {
        this.comandes_creades = new HashMap<>();
    }

    // Método para crear una nueva comanda
    public void crearComanda(String nomComanda) {
        if (nomComanda == null || nomComanda.isEmpty()) {
            throw new IllegalArgumentException("El nom de la comanda no pot estar buit.");
        }
        if (comandes_creades.containsKey(nomComanda)) {
            throw new IllegalArgumentException("Ja existeix una comanda amb aquest nom.");
        }

        comandes_creades.put(nomComanda, new Comanda(nomComanda));
    }

    // Método para añadir un producto a una comanda
    public void afegirProducteComanda(String nomComanda, String nomProducte, int quantitat) throws QuanitatInvalidException, ComandaNotFoundException {
        Comanda comanda = comandes_creades.get(nomComanda);
        if (comanda == null) {
            throw new ComandaNotFoundException(nomComanda);
        }
        comanda.afegirProducte(nomProducte, quantitat);

    }

    // Método para eliminar un producto de una comanda
    public void eliminarProducteComanda(String nomComanda, String nomProducte) {
        Comanda comanda = comandes_creades.get(nomComanda);
        if (comanda == null) {
            throw new IllegalArgumentException("No existeix la comanda amb aquest nom.");
        }
        try {
            comanda.eliminarProducte(nomProducte);
        } catch (ProductNotFoundComandaException e) {
            throw e; // Reenvía la excepción
        }
    }

    // Método para eliminar una comanda
    public void eliminarComanda(String nomComanda) {
        if (comandes_creades.remove(nomComanda) == null) {
            throw new IllegalArgumentException("No s'ha trobat cap comanda amb aquest nom.");
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
    public Map<String, Comanda> getComandes() {
        return new HashMap<>(comandes_creades); // Retorna una copia para evitar modificaciones externas
    }
    public Comanda getComandaUnica(String nomComanda) {
        if (comandes_creades.containsKey(nomComanda)) {        return comandes_creades.get(nomComanda);}
        else{
            throw new ComandaNotFoundException(nomComanda);
        }
    }

    // Método para crear una comanda automática con productos
    public void crearComandaAutomatica(String nomComanda, Map<String, Integer> productosFaltantes) {
        crearComanda(nomComanda);
        for (Map.Entry<String, Integer> entry : productosFaltantes.entrySet()) {
            try {
                afegirProducteComanda(nomComanda, entry.getKey(), entry.getValue());
            } catch (ProducteJaExisteixException | IllegalArgumentException e) {
                throw e; // Reenvía la excepción si ocurre
            }
        }
    }
}
