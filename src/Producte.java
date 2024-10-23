import java.util.Map;
import java.util.HashMap;

enum Tcategoria {

}

public class Producte {
    private String nom;
    private Tcategoria categoria;
    private float preu_compra;
    private float preu_venda;
    private int max_hueco;
    private int max_magatzem;
    private Map<String, Float> similitud;
    private int stock_magatzem;

    public Producte Producte(String n, Tcategoria cat, float pv, float pc, int mh, int mm) {
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

    public float get_preu_compra() {
        return preu_compra;
    }

    public int get_max_hueco() {
        return max_hueco;
    }

    public void mod_preu_venda(float nou_preu) {
        preu_venda = nou_preu;
    }

    public void mod_preu_compra(float nou_preu) {
        preu_compra = nou_preu;
    }

    public void mod_nom(String nou_nom) {
        nom = nou_nom;
    }

    public void mod_stock(int nou_stock) {

    }

    //Si el Producte nom ja té similitud assignada, es sobrescriurà amb la nova
    public void afegir_similitud(String nom, float valor) {
        similitud.put(nom, valor);
    }

    public void moure_a_prestatge(int quantitat, String nom, Pos posicio) {

    }

    //Això no s'hauria d'implementar a la classe Prestatgeria?
    public void moure_stock_a_magatzem(int quantitat) {

    }

    //Això no s'hauria d'implementar a la classe Prestatgeria?
    public void retirar_producte() {

    }
}