package classes;

import java.util.HashMap;
import java.util.Map;

public class Comanda {

    private String nom; // Nombre de la comanda
    private Map<String, Integer> ordres; // Mapa de productos y cantidades

    // Constructor
    public Comanda(String nom) {
        this.nom = nom;
        this.ordres = new HashMap<>();
    }

    // Getter para obtener el nombre de la comanda
    public String getNom() {
        return nom;
    }

    // Método para añadir un producto a la comanda
    public void afegirProducte(String nomProducte, int quantitat) {
        ordres.put(nomProducte, quantitat);
    }

    // Método para obtener el mapa de productos y cantidades
    public Map<String, Integer> getOrdres() {
        return ordres;
    }

    // Método para eliminar un producto de la comanda
    public void eliminarProducte(String nomProducte) {
        ordres.remove(nomProducte);
    }

    // Método para obtener la cantidad de un producto específico
    public int obtenirQuantitatProducte(String nomProducte) {
        return ordres.getOrDefault(nomProducte, 0);
    }
}
