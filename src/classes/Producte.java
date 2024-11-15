package classes;

import Exepcions.MaxMagatzemWarning;
import Exepcions.QuanitatInvalidException;
import Exepcions.ZeroStockMagatzemWarning;

import java.util.Map;
import java.util.HashMap;

import static java.lang.Integer.max;


public class Producte {


    private String nom;

    private int max_hueco;
    private int max_magatzem;
    private Map<String, Float> similitud = new HashMap<>();
    private int stock_magatzem;

    public Producte(String n,  int max_h, int max_m) {
        nom = n;

        max_hueco = max_h;
        max_magatzem = max_m;
        similitud = new HashMap<>(); //Si un producte no apareix en el map, s'entén que la seva similitud és 0.
        stock_magatzem = 0;
    }


    //Getters

    public String get_nom() {

        return nom;
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



    public float getSimilitud(String nom) {
        return similitud.getOrDefault(nom, 0f); // Retorna 0 si 'nom' no está en el mapa
    }


    public void mod_stock(int nou_stock) {
        stock_magatzem = nou_stock;
    }

    public void incrementar_stock(int quantitat) throws QuanitatInvalidException {
        if(quantitat < 0){
            throw new QuanitatInvalidException(0);
        }
        if(stock_magatzem + quantitat > max_magatzem) {
            stock_magatzem = max_magatzem;
        }
        else stock_magatzem += quantitat;
    }

    public void decrementar_stock(int quant) throws ZeroStockMagatzemWarning {
        stock_magatzem = max(stock_magatzem - quant, 0);
        if(stock_magatzem==0) throw new ZeroStockMagatzemWarning(nom);
    }

    //Si el Producte nom ja té similitud assignada, es sobreescriurà amb la nova
    public void afegir_similitud(String nom, float valor) {
        similitud.put(nom, valor);
    }

    public void eliminarSimilitud(String nom) {
        similitud.remove(nom);
    }



}