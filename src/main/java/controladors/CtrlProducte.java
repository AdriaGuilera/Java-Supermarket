package controladors;

import Exepcions.*;
import classes.Comanda;
import classes.Producte;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Clase controladora para gestionar los productos en el almacén.
 * Proporciona métodos para agregar, eliminar, modificar y consultar productos.
 */
public class CtrlProducte {

    /**
     * Mapa que contiene todos los productos del almacén, incluyendo aquellos con stock igual a cero.
     */
    private static Map<String, Producte> productesMagatzem;

    /**
     * Constructor de la clase CtrlProducte.
     * Inicializa un mapa vacío para los productos.
     */
    public CtrlProducte() {
        productesMagatzem = new HashMap<>();
    }

    ////////////////////// Métodos de Gestión de Comandas //////////////////////

    /**
     * Ejecuta una lista de comandas, actualizando el stock de los productos en el almacén.
     *
     * @param comandes Mapa de comandas a ejecutar.
     */
    public void executarComandes(Map<String, Comanda> comandes) {
        for (Map.Entry<String, Comanda> comanda : comandes.entrySet()) {
            for (Map.Entry<String, Integer> ordre : comanda.getValue().getOrdres().entrySet()) {
                String nom = ordre.getKey();
                int quant = ordre.getValue();
                Producte p = productesMagatzem.get(nom);
                if (p.getStock() + quant > p.getMaxMagatzem() && p!=null) {
                    p.incrementarStock(p.getMaxMagatzem() - p.getStock());
                } else if(p!=null) {
                    p.incrementarStock(quant);
                }
            }
        }
    }

    /**
     * Genera una comanda automática para reponer el stock de productos en el almacén.
     *
     * @return Un mapa con los productos que necesitan reposición y sus cantidades.
     */
    public Map<String, Integer> generarComandaAutomatica() {
        Map<String, Integer> productosRestantes = new HashMap<>();
        productesMagatzem.forEach((key, value) -> {
            int maximo = value.getMaxMagatzem();
            int diferencia = maximo - value.getStock();
            if(diferencia>0)productosRestantes.put(key, diferencia);
        });
        return productosRestantes;
    }

    ////////////////////// Métodos de Gestión de Productos //////////////////////

    /**
     * Da de alta un nuevo producto en el almacén.
     *
     * @param nomProducte Nombre del producto.
     * @param maxHueco    Capacidad máxima por hueco.
     * @param maxMagatzem Capacidad máxima del almacén.
     * @param stockMagatzem       Stock inicial.
     * @throws ProducteAlreadyExistsException Si el producto ya existe.
     * @throws QuanitatInvalidException    Si el stock inicial es negativo.
     * @throws StockTooBigException        Si el stock inicial excede la capacidad máxima.
     * @throws IllegalArgumentException    Si las capacidades son no positivas.
     */
    public void altaProducte(String nomProducte, int maxHueco, int maxMagatzem, int stockMagatzem)
            throws ProducteAlreadyExistsException, QuanitatInvalidException, StockTooBigException, IllegalArgumentException {
        if (productesMagatzem.containsKey(nomProducte)) {
            throw new ProducteAlreadyExistsException(nomProducte);
        }
        Producte p = new Producte(nomProducte, maxHueco, maxMagatzem, stockMagatzem);
        productesMagatzem.put(nomProducte, p);
    }

    /**
     * Elimina un producto del almacén.
     *
     * @param nomProducte Nombre del producto.
     * @throws ProductNotFoundMagatzemException Si el producto no existe.
     */
    public void eliminarProducte(String nomProducte) throws ProductNotFoundMagatzemException {
        if (productesMagatzem.containsKey(nomProducte)) {
            productesMagatzem.remove(nomProducte);
        } else {
            throw new ProductNotFoundMagatzemException(nomProducte);
        }
    }

    ////////////////////// Gestión de Similitud //////////////////////

    /**
     * Asigna una relación de similitud entre dos productos.
     *
     * @param nom1  Nombre del primer producto.
     * @param nom2  Nombre del segundo producto.
     * @param value Valor de similitud.
     * @throws ProductNotFoundMagatzemException Si alguno de los productos no existe.
     * @throws calculMateixosProductesSimilitud Si ambos nombres de productos son iguales.
     */
    public void afegirSimilitud(String nom1, String nom2, float value)
            throws ProductNotFoundMagatzemException, calculMateixosProductesSimilitud {
        if (nom1.equals(nom2)) {
            throw new calculMateixosProductesSimilitud(nom1);
        }
        Producte p1 = productesMagatzem.get(nom1);
        Producte p2 = productesMagatzem.get(nom2);
        if (p1 == null) {
            throw new ProductNotFoundMagatzemException(nom1);
        }
        if (p2 == null) {
            throw new ProductNotFoundMagatzemException(nom2);
        }
        p1.afegirSimilitud(nom2, value);
        p2.afegirSimilitud(nom1, value);
    }

