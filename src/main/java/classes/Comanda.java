package classes;

import Exepcions.ProducteJaExisteixException;
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
     * @throws ProducteJaExisteixException Si el producto ya existe en la comanda.
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
     * Obtiene el nombre de la comanda.
     *
     * @return El nombre de la comanda.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Verifica si un producto específico está presente en la comanda.
     *
     * @param nomProducte Nombre del producto a verificar.
     * @return {@code true} si el producto está presente, de lo contrario {@code false}.
     */
    public boolean conteProducte(String nomProducte) {
        return ordres.containsKey(nomProducte);
    }

    /**
     * Obtiene la cantidad de un producto específico en la comanda.
     *
     * @param nomProducte Nombre del producto.
     * @return La cantidad del producto.
     * @throws ProductNotFoundComandaException Si el producto no se encuentra en la comanda.
     */
    public int getQuantitat(String nomProducte) {
        Integer quantitat = ordres.get(nomProducte);
        if (quantitat == null) {
            throw new ProductNotFoundComandaException(nomProducte);
        }
        return quantitat;
    }

    /**
     * Obtiene el mapa de productos y cantidades de la comanda.
     *
     * @return Una copia del mapa con los productos y sus cantidades.
     */
    public Map<String, Integer> getOrdres() {
        return new HashMap<>(ordres); // Retorna una copia para evitar modificaciones externas
    }
}
