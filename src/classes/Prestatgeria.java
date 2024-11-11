package classes;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;



public class Prestatgeria {
	private String id;
	private int mida_prestatgeria;
	private int mida_prestatge;
	private Map<String, Integer> productes; //nom, quantitat
	private Map<Integer, String> posicions; //posicio, nom
	private Set<String> productes_fixats;
	
	public Set<String> getProductesFixats(){
		return productes_fixats;
	}
	
	public Prestatgeria(String id, int buits, int mida_prestatge) {
		this.id = id;
		this.mida_prestatgeria= buits;
		this.mida_prestatge=mida_prestatge;
		this.productes= new HashMap<>();
		this.posicions = new HashMap<>();
		this.productes_fixats= new HashSet<>();
		
	}
	
	public void afegir_producte(String nomP, Integer quantitat) {
		if(!productes.containsKey(nomP)) {
			productes.put(nomP, quantitat);
			int pos = 0;
			while(posicions.containsKey(pos)) {
				pos++;
			}
			posicions.put(pos, nomP);
		}
		else {
			incrementar_quantitat(nomP, quantitat);
		}
	}
	public void eliminar_producte(String nomP) {
		if(productes.containsKey(nomP)){
			productes.remove(nomP);
			productes_fixats.remove(nomP);
			int pos = get_pos(nomP);
			posicions.remove(pos);
		}
	}
	
	public String getid() {
		return id;
	}

	public int getMidaPrestatgeria() {
		return mida_prestatgeria;
	}
	
	public  Vector<String> getNomsProductes(){
		return new Vector<>(productes.keySet());
	}

	public int getProductesSize(){
		return productes.size();
	}

	public Map<String,Integer> get_productes(){
		return productes;
	}

	
	public int get_pos(String nomP){
		for(Map.Entry<Integer, String> entry: posicions.entrySet()) {
			if(entry.getValue().equals(nomP)) {
				return entry.getKey();
			}
		}
		return -1;
	}
	
	public void afegir_prestatge() {
		this.mida_prestatgeria += mida_prestatge;
	}


	public Map<String,Integer> eliminar_prestatge() {
		Map<String,Integer> productes_eliminats = new HashMap<>();
		 for(int i = mida_prestatgeria - mida_prestatge; i<mida_prestatgeria; i++) {
			 if(posicions.containsKey(i)) {
				 String nomP = posicions.get(i);
				 productes_eliminats.put(nomP, productes.get(nomP));

				 productes.remove(nomP);
				 productes_fixats.remove(nomP);
				 posicions.remove(i);
			 }
		 }
		 mida_prestatgeria -= mida_prestatge;
		 return productes_eliminats;
	}
	
public void incrementar_quantitat(String nomP, Integer quantitat) {
	if(productes.containsKey(nomP)) {
        productes.compute(nomP, (k, currentQuantitat) -> currentQuantitat + quantitat);
	}
}
public void decrementar_quantitat(String nomP, Integer quantitat) {
	if(productes.containsKey(nomP)) {
		int quantitat_actual = productes.get(nomP);
		if(quantitat_actual - quantitat >= 0) {
			productes.compute(nomP, (k, currentQuantitat) -> currentQuantitat - quantitat);
		}
	}
}

public void fixar_producte_prestatgeria(String nomP) {
	productes_fixats.add(nomP);
	
}
public void desfixar_producte_prestatgeria(String nomP) {
	productes_fixats.remove(nomP);
}

public void imprimir_productes() {
	for(Map.Entry<Integer, String> entry: posicions.entrySet()) {
		String nomP = entry.getValue();
		Integer quantitat = productes.get(nomP);
		Integer posicio = entry.getKey();
		Boolean fixat = productes_fixats.contains(nomP);
		System.out.println("Posició: " + posicio + " " + nomP + " " + quantitat + " " + "Fixat : " + fixat.toString());
	}
}

public boolean esta_a_prestatgeria(String nom) { return productes.containsKey(nom);
}

public int get_quantProducte(String nom) {
		return productes.get(nom);
}

public void print_prestatgeria(String nom) {

	}






}