import java.util.Map;
import java.util.HashMap;

public class CtrlPrestatgeria {
	public Map<String, Prestatgeria> prestatgeries;
	
	public CtrlPrestatgeria() {
	 prestatgeries = new HashMap<>();
	}

    public  String afegirPrestatgeria(String id, int buits,int max, int mida_prestatge) {
        if (id == null || id.isEmpty()) {
            return "Error: El nom de la prestatgeria no pot estar buit.";
        }
        if (prestatgeries.containsKey(id)) {
            return "Error: Ja existeix una prestatgeria amb aquest idedntificador.";
        }
        prestatgeries.put(id, new Prestatgeria(id,buits,max,mida_prestatge));
        return "Prestatgeria afegida correctament";
    }
    public  String eliminarPrestatgeria(String id) {
        if (id == null || id.isEmpty()) {
            return "Error: El nom de la prestatgeria no pot estar buit.";
        }
        if (!prestatgeries.containsKey(id)) {
            return "Error: No existeix una prestatgeria amb aquest idedntificador.";
        }
        prestatgeries.remove(id);
        return "Prestatgeria eliminada correctament";
    }

    public  String fixarProducte(String id, String nomP) {
        if (id == null || id.isEmpty()) {
            return "Error: El nom de la prestatgeria no pot estar buit.";
        }
        if(!prestatgeries.containsKey(id)) {
            return "Error: No existeix una prestatgeria amb aquest identificador.";
        }

        prestatgeries.get(id).fixar_producte_prestatgeria(nomP);
        return "Producte fixat.";

    }

    public String desfixarProducte(String id, String nomP) {
        if (id == null || id.isEmpty()) {
            return "Error: El nom de la prestatgeria no pot estar buit.";
        }
        if(!prestatgeries.containsKey(id)) {
            return "Error: No existeix una prestatgeria amb aquest identificador.";
        }
        Prestatgeria pr = prestatgeries.get(id);
        if(pr.esta_a_prestatgeria(nomP)) {
            pr.fixar_producte_prestatgeria(nomP);
            return "Producte fixat.";
        }
        return "El producte no està en la prestatgeria amb l'identificador introduït.";

    }

    public String afegir_prestatge(String id) {
        if (id == null || id.isEmpty()) {
            return "Error: El nom de la prestatgeria no pot estar buit.";
        }
        if(!prestatgeries.containsKey(id)) {
            return "Error: No existeix una prestatgeria amb aquest identificador.";
        }
        Prestatgeria pr = prestatgeries.get(id);
        pr.afegir_prestatge();
        return "S'ha afegit un prestatge";
    }

    //Mou un producte del magatzem a la prestatgeria
    public int moureProducte(String nom, int quantitat, String id_prest, int max_hueco) {
        Prestatgeria pr = prestatgeries.get(id_prest);
        if (pr.getProductesSize() >= buits) return -1;
        boolean ja_hi_es = pr.esta_a_prestatgeria(nom);
        if (ja_hi_es) {
            int q = pr.get_quantProducte(nom);
            if (q + quant > max_hueco) return -1;
            pr.incrementar_quantitat(nom, quantitat);
        }
        else pr.afegir_producte(nom, quantitat);
        return 0;
    }
    public boolean contains_producte(String nom_producte, int quantiat, String id_prestatgeria, int quantitat_ja_afegida) {
        Prestatgeria pr = prestatgeries.get(id_prestatgeria);
        return pr.esta_a_prestatgeria(nom_producte) && ((pr.get_quantProducte(nom_producte)-quantitat_ja_afegida) >= quantiat);
    }
}
