import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;



public class Prestatgeria {
	private String id;
	private int buits;
	private int mida_prestatge;
	private int max_producte_buit;
	private Map<String, Pair<Integer, Integer>> productes;// productes: map<nom:string, pair(posicio:int, quantitat:int)>
	private Map<String, Boolean> productes_fixats;
	private Vector<String> distribucio;
	
	public Prestatgeria(String id, int buits,int max, int mida_prestatge) {
		this.id = id;
		this.buits= buits;
		this.max_producte_buit= max;
		this.mida_prestatge=mida_prestatge;
		this.productes= new HashMap<>();
		this.productes_fixats= new HashMap<>();
		this.distribucio= new Vector<>(this.buits);
		
	}
	
	public void afegir_producte(String nomP, Integer quantitat) {
		if(productes.size() < buits) {
			distribucio.add(nomP);
			int pos = distribucio.indexOf(nomP);
			Pair<Integer, Integer> newPair= new Pair<>(pos, quantitat);
			productes.put(nomP, newPair);
			productes_fixats.put(nomP, false);
		}
	}
	public void eliminar_producte(String nomP) {
		distribucio.remove(nomP);
		productes.remove(nomP);
		productes_fixats.remove(nomP);
	}
	//hola
	public String getid() {
		return id;
	}
	
	
	public  Vector<String> getProductes(){
		return distribucio;
	}
	
	public Integer getProductesSize(){
		return productes.size();
	}
	
	public Set<Pair<String, Integer>> auto_reomplir(){
		Set<Pair<String, Integer>> set = new HashSet<>();
		productes.forEach((key, value) -> {
			Pair<String, Integer> cantidad =  new Pair<>(key, value.getValue());
			if( cantidad.getValue() < max_producte_buit) {
				set.add(cantidad);
			}
		});
		return set;
		
	}
	
	public int get_pos(String nomP){
		return productes.get(nomP).getKey();
	}
	
	public void afegir_prestatge() {
		this.buits += mida_prestatge;
	}
	
	public void eliminar_prestatge() {
		this.buits -= mida_prestatge;
	}
	
public void incrementar_quantitat(String nomP, Integer quantitat) {
	Pair<Integer, Integer> currentPair = productes.get(nomP);

    if (currentPair != null) { 
        int firstValue = currentPair.getKey();     
        int secondValue = currentPair.getValue();  
        if(secondValue + quantitat <= max_producte_buit) {
        	Pair<Integer, Integer> newPair = new Pair<>(firstValue, secondValue + quantitat);
        	productes.put(nomP, newPair);
        }
    }
}
public void decrementar_quantitat(String nomP, Integer quantitat) {
	Pair<Integer, Integer> currentPair = productes.get(nomP);

    if (currentPair != null) {
        int firstValue = currentPair.getKey();     
        int secondValue = currentPair.getValue();  
        if(secondValue - quantitat > 0) {
        	Pair<Integer, Integer> newPair = new Pair<>(firstValue, secondValue - quantitat);
        	productes.put(nomP, newPair);
        }
        else if(secondValue - quantitat == 0) {
        	eliminar_producte(nomP);
        	
        }
    }
}

public void fixar_producte_prestatgeria(String nomP) {
	productes_fixats.put(nomP, true);
	
}
public void desfixar_producte_prestatgeria(String nomP) {
	productes_fixats.put(nomP, false);
}

public void imprimir_productes() {
	for(String element: distribucio) {
		System.out.println(element);
		
	}
}

public boolean esta_a_prestatgeria(String nom) {
		return productes.containsKey(nom);
}

public int get_quantProducte(String nom) {
	productes.
}
}