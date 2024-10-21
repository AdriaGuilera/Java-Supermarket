import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.HashSet;
import java.util.Set;
//import javafx.util.Pair;


public class Prestatgeria {
	private String id;
	private int buit;
	private int mida_prestatge;
	private int max_producte_buit;
	//private Map<String, Pair<Integer, Integer>> productes;// productes: map<nom:string, pair(posicio:int, quantitat:int)>
	private Map<String, Integer> productes;//mientras no va el pair usaré esto
	
	//private Vector<Pair<String, Boolean>> productes_fixats;
	
	public Prestatgeria(String id, int buit,int max, int mida_prestatge) {
		this.id = id;
		this.buit= buit;
		this.max_producte_buit= max;
		this.mida_prestatge=mida_prestatge;
		this.productes= new HashMap<>();
		//this.productes_fixats= new Vector<Pair<String, Boolean>>();
		
	}
	
	public String getid() {
		return id;
	}
	
	//modificar cuando vaya el pair
	public Map<String, Integer> getProductes(){
		return productes;
	}
	//modificar cuando vaya el pair
	public Set<Integer> auto_omplir(){
		Set<Integer> set = new HashSet<>();
		int cantidad;
		for (Map.Entry<String, Integer> entry : productes.entrySet()) {
			cantidad = entry.getValue();
			if( cantidad < max_producte_buit) {
				set.add(cantidad);
			}
		}
		return set;
		
	}
	public void afegir_prestatge() {
		this.buit += mida_prestatge;
	}
	public void eliminar_prestatge() {
		this.buit -= mida_prestatge;
	}
	
public void incrementar_quantitat(String id, Integer quantitat) {
	productes.put(id, productes.get(id)+quantitat);
}
public void decrementar_quantitat(String id, Integer quantitat) {
	productes.put(id, productes.get(id)-quantitat);
}
}
