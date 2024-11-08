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
            boolean found = false;
            int i = 0;
            while (i < pairs.size() && !found) {
                Pair<String, Integer> pair = pairs.get(i);
                if (pair.getKey().equals(id_prestatgeria)) {
                    if (pair.getValue() > quantitat) {
                        pair.setValue(pair.getValue() - quantitat);
                    } else {
                        System.out.println("Error: quantitat a retirar major que la quantitat a la caixa.");
                    }
                    found = true;
                    if(pair.getValue() == 0){
                        pairs.remove(pair);
                    }
                }
                i++;
            }
            if (!found) {
                System.out.println("Error: Prestatgeria del producte erronea.");
            }
        } else {
        System.out.println("Error: Producte no esta a Caixa.");
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

    public void imprimir_ticket_per_prestatgeries(){
        for (Map.Entry<String, Vector<Pair<String, Integer>>> entry : productes.entrySet()) {
            String key = entry.getKey();
            Vector<Pair<String, Integer>> value = entry.getValue();
            System.out.println(key + ":");
            for (Pair<String, Integer> pair : value) {
                System.out.println("   Prestatgeria: " + pair.getKey());
                System.out.println("   Quantitat: " + pair.getValue());
            }
        }
    }
    //Imprimeix Nom producte: quantitat toal
    public void imprimirticket(){
        for (Map.Entry<String, Vector<Pair<String, Integer>>> entry : productes.entrySet()) {
            String producte = entry.getKey();
            Vector<Pair<String, Integer>> value = entry.getValue();
            int total = 0;
            for (Pair<String, Integer> pair : value) {
                total += pair.getValue();
            }
            System.out.println(producte + ": " + total);
        }

    }



    public void pagar(){
        productes.clear();
    }
}
