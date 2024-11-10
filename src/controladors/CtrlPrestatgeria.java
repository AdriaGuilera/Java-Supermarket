package controladors;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

import classes.Pair;
import classes.Prestatgeria;
public class CtrlPrestatgeria {
	public Map<String, classes.Prestatgeria> prestatgeries;
	
	public CtrlPrestatgeria() {
	 prestatgeries = new HashMap<>();
	}

    public  String afegirPrestatgeria(String id, int mida,int max, int mida_prestatge) {
        if (id == null || id.isEmpty()) return "Error: El nom de la prestatgeria no pot estar buit.";
        
        if (prestatgeries.containsKey(id)) return "Error: Ja existeix una prestatgeria amb aquest idedntificador.";
        
        prestatgeries.put(id, new Prestatgeria(id, mida,max,mida_prestatge));
        return "Prestatgeria afegida correctament";
    }
    public  String eliminarPrestatgeria(String id) {
        if (id == null || id.isEmpty()) return "Error: El nom de la prestatgeria no pot estar buit.";
        
        if (!prestatgeries.containsKey(id)) return "Error: No existeix una prestatgeria amb aquest idedntificador.";
        
        prestatgeries.remove(id);
        return "Prestatgeria eliminada correctament";
    }

    public  String fixarProducte(String id, String nomP) {
        if (id == null || id.isEmpty()) return "Error: El nom de la prestatgeria no pot estar buit.";
        
        if(!prestatgeries.containsKey(id)) return "Error: No existeix una prestatgeria amb aquest identificador.";
        

        prestatgeries.get(id).fixar_producte_prestatgeria(nomP);
        return "Producte fixat.";

    }

    public String desfixarProducte(String id, String nomP) {
        if (id == null || id.isEmpty()) return "Error: El nom de la prestatgeria no pot estar buit.";
        
        if(!prestatgeries.containsKey(id)) return "Error: No existeix una prestatgeria amb aquest identificador.";
        
        Prestatgeria pr = prestatgeries.get(id);
        if(pr.esta_a_prestatgeria(nomP)) {
            pr.desfixar_producte_prestatgeria(nomP);
            return "Producte desfixat.";
        }
        return "El producte no està en la prestatgeria amb l'identificador introduït.";

    }
    //funcion auxiliar para reponer una prestatgeria (se usara en una función del ctrl de domini)
    public Set<Pair<String, Integer>> reposarPrestatgeria(String id) {
    	if (id == null || id.isEmpty()) return new HashSet<>();
    	
        if(!prestatgeries.containsKey(id)) return new HashSet<>();//en la función que llame a esta función se tendrá que mirar que no devuelva vacío
        
        Prestatgeria pr = prestatgeries.get(id);
        return pr.auto_reomplir();
    }
    //función auxiliar que elimina el producto de prestatgeria y devuelve el nombre y la cantidad que habrá que aumentar en el almacén
    public Pair<String, Integer> retirarProductePrestatgeria(String id, String nomP){
    	if (id == null || id.isEmpty()) return new Pair<>();
    	
        if(!prestatgeries.containsKey(id)) return new Pair<>();
        
        Prestatgeria pr = prestatgeries.get(id);
        if(!pr.esta_a_prestatgeria(nomP)) return new Pair<>();
        
        int quantitat = pr.get_quantProducte(nomP);
        pr.eliminar_producte(nomP);
        return new Pair<>(nomP, quantitat);
    }
    
    public void decrementar_quantitat_producte(String id, String nomP, int quantitat){
        	if (id == null || id.isEmpty()) return;

            if(!prestatgeries.containsKey(id)) return;

            Prestatgeria pr = prestatgeries.get(id);
            if(!pr.esta_a_prestatgeria(nomP)) return;

            pr.decrementar_quantitat(nomP, quantitat);
    }
    
    public String afegir_prestatgeria(String id) {
        if (id == null || id.isEmpty()) return "Error: El nom de la prestatgeria no pot estar buit.";
        
        if(!prestatgeries.containsKey(id)) return "Error: No existeix una prestatgeria amb aquest identificador.";
        
        Prestatgeria pr = prestatgeries.get(id);
        pr.afegir_prestatge();
        return "S'ha afegit un prestatge";
    }
    
    public String eliminar_prestatgeria(String id) {
        if (id == null || id.isEmpty()) return "Error: El nom de la prestatgeria no pot estar buit.";
        
        if(!prestatgeries.containsKey(id)) return "Error: No existeix una prestatgeria amb aquest identificador.";
        
        Prestatgeria pr = prestatgeries.get(id);
        pr.eliminar_prestatge();
        return "S'ha eliminat un prestatge";
    }

    //Mou un producte del magatzem a la prestatgeria
    public void moureProducte(String nom, int quantitat, String id_prest, int max_hueco) {
        Prestatgeria pr = prestatgeries.get(id_prest);
        int mida_prestatgeria = pr.getMidaPrestatgeria();
        if (pr.getProductesSize() >= mida_prestatgeria) return;
        boolean ja_hi_es = pr.esta_a_prestatgeria(nom);
        if (ja_hi_es) {
            int q = pr.get_quantProducte(nom);
            if (q + quantitat > max_hueco) return;
            pr.incrementar_quantitat(nom, quantitat);
        }
        else pr.afegir_producte(nom, quantitat);
    }
    public boolean contains_producte(String nom_producte, int quantiat, String id_prestatgeria, int quantitat_ja_afegida) {
        Prestatgeria pr = prestatgeries.get(id_prestatgeria);
        return pr.esta_a_prestatgeria(nom_producte) && ((pr.get_quantProducte(nom_producte)-quantitat_ja_afegida) >= quantiat);
    }


}
