package classes;

import Exepcions.NotEnoughQuantityCaixaWarning;
import Exepcions.ProductNotFoundCaixaException;
import Exepcions.QuanitatInvalidException;

import java.util.Map;
import java.util.HashMap;

public class Caixa {
    private Map<String,Integer> productes=new HashMap<>(); //nom_producte, <id_prestatgeria, quantitat>

    public Caixa(){
        productes = new HashMap<>();
    }

    public void afegir_producte(String nom_producte, int quantitat) throws QuanitatInvalidException{
        if(quantitat <= 0){
            throw new QuanitatInvalidException();
        }
        if(productes.containsKey(nom_producte)){
            productes.put(nom_producte, productes.get(nom_producte) + quantitat);
        }
        else{
            productes.put(nom_producte, quantitat);
        }
    }

    public void retirar_producte(String nom_producte, int quantitat) throws NotEnoughQuantityCaixaWarning, ProductNotFoundCaixaException{
        if(productes.containsKey(nom_producte)){
            if(productes.get(nom_producte) >= quantitat){
                productes.put(nom_producte, productes.get(nom_producte) - quantitat);
            }
            else{
                throw new NotEnoughQuantityCaixaWarning(nom_producte);
            }

        }
        else {
            throw new ProductNotFoundCaixaException(nom_producte);
        }
    }

    public int get_quantitat(String nom_producte){
        if(productes.containsKey(nom_producte)){
            return productes.get(nom_producte);
        }
        else {
            throw new ProductNotFoundCaixaException(nom_producte);
        }
    }

    //Imprimeix Nom producte: quantitat toal
    public Map<String,Integer> getticket(){
        return productes;
    }

    public void pagar(){
        productes.clear();
    }
}
