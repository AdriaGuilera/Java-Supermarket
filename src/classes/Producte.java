package classes;

import Exepcions.MaxMagatzemWarning;
import Exepcions.QuanitatInvalidException;
import Exepcions.StockTooBigException;
import Exepcions.ZeroStockMagatzemWarning;

import java.util.Map;
import java.util.HashMap;

import static java.lang.Integer.max;

/**
 * Clase que representa un producto con su información asociada, como nombre, stock, y valores de similitud con otros productos.
 */
public class Producte {

    private String nom; // Nombre del producto
    private int maxHueco; // Capacidad máxima por hueco
    private int maxMagatzem; // Capacidad máxima en el almacén
    private Map<String, Float> similitud = new HashMap<>(); // Mapa de similitudes con otros productos
    private int stockMagatzem; // Cantidad actual en el almacén

    /**
     * Constructor de la clase Producte.
     *
     * @param nom       Nombre del producto.
     * @param maxHueco  Capacidad máxima por hueco.
     * @param maxMagatzem Capacidad máxima en el almacén.
     * @param stock     Stock inicial del producto.
     * @throws QuanitatInvalidException Si el stock inicial es negativo.
     * @throws StockTooBigException     Si el stock inicial excede la capacidad máxima del almacén.
     * @throws IllegalArgumentException Si los valores de capacidad son no positivos.
     */
    public Producte(String nom, int maxHueco, int maxMagatzem, int stock)
            throws QuanitatInvalidException, StockTooBigException, IllegalArgumentException {
        if (maxHueco <= 0 || maxMagatzem <= 0) {
            throw new IllegalArgumentException("Mida de magatzem i mida del hueco han de ser positius");
        }
        if (stock < 0) {
            throw new QuanitatInvalidException(0);
        }
        if (stock > maxMagatzem) {
            throw new StockTooBigException(maxMagatzem);
        }

        this.nom = nom;
        this.maxHueco = maxHueco;
        this.maxMagatzem = maxMagatzem;
        this.similitud = new HashMap<>(); // Si un producto no aparece en el mapa, su similitud es 0.
        this.stockMagatzem = stock;
    }

    ////////////////////// Getters //////////////////////

    /**
     * Obtiene el nombre del producto.
     *
     * @return El nombre del producto.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Obtiene la capacidad máxima por hueco.
     *
     * @return La capacidad máxima por hueco.
     */
    public int getMaxHueco() {
        return maxHueco;
    }

    /**
     * Obtiene la capacidad máxima del almacén.
     *
     * @return La capacidad máxima del almacén.
     */
    public int getMaxMagatzem() {
        return maxMagatzem;
    }

    /**
     * Obtiene el stock actual del producto en el almacén.
     *
     * @return El stock actual.
     */
    public int getStock() {
        return stockMagatzem;
    }

    /**
     * Obtiene la similitud del producto actual con otro producto dado.
     *
     * @param nomProducte Nombre del otro producto.
     * @return El valor de similitud, o 0 si no existe en el mapa.
     */
    public float getSimilitud(String nomProducte) {
        return similitud.getOrDefault(nomProducte, 0f);
    }

    ////////////////////// Modificadores de Stock //////////////////////

    /**
     * Modifica el stock actual del producto.
     *
     * @param nouStock Nuevo valor de stock.
     */
    public void modStock(int nouStock) {
        stockMagatzem = nouStock;
    }

    /**
     * Incrementa el stock del producto en una cantidad específica.
     *
     * @param quantitat Cantidad a incrementar.
     * @throws QuanitatInvalidException Si la cantidad es negativa.
     */
    public void incrementarStock(int quantitat) throws QuanitatInvalidException {
        if (quantitat < 0) {
            throw new QuanitatInvalidException(0);
        }
        if (stockMagatzem + quantitat > maxMagatzem) {
            stockMagatzem = maxMagatzem;
        } else {
            stockMagatzem += quantitat;
        }
    }

    /**
     * Decrementa el stock del producto en una cantidad específica.
     *
     * @param quant Cantidad a decrementar.
     * @throws QuanitatInvalidException Si la cantidad es negativa.
     */
    public void decrementarStock(int quant) throws QuanitatInvalidException {
        if (quant < 0) {
            throw new QuanitatInvalidException(0);
        }
        stockMagatzem = max(stockMagatzem - quant, 0);
        if (stockMagatzem == 0) {
            throw  new ZeroStockMagatzemWarning(nom);
        }
    }

    ////////////////////// Gestión de Similitud //////////////////////

    /**
     * Asigna un valor de similitud con otro producto. Si ya existe una similitud, esta se sobreescribe.
     *
     * @param nom   Nombre del producto con el que se establece la similitud.
     * @param valor Valor de similitud.
     */
    public void afegirSimilitud(String nom, float valor) {
        similitud.put(nom, valor);
    }

    /**
     * Elimina la relación de similitud con otro producto.
     *
     * @param nom Nombre del producto cuya similitud se elimina.
     */
    public void eliminarSimilitud(String nom) {
        similitud.remove(nom);
    }
}
