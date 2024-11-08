package classes;

import java.util.Map;
import java.util.HashMap;



public class Producte {


    private String nom;
    private Tcategoria categoria;
    private float preu_compra;
    private float preu_venda;
    private int max_hueco;
    private int max_magatzem;
    private Map<String, Float> similitud;
    private int stock_magatzem;

    public Producte(String n, Tcategoria cat, float pv, float pc, int mh, int mm) {
        nom = n;
        categoria = cat;
        preu_compra = pc;
        preu_venda = pv;
        max_hueco = mh;
        max_magatzem = mm;
        similitud = new HashMap<>(); //Si un producte no apareix en el map, s'entén que la seva similitud és 0.
        stock_magatzem = 0;
    }

    public float get_preu_venda() {
        return preu_venda;
    }

    public Tcategoria get_categoria() {
        return categoria;
    }

    public float get_preu_compra() {
        return preu_compra;
    }

    public int get_max_hueco() {
        return max_hueco;
    }

    public int get_max_magatzem() {
        return max_magatzem;
    }

    public int get_stock() {
        return stock_magatzem;
    }

    public void imprimir_similituds() {
        similitud.forEach((key, value) -> System.out.println("Nom: " + key + ", Similitud: " + value));
    }

    public float getSimilitud(String nom) {
        return similitud.get(nom);
    }

    public void mod_preu_venda(float nou_preu) {
        preu_venda = nou_preu;
    }

    public void mod_preu_compra(float nou_preu) {
        preu_compra = nou_preu;
    }

    public void mod_cat(Tcategoria cat) {
        categoria = cat;
    }

    public void mod_nom(String nou_nom) {
        nom = nou_nom;
    }

    public void mod_mh(int mh) {
        max_hueco = mh;
    }

    public void mod_mm(int mm) {
        max_magatzem = mm;
    }

    public void mod_stock(int nou_stock) {
        stock_magatzem = nou_stock;
    }

    public void incrementar_stock(int quant) {
        stock_magatzem += quant;
    }

    public void decrementar_stock(int quant) {
        stock_magatzem -= quant;
    }

    //Si el Producte nom ja té similitud assignada, es sobreescriurà amb la nova
    public void afegir_similitud(String nom, float valor) {
        similitud.put(nom, valor);
    }

    public void moure_stock_a_magatzem(int quantitat) {

    }

    public static void retirar_producte() {

    }
}