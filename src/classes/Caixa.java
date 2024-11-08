package classes;

import java.util.Map;
import java.util.HashMap;
import java.util.Vector;

public class Caixa {
    private Map<String, Vector<Pair<String, Integer>>> productes;

    public Caixa(){
        productes = new HashMap<>();
    }

    public void afegir_producte(String nom_producte, int quantitat, String id_prestatgeria){
        Vector<Pair<String, Integer>> pairs = productes.get(nom_producte);
        boolean found = false;
        for (Pair<String, Integer> pair : pairs) {
            if (pair.getKey().equals(id_prestatgeria)) {
            pair.setValue(pair.getValue() + quantitat);
                found = true;
                break;
            }
        }
        if (!found) {
            pairs.add(new Pair<>(id_prestatgeria, quantitat));
        }

        else{
            Vector<Pair<String, Integer>> newPairs = new Vector<>();
            newPairs.add(new Pair<>(id_prestatgeria, quantitat));
            productes.put(nom_producte, newPairs);
        }

    }

    public void retirar_producte(String nom_producte, int quantitat, String id_prestatgeria){
        if(productes.containsKey(nom_producte)){
            Vector<Pair<String, Integer>> pairs = productes.get(nom_producte);
            for (Pair<String, Integer> pair : pairs) {
                if (pair.getKey().equals(id_prestatgeria)) {
                    if (pair.getValue() > quantitat) {
                        pair.setValue(pair.getValue() - quantitat);
                    } else {
                        pairs.remove(pair);
                    }
                    break;
                }
            }
            if (pairs.isEmpty()) {
                productes.remove(nom_producte);
            }
        }
    }

    public int get_quantitat(String nom_producte, String id_prestatgeria){
        if(productes.containsKey(nom_producte)){
            Vector<Pair<String, Integer>> pairs = productes.get(nom_producte);
            for (Pair<String, Integer> pair : pairs) {
                if (pair.getKey().equals(id_prestatgeria)) {
                    return pair.getValue();
                }
            }
        }
        return 0;
    }

    public void imprimir_ticket(){
        for (Map.Entry<String, Vector<Pair<String, Integer>>> entry : productes.entrySet()) {
            String key = entry.getKey();
            Vector<Pair<String, Integer>> value = entry.getValue();
            System.out.println(key + ":");
            for (Pair<String, Integer> pair : value) {
                System.out.println(pair.getKey() + " " + pair.getValue());
            }
        }
    }

    public int get_quantitat_producte(String nom, String id_prestatgeria){
        if(productes.containsKey(nom)){
            Vector<Pair<String, Integer>> pairs = productes.get(nom);
            for (Pair<String, Integer> pair : pairs) {
                if (pair.getKey().equals(id_prestatgeria)) {
                    return pair.getValue();
                }
            }
        }
        return 0;
    }

    public void pagar(){
        productes.clear();
    }
}
