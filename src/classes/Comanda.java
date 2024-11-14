package classes;

import Exepcions.ProducteJaExisteixException;
import Exepcions.ProductNotFoundComandaException;
import Exepcions.QuanitatInvalidException;

import java.util.HashMap;
import java.util.Map;

public class Comanda {
    private String nom; // Nombre de la comanda
    private Map<String, Integer> ordres; // Mapa de productos y cantidades

    // Constructor
    public Comanda(String nom) {
        this.nom = nom;
        this.ordres = new HashMap<>();
    }

    // Getter para obtener el nombre de la comanda
    public String getNom() {
        return nom;
    }

    /**
     * Método para añadir un producto a la comanda.
     * @param nomProducte Nombre del producto a añadir.
     * @param quantitat Cantidad del producto a añadir.
     * @throws ProducteJaExisteixException si el producto ya existe en la comanda.
     * @throws IllegalArgumentException si la cantidad es menor o igual a cero.
     */
    public void afegirProducte(String nomProducte, int quantitat) throws QuanitatInvalidException {
        if (quantitat <= 0) {
            throw new QuanitatInvalidException();
        }
        if (ordres.containsKey(nomProducte)) {
            ordres.compute(nomProducte, (k, v) -> v + quantitat);
        }
        else ordres.put(nomProducte, quantitat);
    }

    /**
     * Método para eliminar un producto de la comanda.
     * @param nomProducte Nombre del producto a eliminar.
     * @throws ProductNotFoundComandaException si el producto no se encuentra en la comanda.
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
     * Método para obtener la cantidad de un producto en la comanda.
     * @param nomProducte Nombre del producto.
     * @return Cantidad del producto.
     * @throws ProductNotFoundComandaException si el producto no se encuentra en la comanda.
     */
    public int getQuantitat(String nomProducte) {
        Integer quantitat = ordres.get(nomProducte);
        if (quantitat == null) {
            throw new ProductNotFoundComandaException(nomProducte);
        }
        return quantitat;
    }

    public boolean conteProducte(String nomProducte) {
        return ordres.containsKey(nomProducte);
    }

    /**
     * Método para obtener el mapa de productos y cantidades.
     * @return Mapa con los productos y sus cantidades.
     */
    public Map<String, Integer> getOrdres() {
        return new HashMap<>(ordres); // Retorna una copia para evitar modificaciones externas
    }
}
