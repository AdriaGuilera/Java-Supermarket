package classes;

import Exepcions.ProducteAlreadyExistsException;
import Exepcions.ProductNotFoundComandaException;
import java.util.HashMap;
import java.util.Map;

/**
 * Representa una comanda con un nombre asociado y un conjunto de productos con sus cantidades.
 * Permite agregar, eliminar y consultar productos dentro de la comanda.
 */
public class Comanda {

    private String nom; // Nombre de la comanda
    private Map<String, Integer> ordres; // Mapa de productos y cantidades

    // Constructor sin argumentos
    public Comanda() {
        this.nom = "";
        this.ordres = new HashMap<>();
    }

    /**
     * Constructor de la clase Comanda.
     *
     * @param nom Nombre de la comanda.
     */
    public Comanda(String nom) {
        this.nom = nom;
        this.ordres = new HashMap<>();
    }

    /**
     * Agrega un producto a la comanda con una cantidad específica.
     * Si el producto ya existe, la cantidad se incrementa.
     *
     * @param nomProducte Nombre del producto a añadir.
     * @param quantitat   Cantidad del producto a añadir.
     * @throws ProducteAlreadyExistsException Si el producto ya existe en la comanda.
     * @throws IllegalArgumentException    Si la cantidad es menor o igual a cero.
     */
    public void afegirProducte(String nomProducte, int quantitat) {
        if (ordres.containsKey(nomProducte)) {
            ordres.compute(nomProducte, (k, v) -> v + quantitat);
        } else {
            ordres.put(nomProducte, quantitat);
        }
    }

    /**
     * Elimina un producto de la comanda o reduce su cantidad.
     * Si la cantidad resultante es cero o menor, el producto es eliminado del mapa.
     *
     * @param nomProducte Nombre del producto a eliminar.
     * @param quantitat   Cantidad a reducir.
     * @throws ProductNotFoundComandaException Si el producto no se encuentra en la comanda.
     */
    public void eliminarProducte(String nomProducte, int quantitat) throws ProductNotFoundComandaException {
        if (!ordres.containsKey(nomProducte)) {
            throw new ProductNotFoundComandaException(nomProducte);
        } else if (ordres.get(nomProducte) - quantitat <= 0) {
            ordres.remove(nomProducte);
        } else {
            ordres.put(nomProducte, ordres.get(nomProducte) - quantitat);
        }
    }

    /**
     * Elimina un producte de la comanda si existeix en les ordres.
     * Si el producte no existeix, no realitza cap acció addicional.
     *
     * @param nomProducte Nom del producte que es vol eliminar de la comanda.
     * @throws ProductNotFoundComandaException Excepció que indica que el producte no s'ha trobat a la comanda.
     */
    public void eliminarProducteSenseExcepcio(String nomProducte) throws ProductNotFoundComandaException {
        if (ordres.containsKey(nomProducte)) {
            ordres.remove(nomProducte);
        }
    }


    /**
     * Obtiene el nombre de la comanda.
     *
     * @return El nombre de la comanda.
     */
    public String getNom() {
        return nom;
    }



    /**
     * Obtiene el mapa de productos y cantidades de la comanda.
     *
     * @return Una copia del mapa con los productos y sus cantidades.
     */
    public Map<String, Integer> getOrdres() {
        return new HashMap<>(ordres); // Retorna una copia para evitar modificaciones externas
    }


    /**
     * Estableix el nom de la comanda.
     *
     * @param nom Nom que s'assignarà a la comanda.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }


}
