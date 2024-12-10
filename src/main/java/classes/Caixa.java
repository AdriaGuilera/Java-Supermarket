package classes;

import Exepcions.ProductNotFoundCaixaException;
import Exepcions.QuanitatInvalidException;

import java.util.Map;
import java.util.HashMap;

/**
 * Representa una caixa (caja) que contiene productos y sus cantidades.
 * Permite añadir, retirar y consultar productos, además de vaciar su contenido.
 */
public class Caixa {

    private Map<String, Integer> productes; // Mapa que asocia nombre del producto con su cantidad

    /**
     * Constructor de la clase Caixa.
     * Inicializa un mapa vacío de productos.
     */
    public Caixa() {
        productes = new HashMap<>();
    }


    /**
     * Añade un producto a la caja. Si el producto ya existe, incrementa su cantidad.
     *
     * @param nomProducte Nombre del producto a añadir.
     * @param quantitat   Cantidad del producto a añadir.
     * @throws QuanitatInvalidException Si la cantidad es menor o igual a cero.
     */
    public void afegirProducte(String nomProducte, int quantitat) throws QuanitatInvalidException {
        if (quantitat <= 0) {
            throw new QuanitatInvalidException(1);
        }
        if (productes.containsKey(nomProducte)) {
            productes.put(nomProducte, productes.get(nomProducte) + quantitat);
        } else {
            productes.put(nomProducte, quantitat);
        }
    }

    /**
     * Retira una cantidad específica de un producto de la caja.
     * Si la cantidad resultante es cero, elimina el producto del mapa.
     *
     * @param nomProducte Nombre del producto a retirar.
     * @param quantitat   Cantidad a retirar.
     * @throws ProductNotFoundCaixaException Si el producto no se encuentra en la caja.
     */
    public void retirarProducte(String nomProducte, int quantitat) throws ProductNotFoundCaixaException {
        if (productes.containsKey(nomProducte)) {
            int quantitatActual = productes.get(nomProducte);
            if (quantitatActual > quantitat) {
                productes.put(nomProducte, quantitatActual - quantitat);
            } else if (quantitatActual - quantitat == 0) {
                productes.remove(nomProducte);
            }
        } else {
            throw new ProductNotFoundCaixaException(nomProducte);
        }
    }

    /**
     * Obtiene la cantidad actual de un producto en la caja.
     *
     * @param nomProducte Nombre del producto.
     * @return Cantidad del producto.
     * @throws ProductNotFoundCaixaException Si el producto no se encuentra en la caja.
     */
    public int getQuantitat(String nomProducte) throws ProductNotFoundCaixaException {
        if (productes.containsKey(nomProducte)) {
            return productes.get(nomProducte);
        } else {
            throw new ProductNotFoundCaixaException(nomProducte);
        }
    }

    /**
     * Obtiene el contenido actual de la caja como un mapa.
     * El mapa contiene los nombres de los productos como claves y las cantidades como valores.
     *
     * @return Mapa de productos y cantidades.
     */
    public Map<String, Integer> getTicket() {
        return productes;
    }

    /**
     * Limpia el contenido de la caja, eliminando todos los productos.
     */
    public void pagar() {
        productes.clear();
    }

    public boolean existexProducte(String nomProducte) {
        if (productes.containsKey(nomProducte)) {
            return true;
        }
        else return false;
    }
}
