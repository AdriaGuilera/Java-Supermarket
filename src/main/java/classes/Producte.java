package classes;

import Exepcions.QuanitatInvalidException;
import Exepcions.StockTooBigException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import static java.lang.Integer.max;

/**
 * Classe que representa un producte dins el sistema. Conté informació sobre el nom,
 * la capacitat màxima de prestatgeria, la capacitat màxima de magatzem, l'stock actual,
 * les similituds amb altres productes, així com la presència en comandes i prestatgeries.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Producte {

    /**
     * Nom del producte.
     */
    @JsonProperty("nom")
    private String nom;

    /**
     * Capacitat màxima per cada "hueco" (posició) que pot tenir el producte a la prestatgeria.
     */
    @JsonProperty("maxHueco")
    private int maxHueco;

    /**
     * Capacitat màxima que pot allotjar el magatzem per a aquest producte.
     */
    @JsonProperty("maxMagatzem")
    private int maxMagatzem;

    /**
     * Quantitat actual d'aquest producte al magatzem.
     */
    @JsonProperty("stock")
    private int stock;

    /**
     * Mapa que relaciona el nom d'un altre producte amb un valor de similitud (float).
     */
    @JsonProperty("similitud")
    private Map<String, Float> similitud = new HashMap<>();

    /**
     * Conjunt de noms de comandes on apareix aquest producte.
     */
    @JsonProperty("comandes")
    private Set<String> comandes = new HashSet<>();

    /**
     * Conjunt de noms de prestatgeries on es troba aquest producte.
     */
    @JsonProperty("prestatgeries")
    private Set<String> prestatgeries = new HashSet<>();

    /**
     * Constructor per defecte que inicialitza el producte amb valors per defecte.
     */
    public Producte() {
        this.nom = "";
        this.maxHueco = 1;
        this.maxMagatzem = 1;
        this.similitud = new HashMap<>();
        this.stock = 0;
        this.comandes = new HashSet<>();
        this.prestatgeries = new HashSet<>();
    }

    /**
     * Constructor principal de la classe Producte.
     *
     * @param nom           Nom del producte.
     * @param maxHueco      Capacitat màxima per cada "hueco" a la prestatgeria.
     * @param maxMagatzem   Capacitat màxima que pot allotjar el magatzem per a aquest producte.
     * @param stock         Quantitat inicial que tindrà el producte al magatzem.
     * @throws QuanitatInvalidException    Si {@code stock} és negatiu.
     * @throws StockTooBigException        Si {@code stock} és més gran que {@code maxMagatzem}.
     * @throws IllegalArgumentException    Si {@code maxHueco} o {@code maxMagatzem} són valors no positius.
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
        this.similitud = new HashMap<>();
        this.stock = stock;
        this.comandes = new HashSet<>();
        this.prestatgeries = new HashSet<>();
    }

    ////////////////////// Getters //////////////////////

    /**
     * Obté el nom del producte.
     *
     * @return Nom del producte.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Obté la capacitat màxima per cada "hueco" a la prestatgeria.
     *
     * @return Capacitat màxima per "hueco".
     */
    public int getMaxHueco() {
        return maxHueco;
    }

    /**
     * Obté la capacitat màxima que pot allotjar el magatzem per a aquest producte.
     *
     * @return Capacitat màxima de magatzem.
     */
    public int getMaxMagatzem() {
        return maxMagatzem;
    }

    /**
     * Obté la quantitat actual d'aquest producte al magatzem.
     *
     * @return Stock actual al magatzem.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Obté el valor de similitud associat a un altre producte.
     *
     * @param nomProducte Nom del producte a consultar.
     * @return Valor de similitud (float) o 0 si no existeix.
     */
    public float getSimilitud(String nomProducte) {
        return similitud.getOrDefault(nomProducte, 0f);
    }

    /**
     * Obté el mapa complet de similituds amb altres productes.
     *
     * @return Mapa de similituds, on la clau és el nom d'un altre producte
     *         i el valor és un float que indica la similitud.
     */
    public Map<String, Float> getSimilituds() {
        return similitud;
    }

    /**
     * Obté el conjunt de comandes on apareix aquest producte.
     *
     * @return Conjunt de noms de comandes.
     */
    public Set<String> getComandes() {
        return comandes;
    }

    /**
     * Obté el conjunt de prestatgeries on es troba aquest producte.
     *
     * @return Conjunt de noms de prestatgeries.
     */
    public Set<String> getPrestatgeries() {
        return prestatgeries;
    }


    ////////////////////// Modificadores de Stock //////////////////////

    /**
     * Modifica directament el valor de l'stock establint un nou valor.
     *
     * @param nouStock Nou valor d'stock per al producte.
     */
    public void modStock(int nouStock) {
        stock = nouStock;
    }

    /**
     * Incrementa l'stock en una quantitat donada, sense superar la capacitat màxima de magatzem.
     *
     * @param quantitat Quantitat que es vol afegir a l'stock.
     * @throws QuanitatInvalidException Si la quantitat és negativa.
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
     * Disminueix l'stock en la quantitat indicada, sense arribar a ser negatiu.
     *
     * @param quant Quantitat que es vol restar de l'stock.
     * @throws QuanitatInvalidException Si la quantitat és negativa.
     */
    public void decrementarStock(int quant) throws QuanitatInvalidException {
        if (quant < 0) {
            throw new QuanitatInvalidException(0);
        }
        stock = max(stock - quant, 0);
    }

    ////////////////////// Gestión de Similitud //////////////////////

    /**
     * Afegeix una similitud amb un altre producte.
     *
     * @param nom   Nom de l'altre producte.
     * @param valor Valor de la similitud a afegir.
     */
    public void afegirSimilitud(String nom, float valor) {
        similitud.put(nom, valor);
    }

    /**
     * Elimina la similitud existent amb un altre producte.
     *
     * @param nom Nom del producte amb el qual es vol eliminar la similitud.
     */
    public void eliminarSimilitud(String nom) {
        similitud.remove(nom);
    }
}
