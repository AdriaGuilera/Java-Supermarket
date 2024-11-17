package controladors;

import java.util.*;
import Exepcions.*;
import classes.Pair;
import classes.Prestatgeria;

/**
 * Controlador que gestiona un conjunto de prestatgeries. Proporciona operaciones
 * para añadir, modificar, consultar y eliminar prestatgeries y sus productos.
 */
public class CtrlPrestatgeria {

    /** Mapa que almacena las prestatgeries con sus identificadores. */
    public Map<String, classes.Prestatgeria> prestatgeries;

    /**
     * Constructor por defecto. Inicializa el mapa de prestatgeries.
     */
    public CtrlPrestatgeria() {
        prestatgeries = new HashMap<>();
    }

    /**
     * Añade una prestatgeria al sistema.
     *
     * @param id            Identificador único de la prestatgeria.
     * @param mida          Tamaño total de la prestatgeria.
     * @param midaPrestatge Tamaño de cada prestatge dentro de la prestatgeria.
     * @throws MidaPrestatgeriaInvalidException Si los tamaños son inválidos.
     * @throws PrestatgeriaJaExisteixException Si ya existe una prestatgeria con el mismo identificador.
     */
    public void afegirPrestatgeria(String id, int mida, int midaPrestatge)
            throws MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException {
        if (!prestatgeries.containsKey(id)){
            if( midaPrestatge <= 0 || mida < 0 || mida < midaPrestatge || mida % midaPrestatge != 0 ){
                throw new MidaPrestatgeriaInvalidException();
            } else {
                Prestatgeria pr = new Prestatgeria(id, mida, midaPrestatge);
                prestatgeries.put(id, pr);
            }

        } else {
            throw new PrestatgeriaJaExisteixException(id);
        }
    }

    /**
     * Obtiene los productos de una prestatgeria específica.
     *
     * @param id Identificador de la prestatgeria.
     * @return Un mapa con los nombres de productos y sus cantidades.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     */
    public Map<String, Integer> getProductesPrestatgeria(String id)
            throws PrestatgeriaNotFoundException {
        if (prestatgeries.containsKey(id)){
            return prestatgeries.get(id).getProductes();
        } else {
            throw new PrestatgeriaNotFoundException(id);
        }
    }

    /**
     * Elimina una prestatgeria del sistema y retorna sus productos.
     *
     * @param id Identificador de la prestatgeria.
     * @return Un mapa con los productos eliminados y sus cantidades.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     */
    public Map<String, Integer> eliminarPrestatgeria(String id)
            throws PrestatgeriaNotFoundException {
        if (prestatgeries.containsKey(id)){
            Prestatgeria pr = prestatgeries.get(id);
            Map<String, Integer> productes = pr.getProductes();
            prestatgeries.remove(id);
            return productes;
        } else {
            throw new PrestatgeriaNotFoundException(id);
        }
    }

