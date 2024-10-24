import java.util.Map;
import java.util.HashMap;

public class CtrlPrestatgeria {
	private Map<String, Prestatgeria> prestatgeries;
	
	public CtrlPrestatgeria() {
	 prestatgeries = new HashMap<>();
	}
	
	public String afegirPrestatgeria(String id, int buits,int max, int mida_prestatge) {
		if (id == null || id.isEmpty()) {
            return "Error: El nom de la prestatgeria no pot estar buit.";
        }
		if (prestatgeries.containsKey(id)) {
            return "Error: Ja existeix una prestatgeria amb aquest idedntificador.";
        }
		prestatgeries.put(id, new Prestatgeria(id,buits,max,mida_prestatge));
		return "Prestatgeria afegida correctament";
	}
	
	public String eliminarPrestatgeria(String id) {
		 if (id == null || id.isEmpty()) {
	            return "Error: El nom de la prestatgeria no pot estar buit.";
	        }
	     if (!prestatgeries.containsKey(id)) {
	            return "Error: No existeix una prestatgeria amb aquest idedntificador.";
	        }
		prestatgeries.remove(id);
		return "Prestatgeria eliminada correctament";
	}
	
	public String fixarProducte(String id, String nomP) {
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
		prestatgeries.get(id).fixar_producte_prestatgeria(nomP);
		return "Producte fixat.";
		
	}

	//Mou un producte del magatzem a la prestatgeria
	public int moureProducte(String nom, int quantitat, String id_prest, int max_hueco) {
		Prestatgeria pr = prestatgeries.get(id_prest);
		if (pr.getProductesSize() >= buits || ) return -1;

	}
}