    /**
     * Elimina una relación de similitud entre dos productos.
     *
     * @param nom1 Nombre del primer producto.
     * @param nom2 Nombre del segundo producto.
     * @throws ProductNotFoundMagatzemException Si alguno de los productos no existe.
     * @throws calculMateixosProductesSimilitud Si ambos nombres de productos son iguales.
     */
    public void eliminarSimilitud(String nom1, String nom2)
            throws ProductNotFoundMagatzemException, calculMateixosProductesSimilitud {
        if (nom1.equals(nom2)) {
            throw new calculMateixosProductesSimilitud(nom1);
        }
        Producte p1 = productesMagatzem.get(nom1);
        Producte p2 = productesMagatzem.get(nom2);
        if (p1 == null) {
            throw new ProductNotFoundMagatzemException(nom1);
        }
        if (p2 == null) {
            throw new ProductNotFoundMagatzemException(nom2);
        }
        p1.eliminarSimilitud(nom2);
        p2.eliminarSimilitud(nom1);
    }

    ////////////////////// Gestión de Stock //////////////////////

    /**
     * Decrementa el stock de un producto en una cantidad específica.
     *
     * @param nomProducte Nombre del producto.
     * @param quantitat   Cantidad a decrementar.
     * @throws ProductNotFoundMagatzemException Si el producto no existe.
     * @throws QuanitatInvalidException         Si la cantidad es negativa.
     */
    public void decrementarStock(String nomProducte, int quantitat)
            throws ProductNotFoundMagatzemException, QuanitatInvalidException {
        if (!productesMagatzem.containsKey(nomProducte)) {
            throw new ProductNotFoundMagatzemException(nomProducte);
        }
        if (quantitat < 0) {
            throw new QuanitatInvalidException(0);
        }
        Producte p = productesMagatzem.get(nomProducte);
        p.decrementarStock(quantitat);
    }

    /**
     * Incrementa el stock de un producto en una cantidad específica.
     *
     * @param nomProducte Nombre del producto.
     * @param quantitat   Cantidad a incrementar.
     * @throws ProductNotFoundMagatzemException Si el producto no existe.
     */
    public void incrementarStock(String nomProducte, int quantitat) throws ProductNotFoundMagatzemException {
        if (!productesMagatzem.containsKey(nomProducte)) {
            throw new ProductNotFoundMagatzemException(nomProducte);
        }
        Producte p = productesMagatzem.get(nomProducte);
        int stock = p.getStock();
        int max = p.getMaxMagatzem();
        if (stock + quantitat > max) {
            p.modStock(max);
        } else {
            p.incrementarStock(quantitat);
        }
    }

    ////////////////////// Getters //////////////////////

    /**
     * Verifica si existe un producto en el almacén.
     *
     * @param nom Nombre del producto.
     * @return {@code true} si el producto existe, {@code false} en caso contrario.
     */
    public boolean existeixProducte(String nom) {
        return productesMagatzem.containsKey(nom);
    }

    /**
     * Obtiene la similitud entre dos productos.
     *
     * @param nom1 Nombre del primer producto.
     * @param nom2 Nombre del segundo producto.
     * @return El valor de similitud entre los dos productos.
     */
    public double getSimilitud(String nom1, String nom2) {
        if (nom1 == null || nom2 == null) {
            return 0;
        }
        return productesMagatzem.get(nom1).getSimilitud(nom2);
    }

    /**
     * Obtiene la capacidad máxima por hueco de un producto.
     *
     * @param nom Nombre del producto.
     * @return La capacidad máxima por hueco.
     * @throws ProductNotFoundMagatzemException Si el producto no existe.
     */
    public int getMaxHueco(String nom) throws ProductNotFoundMagatzemException {
        if (!productesMagatzem.containsKey(nom)) {
            throw new ProductNotFoundMagatzemException(nom);
        }
        return productesMagatzem.get(nom).getMaxHueco();
    }

    /**
     * Obtiene el stock actual de un producto en el almacén.
     *
     * @param key Nombre del producto.
     * @return El stock actual.
     * @throws ProductNotFoundMagatzemException Si el producto no existe.
     */
    public int getStockMagatzem(String key) throws ProductNotFoundMagatzemException {
        Producte producte = productesMagatzem.get(key);
        if (producte == null) {
            throw new ProductNotFoundMagatzemException(key);
        }
        return producte.getStock();
    }

    /**
     * Obtiene un producto específico del almacén.
     *
     * @param nom Nombre del producto.
     * @return El producto encontrado.
     * @throws ProductNotFoundMagatzemException Si el producto no existe.
     */
    public Producte getProducte(String nom) throws ProductNotFoundMagatzemException {
        if (!productesMagatzem.containsKey(nom)) {
            throw new ProductNotFoundMagatzemException(nom);
        }
        return productesMagatzem.get(nom);
    }

    /**
     * Obtiene todos los productos del almacén.
     *
     * @return Un mapa con todos los productos.
     */
    public Map<String, Producte> getMagatzem() {
        return productesMagatzem;
    }


    public void carregarProducte(Producte producte) {
        productesMagatzem.put(producte.getNom(), producte);
    }

    public Set<String> getComandes(String nomProducte){
        System.out.println("hola");
        System.out.println(productesMagatzem.get(nomProducte));
        return  productesMagatzem.get(nomProducte).getComandes();
    }
    public void afegirComandaProducte(String nomProducte, String nomComanda){
        productesMagatzem.get(nomProducte).afegirComanda(nomComanda);
    }
    public void eliminarComandaProducte(String nomProducte, String nomComanda){
        productesMagatzem.get(nomProducte).eliminarComanda(nomComanda);
    }

}
