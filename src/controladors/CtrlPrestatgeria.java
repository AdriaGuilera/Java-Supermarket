package controladors;

import java.util.*;


import Exepcions.*;
import Exepcions.PrestatgeriaNotFoundException;
import Exepcions.ProductNotFoundPrestatgeriaException;
import classes.Pair;
import classes.Prestatgeria;
import Exepcions.ProductNotFoundPrestatgeriaException;

public class CtrlPrestatgeria {
	public Map<String, classes.Prestatgeria> prestatgeries;
	
	public CtrlPrestatgeria() {
	 prestatgeries = new HashMap<>();
	}

    public  void afegirPrestatgeria(String id, int mida, int mida_prestatge) {
        if (!prestatgeries.containsKey(id)){
            if(mida%mida_prestatge!=0){
                System.out.println("Error: La mida de la prestatgeria a de ser multiple de la del prestatge");
            }else {
                Prestatgeria pr = new Prestatgeria(id, mida, mida_prestatge);
                prestatgeries.put(id, pr);
            }

        }
        else {
            System.out.println("Error: Ja existeix una prestatgeria amb aquest identificador.");
        }
    }

    public Map<String,Integer> getProductesPrestatgeria(String id) {
        if (prestatgeries.containsKey(id)){
            return prestatgeries.get(id).get_productes();
        }
        else {
            System.out.println("Error: No existeix una prestatgeria amb aquest identificador.");
            return new HashMap<>();
        }
    }

    public Map<String,Integer> eliminarPrestatgeria(String id) {
        if (prestatgeries.containsKey(id)){
            Prestatgeria pr = prestatgeries.get(id);
            Map<String,Integer> productes = pr.get_productes();
            prestatgeries.remove(id);
            return productes;
        }
        else {
            System.out.println("Error: No existeix una prestatgeria amb aquest identificador.");
        }
        return new HashMap<>();
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

    public void incrementar_quantitat_producte(String id, String nomP, int quantitat){
        if(!prestatgeries.containsKey(id)){
            System.out.println("Error: No existeix una prestatgeria amb aquest identificador.");
            return;
        }
        else{
            Prestatgeria pr = prestatgeries.get(id);
            pr.incrementar_quantitat(nomP, quantitat);
        }
    }



    //función auxiliar que elimina el producto de prestatgeria y devuelve el nombre y la cantidad que habrá que aumentar en el almacén
    public Pair<String, Integer> retirarProductePrestatgeria(String id, String nomP){
        if(!prestatgeries.containsKey(id)){
            throw new PrestatgeriaNotFoundException(id);
        }
        Prestatgeria pr = prestatgeries.get(id);
        if(!pr.esta_a_prestatgeria(nomP)){
            throw new ProductNotFoundPrestatgeriaException(id, nomP);
        }
        
        int quantitat = pr.get_quantProducte(nomP);
        pr.eliminar_producte(nomP);
        return new Pair<>(nomP, quantitat);
    }
    
    public int decrementar_quantitat_producte(String id, String nomP, int quantitat) throws QuanitatInvalidException, ProductNotFoundPrestatgeriaException, PrestatgeriaNotFoundException {
            if(!prestatgeries.containsKey(id)){
                throw new PrestatgeriaNotFoundException(id);
            }
            else{
                Prestatgeria pr = prestatgeries.get(id);
                return pr.decrementar_quantitat(nomP, quantitat);
            }
    }
    
    public void afegir_prestatge(String id) {
        if(!prestatgeries.containsKey(id)){
            System.out.println("Error: No existeix una prestatgeria amb aquest identificador.");
            return;
        }
        else{
            Prestatgeria pr = prestatgeries.get(id);
            pr.afegir_prestatge();
            System.out.println("Prestatge añadido a la prestatgeria.");
        }
    }
    
    public Map<String,Integer> eliminar_prestatge(String id) {
        if(!prestatgeries.containsKey(id)){
            System.out.println("Error: No existeix una prestatgeria amb aquest identificador.");
            return new HashMap<>();
        }
        else{
            Prestatgeria pr = prestatgeries.get(id);
            return pr.eliminar_prestatge();
        }
    }

    //Mou un producte del magatzem a la prestatgeria
    public void afegirProducte(String id_prest, String nom,int quantitat) {
        if(!prestatgeries.containsKey(id_prest)) {
            System.out.println("Error: No existeix una prestatgeria amb aquest identificador.");
            return;
        }
        else{
            Prestatgeria pr = prestatgeries.get(id_prest);
            pr.afegir_producte(nom, quantitat);
        }
    }
    public boolean contains_quantitat(String nom_producte, int quantiat, String id_prestatgeria, int quantitat_ja_afegida) {
        Prestatgeria pr = prestatgeries.get(id_prestatgeria);
        return pr.esta_a_prestatgeria(nom_producte) && ((pr.get_quantProducte(nom_producte)-quantitat_ja_afegida) >= quantiat);
    }

    public boolean contains_producte(String id, String nom_producte){
        if(!prestatgeries.containsKey(id)){
            System.out.println("Error: No existeix una prestatgeria amb aquest identificador.");
            return false;
        }
        else{
            Prestatgeria pr = prestatgeries.get(id);
            return pr.esta_a_prestatgeria(nom_producte);
        }
    }

    public Vector<String> getNomsProductes(String id){
        if(!prestatgeries.containsKey(id)){
            System.out.println("Error: No existeix una prestatgeria amb aquest identificador.");
            return new Vector<>();
        }
        else{
            Prestatgeria pr = prestatgeries.get(id);
            return pr.getNomsProductes();
        }
    }

    public void printPrestatgeria(String id) {
        if(!prestatgeries.containsKey(id)){
            System.out.println("Error: No existeix una prestatgeria amb aquest identificador.");
            return;
        }
        else{
            System.out.println("Prestatgeria " + id + ":");
            Prestatgeria pr = prestatgeries.get(id);
            pr.imprimirdistribucio();
        }
    }

    public void moureProducte(String id_prestatgeria,int hueco_origen, int hueco_desti) {
        if(!prestatgeries.containsKey(id_prestatgeria)){
            System.out.println("Error: No existeix una prestatgeria amb aquest identificador.");
            return;
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
            System.out.println("Error: No existeix una prestatgeria amb aquest identificador.");
            return;
        }
        else{
            Prestatgeria pr = prestatgeries.get(id);
            pr.setDistribucio(ordre);
        }
    }
    public Set<String> getProductesFixats(String id){
        if(!prestatgeries.containsKey(id)){
            System.out.println("Error: No existeix una prestatgeria amb aquest identificador.");
            return new HashSet<>();
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
