package classes;

import Exepcions.MaxMagatzemWarning;
import Exepcions.QuanitatInvalidException;
import Exepcions.StockTooBigException;
import Exepcions.ZeroStockMagatzemWarning;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.HashMap;

import static java.lang.Integer.max;

/**
 * Clase que representa un producto con su información asociada, como nombre, stock, y valores de similitud con otros productos.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Producte {
    @JsonProperty("nom")
    private String nom; // Nombre del producto
    @JsonProperty("maxHueco")
    private int maxHueco; // Capacidad máxima por hueco
    @JsonProperty("maxMagatzem")
    private int maxMagatzem; // Capacidad máxima en el almacén
    @JsonProperty("stock")
    private int stock; // Cantidad actual en el almacén
    @JsonProperty("similitud")
    private Map<String, Float> similitud = new HashMap<>(); // Mapa de similitudes con otros productos




    // Constructor por defecto
    public Producte() {
        this.nom = "";
        this.maxHueco = 1;
        this.maxMagatzem = 1;
        this.similitud = new HashMap<>();
        this.stock = 0;
    }

    // Constructor principal
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
        this.similitud = new HashMap<>();
        this.stock = stock;
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
        return stock;
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

    public Map<String,Float> getSimilituds() {
        return similitud;
    }

    ////////////////////// Modificadores de Stock //////////////////////

    /**
     * Modifica el stock actual del producto.
     *
     * @param nouStock Nuevo valor de stock.
     */
    public void modStock(int nouStock) {
        stock = nouStock;
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
        if (stock + quantitat > maxMagatzem) {
            stock = maxMagatzem;
        } else {
            stock += quantitat;
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
        stock = max(stock - quant, 0);
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
