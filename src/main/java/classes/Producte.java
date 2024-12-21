package classes;

import Exepcions.QuanitatInvalidException;
import Exepcions.StockTooBigException;
import Exepcions.ZeroStockMagatzemWarning;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import static java.lang.Integer.max;

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

    @JsonProperty("comandes")
    private Set<String> comandes = new HashSet<>(); // Conjunto de comandas donde aparece el producto

    @JsonProperty("prestatgeries")
    private Set<String> prestatgeries = new HashSet<>(); // Conjunto de prestatgeries donde se encuentra el producto

    // Constructor por defecto
    public Producte() {
        this.nom = "";
        this.maxHueco = 1;
        this.maxMagatzem = 1;
        this.similitud = new HashMap<>();
        this.stock = 0;
        this.comandes = new HashSet<>();
        this.prestatgeries = new HashSet<>();
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
        this.comandes = new HashSet<>();
        this.prestatgeries = new HashSet<>();
    }

    ////////////////////// Getters //////////////////////

    public String getNom() {
        return nom;
    }

    public int getMaxHueco() {
        return maxHueco;
    }

    public int getMaxMagatzem() {
        return maxMagatzem;
    }

    public int getStock() {
        return stock;
    }

    public float getSimilitud(String nomProducte) {
        return similitud.getOrDefault(nomProducte, 0f);
    }

    public Map<String, Float> getSimilituds() {
        return similitud;
    }

    public Set<String> getComandes() {
        return comandes;
    }

    public Set<String> getPrestatgeries() {
        return prestatgeries;
    }

    ////////////////////// Gestión individual de Comandes //////////////////////

    /**
     * Añade una única comanda al conjunto de comandes.
     *
     * @param comanda Nombre de la comanda.
     */
    public void afegirComanda(String comanda) {
        if (comanda != null && !comanda.isEmpty()) {
            System.out.println(comanda);
            comandes.add(comanda);
        }
    }

    /**
     * Elimina una única comanda del conjunto de comandes.
     *
     * @param comanda Nombre de la comanda a eliminar.
     */
    public void eliminarComanda(String comanda) {
        System.out.println("lala");
        System.out.println(comanda);
        comandes.remove(comanda);
    }

    ////////////////////// Gestión individual de Prestatgeries //////////////////////

    /**
     * Añade una única prestatgeria al conjunto de prestatgeries.
     *
     * @param prestatgeria Nombre de la prestatgeria.
     */
    public void addPrestatgeria(String prestatgeria) {
        if (prestatgeria != null && !prestatgeria.isEmpty()) {
            prestatgeries.add(prestatgeria);
        }
    }

    /**
     * Elimina una única prestatgeria del conjunto de prestatgeries.
     *
     * @param prestatgeria Nombre de la prestatgeria a eliminar.
     */
    public void removePrestatgeria(String prestatgeria) {
        prestatgeries.remove(prestatgeria);
    }

    ////////////////////// Modificadores de Stock //////////////////////

    public void modStock(int nouStock) {
        stock = nouStock;
    }

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

    public void decrementarStock(int quant) throws QuanitatInvalidException {
        if (quant < 0) {
            throw new QuanitatInvalidException(0);
        }
        stock = max(stock - quant, 0);
    }

    ////////////////////// Gestión de Similitud //////////////////////

    public void afegirSimilitud(String nom, float valor) {
        similitud.put(nom, valor);
    }

    public void eliminarSimilitud(String nom) {
        similitud.remove(nom);
    }
}
