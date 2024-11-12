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
	
	public Prestatgeria(String id, int mida_prestatgeria, int mida_prestatge) {
		this.id = id;
		this.mida_prestatgeria= mida_prestatgeria;
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
			System.out.println("Error: Ja existeix un producte amb aquest nom a la prestatgeria, utilitza la funcio de incrementar.");
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
		Vector<String> nombres = new Vector<>();
		for(int i=0; i<mida_prestatgeria; ++i){
			if(posicions.containsKey(i)){
				nombres.add(posicions.get(i));
			}else{
				nombres.add(null);
			}
		}
		return nombres;
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
		for(int i =0; i<mida_prestatge; ++i){
			posicions.put(mida_prestatgeria+i,null);
		}
		this.mida_prestatgeria += mida_prestatge;
	}


	public Map<String,Integer> eliminar_prestatge() {
		Map<String,Integer> productes_eliminats = new HashMap<>();
		if(mida_prestatge<=mida_prestatgeria) {
			for (int i = mida_prestatgeria - mida_prestatge; i < mida_prestatgeria; i++) {
				System.out.println("bolivia");

				if (posicions.containsKey(i)) {
					String nomP = posicions.get(i);
					if (nomP != null) productes_eliminats.put(nomP, productes.get(nomP));


					productes.remove(nomP);
					productes_fixats.remove(nomP);
					posicions.remove(i);
				}
			}
			mida_prestatgeria -= mida_prestatge;
			System.out.println(posicions);
		}
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

	public void imprimirdistribucio() {
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
	public void moure_producte(int hueco_origen, int hueco_desti) {
		if(!posicions.containsKey(hueco_origen)) {
			System.out.println("Error: No existeix un producte a la posicio origen.");
			return;
		}
		if(posicions.containsKey(hueco_desti)) {
			System.out.println("Swap productes");
			String nomPdesti = posicions.get(hueco_desti);
			String nomP = posicions.get(hueco_origen);
			posicions.remove(hueco_origen);
			posicions.remove(hueco_desti);
			posicions.put(hueco_desti, nomP);
			posicions.put(hueco_origen, nomPdesti);
		}else{
			String nomP = posicions.get(hueco_origen);
			posicions.remove(hueco_origen);
			posicions.put(hueco_desti, nomP);
		}

	}

	public void setDistribucio(Vector<String> ordre) {
		int i = 0;
		posicions.clear();
		System.out.println("hola5");
		for(String nomP: ordre) {
			posicions.put(i, nomP);
			i++;
		}
		System.out.println("hola5");
	}






}