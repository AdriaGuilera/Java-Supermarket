package controladors;

import java.util.HashMap;
import java.util.Map;

import Exepcions.ComandaNotFoundException;
import Exepcions.QuanitatInvalidException;
import classes.Comanda;
import Exepcions.ProductNotFoundComandaException;

/**
 * Clase controladora para gestionar las comandas.
 * Proporciona métodos para crear, eliminar, consultar y modificar comandas.
 */
public class CtrlComandes {

    public Map<String, Comanda> comandesCreades; // Mapa que almacena las comandas creadas

    /**
     * Constructor de la clase CtrlComandes.
     * Inicializa un mapa vacío para almacenar las comandas.
     */
    public CtrlComandes() {
        this.comandesCreades = new HashMap<>();
    }

    /**
     * Crea una nueva comanda.
     *
     * @param nomComanda Nombre de la comanda a crear.
     * @throws IllegalArgumentException Si ya existe una comanda con ese nombre.
     */
    public void crearComanda(String nomComanda) {
        if (comandesCreades.containsKey(nomComanda)) {
            throw new IllegalArgumentException("Ja existeix una comanda amb aquest nom.");
        }
        comandesCreades.put(nomComanda, new Comanda(nomComanda));
    }


    /**
     * Crea una comanda automática con productos especificados.
     *
     * @param nomComanda         Nombre de la comanda a crear.
     * @param productosFaltantes Mapa de productos y sus cantidades a añadir a la comanda.
     */
    public void crearComandaAutomatica(String nomComanda, Map<String, Integer> productosFaltantes) {
        crearComanda(nomComanda);
        for (Map.Entry<String, Integer> entry : productosFaltantes.entrySet()) {
            afegirProducteComanda(nomComanda, entry.getKey(), entry.getValue());
        }
    }

    /**
     * Elimina una comanda existente.
     *
     * @param nomComanda Nombre de la comanda a eliminar.
     * @throws ComandaNotFoundException Si la comanda no existe.
     */
    public void eliminarComanda(String nomComanda) throws IllegalArgumentException {
        comandesCreades.remove(nomComanda);

    }

    /**
     * Añade un producto a una comanda existente.
     *
     * @param nomComanda Nombre de la comanda.
     * @param nomProducte Nombre del producto a añadir.
     * @param quantitat Cantidad del producto.
     * @throws QuanitatInvalidException Si la cantidad es inválida.
     * @throws ComandaNotFoundException Si la comanda no existe.
     */
    public void afegirProducteComanda(String nomComanda, String nomProducte, int quantitat)
            throws QuanitatInvalidException, ComandaNotFoundException {
        Comanda comanda = comandesCreades.get(nomComanda);
        if (comanda == null) {
            throw new ComandaNotFoundException(nomComanda);
        }
        comanda.afegirProducte(nomProducte, quantitat);
    }

    public void eliminarProducteComandes(String nomProducte){
        for (Comanda comanda : comandesCreades.values()) {
            comanda.eliminarProducteSenseExcepcio(nomProducte);
        }
    }

    /**
     * Elimina un producto de una comanda existente.
     *
     * @param nomComanda Nombre de la comanda.
     * @param nomProducte Nombre del producto a eliminar.
     * @param quantitat Cantidad a eliminar.
     * @throws ProductNotFoundComandaException Si el producto no se encuentra en la comanda.
     * @throws ComandaNotFoundException Si la comanda no existe.
     */
    public void eliminarProducteComanda(String nomComanda, String nomProducte, int quantitat)
            throws ProductNotFoundComandaException, ComandaNotFoundException {
        Comanda comanda = comandesCreades.get(nomComanda);
        if (comanda == null) {
            throw new ComandaNotFoundException(nomComanda);
        }
        comanda.eliminarProducte(nomProducte, quantitat);
    }

    /**
     * Obtiene un mapa de comandas a partir de un array de nombres.
     *
     * @param nomsComandes Array con los nombres de las comandas a obtener.
     * @return Un mapa con las comandas encontradas.
     * @throws ComandaNotFoundException Si alguna comanda no se encuentra.
     */
    public Map<String, Comanda> obtenirComandes(String[] nomsComandes) throws ComandaNotFoundException {
        Map<String, Comanda> comandesAExecutar = new HashMap<>();
        for (String nomComanda : nomsComandes) {
            Comanda comanda = comandesCreades.get(nomComanda);
            if (comanda != null) {
                comandesAExecutar.put(nomComanda, comanda);
            } else {
                throw new ComandaNotFoundException(nomComanda);
            }
        }
        return comandesAExecutar;
    }

    /**
     * Obtiene una comanda específica por su nombre.
     *
     * @param nomComanda Nombre de la comanda a obtener.
     * @return La comanda encontrada.
     * @throws ComandaNotFoundException Si la comanda no existe.
     */
    public Comanda getComandaUnica(String nomComanda) throws ComandaNotFoundException {
        if (comandesCreades.containsKey(nomComanda)) {
            return comandesCreades.get(nomComanda);
        } else {
            throw new ComandaNotFoundException(nomComanda);
        }
    }

    /**
     * Obtiene todas las comandas creadas.
     *
     * @return Un mapa con todas las comandas creadas.
     */
    public Map<String, Comanda> getComandes() {
        return new HashMap<>(comandesCreades); // Retorna una copia para evitar modificaciones externas
    }

    /**
     * Verifica si existe una comanda con un nombre específico.
     *
     * @param nomComanda Nombre de la comanda a verificar.
     * @return {@code true} si la comanda existe, {@code false} en caso contrario.
     */
    public boolean existeixComanda(String nomComanda) {
        return comandesCreades.containsKey(nomComanda);
    }


    public void cargarComanda(Comanda comanda) {
        comandesCreades.put(comanda.getNom(), comanda);
    }


}


