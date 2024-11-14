package controladors;

import java.util.*;
import Exepcions.*;
import classes.Pair;
import classes.Prestatgeria;

public class CtrlPrestatgeria {
	public Map<String, classes.Prestatgeria> prestatgeries;
	
	public CtrlPrestatgeria() {
	 prestatgeries = new HashMap<>();
	}

    public  void afegirPrestatgeria(String id, int mida, int mida_prestatge)
    throws MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteix {
        if (!prestatgeries.containsKey(id)){
            if(mida >= mida_prestatge && mida%mida_prestatge!=0){
                throw new MidaPrestatgeriaInvalidException();
            }else {
                Prestatgeria pr = new Prestatgeria(id, mida, mida_prestatge);
                prestatgeries.put(id, pr);
            }

        }
        else {
            throw new PrestatgeriaJaExisteix(id);
        }
    }

    public Map<String,Integer> getProductesPrestatgeria(String id)
    throws PrestatgeriaNotFoundException {
        if (prestatgeries.containsKey(id)){
            return prestatgeries.get(id).get_productes();
        }
        else {
            throw new PrestatgeriaNotFoundException(id);
        }
    }

    public Map<String,Integer> eliminarPrestatgeria(String id)
    throws PrestatgeriaNotFoundException {
        if (prestatgeries.containsKey(id)){
            Prestatgeria pr = prestatgeries.get(id);
            Map<String,Integer> productes = pr.get_productes();
            prestatgeries.remove(id);
            return productes;
        }
        else {
            throw new PrestatgeriaNotFoundException(id);
        }
    }

    public void fixarProducte(String id, String nomP)
    throws PrestatgeriaNotFoundException {
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        }
        prestatgeries.get(id).fixar_producte_prestatgeria(nomP);
    }

    public void desfixarProducte(String id, String nomP)
    throws PrestatgeriaNotFoundException, ProductNotFoundPrestatgeriaException {

        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        }
        Prestatgeria pr = prestatgeries.get(id);
        if(pr.esta_a_prestatgeria(nomP)) {
            pr.desfixar_producte_prestatgeria(nomP);
        }
        else{
            throw new ProductNotFoundPrestatgeriaException(id, nomP);
        }
    }

    public void incrementar_quantitat_producte(String id, String nomP, int quantitat)
    throws QuanitatInvalidException, PrestatgeriaNotFoundException {
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        }
        else{
            Prestatgeria pr = prestatgeries.get(id);
            pr.incrementar_quantitat(nomP, quantitat);
        }
    }


    //función auxiliar que elimina el producto de prestatgeria y devuelve el nombre y la cantidad que habrá que aumentar en el almacén
    public Pair<String, Integer> retirarProductePrestatgeria(String id, String nomP)
    throws PrestatgeriaNotFoundException, ProductNotFoundPrestatgeriaException{
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        }
        Prestatgeria pr = prestatgeries.get(id);
        int quantitat = pr.get_quantProducte(nomP);
        pr.eliminar_producte(nomP);
        return new Pair<>(nomP, quantitat);
    }
    
    public int decrementar_quantitat_producte(String id, String nomP, int quantitat)
    throws QuanitatInvalidException, ProductNotFoundPrestatgeriaException, PrestatgeriaNotFoundException {
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        }
        else{
            Prestatgeria pr = prestatgeries.get(id);
            return pr.decrementar_quantitat(nomP, quantitat);
        }
    }
    
    public void afegir_prestatge(String id)
    throws PrestatgeriaNotFoundException {
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        }
        else{
            Prestatgeria pr = prestatgeries.get(id);
            pr.afegir_prestatge();
        }
    }
    
    public Map<String,Integer> eliminar_prestatge(String id)
    throws PrestatgeriaNotFoundException {
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        }
        else{
            Prestatgeria pr = prestatgeries.get(id);
            return pr.eliminar_prestatge();
        }
    }

    //Mou un producte del magatzem a la prestatgeria
    public void afegirProducte(String id_prest, String nom,int quantitat)
    throws PrestatgeriaNotFoundException {
        if(!prestatgeries.containsKey(id_prest)) {
            throw new PrestatgeriaNotFoundException(id_prest);
        }
        else{
            Prestatgeria pr = prestatgeries.get(id_prest);
            pr.afegir_producte(nom, quantitat);
        }
    }

    public boolean contains_producte(String id, String nom_producte)
    throws PrestatgeriaNotFoundException {
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        }
        else{
            Prestatgeria pr = prestatgeries.get(id);
            return pr.esta_a_prestatgeria(nom_producte);
        }
    }

    public Vector<String> getNomsProductes(String id)
    throws PrestatgeriaNotFoundException {
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        }
        else{
            Prestatgeria pr = prestatgeries.get(id);
            return pr.getNomsProductes();
        }
    }

    public Prestatgeria getPrestatgeria(String id)
    throws PrestatgeriaNotFoundException {
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        }
        return prestatgeries.get(id);
    }

    public void moureProducte(String id_prestatgeria,int hueco_origen, int hueco_desti)
    throws PrestatgeriaNotFoundException {
        if(!prestatgeries.containsKey(id_prestatgeria)){
            throw new PrestatgeriaNotFoundException(id_prestatgeria);
        }
        else{
            Prestatgeria pr = prestatgeries.get(id_prestatgeria);
            pr.moure_producte(hueco_origen, hueco_desti);
        }
    }
    public int get_quantitat_producte(String id_prestatgeria, String nomProducte){
        if(!prestatgeries.containsKey(id_prestatgeria)){
            System.out.println("Error: No existeix una prestatgeria amb aquest identificador.");
            return -1;
        }
        else{
            Prestatgeria pr = prestatgeries.get(id_prestatgeria);
            return pr.get_quantProducte(nomProducte);
        }
    }
    public void setDistribucio(String id, Vector<String> ordre){
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        }
        else{
            Prestatgeria pr = prestatgeries.get(id);
            pr.setDistribucio(ordre);
        }
    }
    public Set<String> getProductesFixats(String id)
    throws PrestatgeriaNotFoundException {
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        }
        else{
            Prestatgeria pr = prestatgeries.get(id);
            return pr.getProductesFixats();
        }
    }
    public void eliminar_producte(String nom){
        for(Prestatgeria pr : prestatgeries.values()){
            pr.eliminar_producte(nom);
        }
    }
}