    /**
     * Añade un prestatge a una prestatgeria existente.
     *
     * @param id Identificador de la prestatgeria.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     */
    public void afegirPrestatge(String id)
            throws PrestatgeriaNotFoundException {
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        } else {
            Prestatgeria pr = prestatgeries.get(id);
            pr.afegirPrestatge();
        }
    }

    /**
     * Elimina un prestatge de una prestatgeria existente.
     *
     * @param id Identificador de la prestatgeria.
     * @return Un mapa con los productos que estaban en el prestatge eliminado y sus cantidades.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     */
    public Map<String, Integer> eliminarPrestatge(String id)
            throws PrestatgeriaNotFoundException {
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        } else {
            Prestatgeria pr = prestatgeries.get(id);
            return pr.eliminarPrestatge();
        }
    }


    /**
     * Fija un producto en una prestatgeria, evitando que sea movido.
     *
     * @param id    Identificador de la prestatgeria.
     * @param nomP  Nombre del producto.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     * @throws ProductNotFoundPrestatgeriaException Si el producto no está en la prestatgeria.
     * @throws ProducteFixatException Si el producto ya está fijado.
     */
    public void fixarProducte(String id, String nomP)
            throws PrestatgeriaNotFoundException, ProductNotFoundPrestatgeriaException, ProducteFixatException {
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        }
        prestatgeries.get(id).fixarProductePrestatgeria(nomP);
    }

    /**
     * Desfija un producto de una prestatgeria.
     *
     * @param id    Identificador de la prestatgeria.
     * @param nomP  Nombre del producto.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     * @throws ProductNotFoundPrestatgeriaException Si el producto no está en la prestatgeria.
     */
    public void desfixarProducte(String id, String nomP)
            throws PrestatgeriaNotFoundException, ProductNotFoundPrestatgeriaException {

        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        }
        Prestatgeria pr = prestatgeries.get(id);
        if(pr.estaAPrestatgeria(nomP)) {
            pr.desfixarProductePrestatgeria(nomP);
        } else {
            throw new ProductNotFoundPrestatgeriaException(id, nomP);
        }
    }

    /**
     * Incrementa la cantidad de un producto en una prestatgeria.
     *
     * @param id       Identificador de la prestatgeria.
     * @param nomP     Nombre del producto.
     * @param quantitat Cantidad a incrementar.
     * @throws QuanitatInvalidException Si la cantidad es inválida.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     */
    public void incrementarQuantitatProducte(String id, String nomP, int quantitat)
            throws QuanitatInvalidException, PrestatgeriaNotFoundException {
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        } else {
            Prestatgeria pr = prestatgeries.get(id);
            pr.incrementarQuantitat(nomP, quantitat);
        }
    }

    /**
     * Retira un producto de una prestatgeria.
     *
     * @param id    Identificador de la prestatgeria.
     * @param nomP  Nombre del producto.
     * @return Un par que contiene el nombre del producto y la cantidad retirada.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     * @throws ProductNotFoundPrestatgeriaException Si el producto no está en la prestatgeria.
     */
    public Pair<String, Integer> retirarProductePrestatgeria(String id, String nomP)
            throws PrestatgeriaNotFoundException, ProductNotFoundPrestatgeriaException {
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        }
        Prestatgeria pr = prestatgeries.get(id);
        int quantitat = pr.getQuantProducte(nomP);
        pr.eliminarProducte(nomP);
        return new Pair<>(nomP, quantitat);
    }

    /**
     * Decrementa la cantidad de un producto en una prestatgeria.
     *
     * @param id        Identificador de la prestatgeria.
     * @param nomP      Nombre del producto.
     * @param quantitat Cantidad a decrementar.
     * @return La cantidad restante del producto en la prestatgeria.
     * @throws QuanitatInvalidException Si la cantidad es inválida.
     * @throws ProductNotFoundPrestatgeriaException Si el producto no está en la prestatgeria.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     */
    public int decrementarQuantitatProducte(String id, String nomP, int quantitat)
            throws QuanitatInvalidException, ProductNotFoundPrestatgeriaException, PrestatgeriaNotFoundException {
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        } else {
            Prestatgeria pr = prestatgeries.get(id);
            return pr.decrementarQuantitat(nomP, quantitat);
        }
    }



    /**
     * Añade un producto a una prestatgeria.
     *
     * @param idPrest  Identificador de la prestatgeria.
     * @param nom      Nombre del producto.
     * @param quantitat Cantidad del producto.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     * @throws JaExisteixProucteaPrestatgeriaException Si el producto ya existe en la prestatgeria.
     * @throws PrestatgeriaFullException Si la prestatgeria no tiene espacio suficiente.
     * @throws QuanitatInvalidException Si la cantidad es inválida.
     */
    public void afegirProducte(String idPrest, String nom, int quantitat)
            throws PrestatgeriaNotFoundException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException, QuanitatInvalidException {
        if(!prestatgeries.containsKey(idPrest)) {
            throw new PrestatgeriaNotFoundException(idPrest);
        } else {
            Prestatgeria pr = prestatgeries.get(idPrest);
            pr.afegirProducte(nom, quantitat);
        }
    }

    /**
     * Elimina un producto de todas las prestatgeries.
     *
     * @param nom Nombre del producto.
     */
    public void eliminarProducte(String nom) {
        for(Prestatgeria pr : prestatgeries.values()){
            pr.eliminarProducteSinRevisarSiExiste(nom);
        }
    }

    /**
     * Mueve un producto de un hueco a otro dentro de una prestatgeria.
     *
     * @param idPrestatgeria Identificador de la prestatgeria.
     * @param huecoOrigen    Índice del hueco origen.
     * @param huecoDesti     Índice del hueco destino.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     * @throws ProducteNotInHuecoException Si el producto no está en el hueco origen.
     * @throws InvalidHuecosException Si los índices de los huecos son inválidos.
     */
    public void moureProducte(String idPrestatgeria, int huecoOrigen, int huecoDesti)
            throws PrestatgeriaNotFoundException, ProducteNotInHuecoException, InvalidHuecosException {
        if(!prestatgeries.containsKey(idPrestatgeria)){
            throw new PrestatgeriaNotFoundException(idPrestatgeria);
        } else {
            Prestatgeria pr = prestatgeries.get(idPrestatgeria);
            pr.moureProducte(huecoOrigen, huecoDesti);
        }
    }


    /**
     * Comprueba si un producto existe en una prestatgeria.
     *
     * @param id          Identificador de la prestatgeria.
     * @param nomProducte Nombre del producto.
     * @return {@code true} si el producto existe en la prestatgeria, de lo contrario {@code false}.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     */
    public boolean containsProducte(String id, String nomProducte)
            throws PrestatgeriaNotFoundException {
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        } else {
            Prestatgeria pr = prestatgeries.get(id);
            return pr.estaAPrestatgeria(nomProducte);
        }
    }

    /**
     * Obtiene los nombres de todos los productos en una prestatgeria.
     *
     * @param id Identificador de la prestatgeria.
     * @return Un vector con los nombres de los productos.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     */
    public Vector<String> getNomsProductes(String id)
            throws PrestatgeriaNotFoundException {
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        } else {
            Prestatgeria pr = prestatgeries.get(id);
            return pr.getNomsProductes();
        }
    }

    /**
     * Obtiene una prestatgeria a partir de su identificador.
     *
     * @param id Identificador de la prestatgeria.
     * @return La prestatgeria correspondiente.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     */
    public Prestatgeria getPrestatgeria(String id){
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        }
        System.out.println("Consulta Prestategeria fet satisfactoriament!");
        return prestatgeries.get(id);
    }



    /**
     * Obtiene la cantidad de un producto en una prestatgeria.
     *
     * @param idPrestatgeria Identificador de la prestatgeria.
     * @param nomProducte    Nombre del producto.
     * @return La cantidad del producto en la prestatgeria.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     * @throws ProductNotFoundPrestatgeriaException Si el producto no está en la prestatgeria.
     */
    public int getQuantitatProducte(String idPrestatgeria, String nomProducte)
            throws PrestatgeriaNotFoundException, ProductNotFoundPrestatgeriaException {
        if(!prestatgeries.containsKey(idPrestatgeria)){
            throw new PrestatgeriaNotFoundException(idPrestatgeria);
        } else {
            Prestatgeria pr = prestatgeries.get(idPrestatgeria);
            return pr.getQuantProducte(nomProducte);
        }
    }

    /**
     * Configura la distribución de los productos en una prestatgeria.
     *
     * @param id    Identificador de la prestatgeria.
     * @param ordre Vector que define el nuevo orden de los productos.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     */
    public void setDistribucio(String id, Vector<String> ordre)
            throws PrestatgeriaNotFoundException {
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        } else {
            Prestatgeria pr = prestatgeries.get(id);
            pr.setDistribucio(ordre);
        }
    }

    /**
     * Obtiene los productos fijados en una prestatgeria.
     *
     * @param id Identificador de la prestatgeria.
     * @return Un conjunto con los nombres de los productos fijados.
     * @throws PrestatgeriaNotFoundException Si la prestatgeria no existe.
     */
    public Set<String> getProductesFixats(String id)
            throws PrestatgeriaNotFoundException {
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        } else {
            Prestatgeria pr = prestatgeries.get(id);
            return pr.getProductesFixats();
        }
    }




}
