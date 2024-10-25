import javax.swing.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;

public class Caixa {

    private double valor;
    private Map<String, Vector<Pair<String, Integer>>> productes;

    public Caixa(){
        valor = 0.0;
        productes = new HashMap<>();
    }

    public void afegir_producte(String nom_producte, double preu, int quantitat, String id_prestatgeria){
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
            valor += preu*quantitat;
        }

        else{
            Vector<Pair<String, Integer>> newPairs = new Vector<>();
            newPairs.add(new Pair<>(id_prestatgeria, quantitat));
            productes.put(nom_producte, newPairs);
            valor += preu * quantitat;
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
    public void pagar(){
        valor = 0.0;
        productes.clear();

    }
}
