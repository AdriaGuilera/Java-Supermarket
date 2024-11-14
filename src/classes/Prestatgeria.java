package classes;

import Exepcions.*;

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
	
	public void afegir_producte(String nomP, Integer quantitat)
	throws JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
		if(!productes.containsKey(nomP)) {
			int pos = 0;
			while(posicions.containsKey(pos)) {
				pos++;
			}
			if(pos < mida_prestatgeria){
				posicions.put(pos, nomP);
				productes.put(nomP, quantitat);
			}
			else throw new PrestatgeriaFullException(id);
		}
		else {
			throw new JaExisteixProucteaPrestatgeriaException(nomP, id);
		}
	}
	public void eliminar_producte(String nomP)
	throws ProductNotFoundPrestatgeriaException {
		if(productes.containsKey(nomP)){
			productes.remove(nomP);
			productes_fixats.remove(nomP);
			int pos = get_pos(nomP);
			posicions.remove(pos);
		}
		else{
			throw new ProductNotFoundPrestatgeriaException(id, nomP);
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

	public Map<Integer, String> getPosicions() {
		return posicions;
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
		mida_prestatgeria += mida_prestatge;
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
	
	public void incrementar_quantitat(String nomP, Integer quantitat)
	throws QuanitatInvalidException {
		if(quantitat <= 0) {
			throw new QuanitatInvalidException();
		}
		if(productes.containsKey(nomP)) {
			productes.compute(nomP, (k, currentQuantitat) -> currentQuantitat + quantitat);
		}
	}
	public int decrementar_quantitat(String nomP, Integer quantitat) throws QuanitatInvalidException, ProductNotFoundPrestatgeriaException {
		if(quantitat <= 0) {
			throw new QuanitatInvalidException();
		}
		if (productes.containsKey(nomP)) {
			int quantitat_actual = productes.get(nomP);
			if (quantitat_actual - quantitat >= 0) {
				productes.put(nomP, quantitat_actual - quantitat);
				return quantitat;
			} else {
				productes.put(nomP, 0);
				return quantitat_actual;
			}
		} else {
			throw new ProductNotFoundPrestatgeriaException(id, nomP);
		}
	}

	public void fixar_producte_prestatgeria(String nomP) {
		productes_fixats.add(nomP);
	}
	public void desfixar_producte_prestatgeria(String nomP) {
		productes_fixats.remove(nomP);
	}

	public Map<Integer,String> getdistribucio() {
		return posicions;
	}

	public boolean esta_a_prestatgeria(String nom) { return productes.containsKey(nom);
	}

	public int get_quantProducte(String nom)
	throws ProductNotFoundPrestatgeriaException {
		if(!productes.containsKey(nom)) {
			throw new ProductNotFoundPrestatgeriaException(id, nom);
		}
		return productes.get(nom);
	}

	public void moure_producte(int hueco_origen, int hueco_desti)
	throws ProducteNotInHuecoException, InvalidHuecosException {
		if(hueco_desti < 0 || hueco_desti >= mida_prestatgeria || hueco_origen < 0 || hueco_origen >= mida_prestatgeria || hueco_origen == hueco_desti) {
			throw new InvalidHuecosException();
		}
		if(!posicions.containsKey(hueco_origen)) {
			throw new ProducteNotInHuecoException(hueco_origen);
		}
		if(posicions.containsKey(hueco_desti)) {
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
		for(String nomP: ordre) {
			posicions.put(i, nomP);
			i++;
		}
	}






}