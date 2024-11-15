package controladors;

import java.util.Map;
import java.util.Set;
import java.util.Vector;

import Exepcions.*;
import classes.*;

public class CtrlDomini {
    public CtrlProducte CtrlProducte = new CtrlProducte();
    public CtrlPrestatgeria CtrlPrestatgeria = new CtrlPrestatgeria();
    public classes.Caixa Caixa = new Caixa();
    public CtrlComandes CtrlComandes = new CtrlComandes();
    public Algorismes Algorismes = new Algorismes();

    //Funciones del CtrlComandes

    public void crearComanda(String nomComanda) throws IllegalArgumentException{
        if (nomComanda == null || nomComanda.isEmpty()) {
            throw new IllegalArgumentException("El nom de la comanda no pot estar buit.");
        }
            CtrlComandes.crearComanda(nomComanda);

    }

    public void eliminarComanda(String nomComanda) throws ProductNotFoundComandaException,ComandaNotFoundException,IllegalArgumentException {
        if (nomComanda == null || nomComanda.isEmpty()) {
            throw new IllegalArgumentException("El nom de la comanda no pot estar buit.");
        }
        CtrlComandes.eliminarComanda(nomComanda);

    }


    public void afegirProducteComanda(String nomComanda, String nomProducte, int quantitat) throws QuanitatInvalidException, ComandaNotFoundException, ProductNotFoundMagatzemException, IllegalArgumentException  {
        if (nomComanda == null || nomComanda.isEmpty()) {
            throw new IllegalArgumentException("El nom de la comanda no pot estar buit.");
        }
        if (nomProducte == null || nomProducte.isEmpty()) {
            throw new IllegalArgumentException("El nom del Producte no pot estar buit.");
        }
        if (quantitat <=0) {
            throw new IllegalArgumentException("La quantitat no pot estar buida.");
        }
        if(!CtrlComandes.existeixComanda(nomComanda)) {
            throw new ComandaNotFoundException(nomComanda);
        }
        if(!CtrlProducte.existeix_producte(nomProducte)) {
               throw new ProductNotFoundMagatzemException(nomProducte);
        }
            CtrlComandes.afegirProducteComanda(nomComanda, nomProducte, quantitat);
    }

    public void eliminarProducteComanda(String nomComanda, String nomProducte, int quantitat) throws ProductNotFoundComandaException, IllegalArgumentException{

        if (nomComanda == null || nomComanda.isEmpty()) {
            throw new IllegalArgumentException("El nom de la comanda no pot estar buit.");
        }
        if (nomProducte == null || nomProducte.isEmpty()) {
            throw new IllegalArgumentException("El nom del Producte no pot estar buit.");
        }
            CtrlComandes.eliminarProducteComanda(nomComanda, nomProducte, quantitat);
    }

    public Map<String, Comanda> getComandes() {
        return CtrlComandes.getComandes();
    }
    public Comanda getComandaUnica(String nomComanda)throws ComandaNotFoundException,IllegalArgumentException {
        if (nomComanda == null || nomComanda.isEmpty()) {
            throw new IllegalArgumentException("El nom de la comanda no pot estar buit.");
        }
           return CtrlComandes.getComandaUnica(nomComanda);
    }

    //Prestatgeria

    // Implementación de los métodos de Prestatgeria

    public void afegirProductePrestatgeria(String nomProducte, int quantitat, String idPrestatgeria)
    throws PrestatgeriaNotFoundException, QuanitatInvalidException, MaxHuecoWarning, ProductNotFoundMagatzemException,
            JaExisteixProucteaPrestatgeriaException, NotEnoughQuantityMagatzem, IllegalArgumentException {

        if (idPrestatgeria == null || idPrestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        if (nomProducte == null || nomProducte.isEmpty()) {
            throw new IllegalArgumentException("El nom del Producte no pot estar buit.");
        }
        int maxhueco = CtrlProducte.getmaxhueco(nomProducte);
        int stock = CtrlProducte.get_stock_magatzem(nomProducte);
        if(quantitat <= 0) {
            throw new QuanitatInvalidException(1);
        }

        if(stock >= quantitat) {
            if (CtrlPrestatgeria.contains_producte(idPrestatgeria, nomProducte)) {
                int actual = CtrlPrestatgeria.get_quantitat_producte(idPrestatgeria, nomProducte);

                if (quantitat + actual > maxhueco) {
                    CtrlPrestatgeria.incrementar_quantitat_producte(idPrestatgeria, nomProducte, maxhueco - actual);
                    CtrlProducte.decrementar_stock(nomProducte, maxhueco - actual);
                    throw new MaxHuecoWarning(nomProducte);
                } else {
                    CtrlPrestatgeria.incrementar_quantitat_producte(idPrestatgeria, nomProducte, quantitat);
                    CtrlProducte.decrementar_stock(nomProducte, quantitat);
                }
            } else {
                if (quantitat > maxhueco) {
                    CtrlPrestatgeria.afegirProducte(idPrestatgeria, nomProducte, maxhueco);
                    CtrlProducte.decrementar_stock(nomProducte, maxhueco);
                    throw new MaxHuecoWarning(nomProducte);
                } else {
                    CtrlPrestatgeria.afegirProducte(idPrestatgeria, nomProducte, quantitat);
                    CtrlProducte.decrementar_stock(nomProducte, quantitat);
                }
            }
        }
        else{
            throw new NotEnoughQuantityMagatzem(nomProducte);
        }


    }


    public void moureProducteDeHueco(String id_prestatgeria, String nomProducte, int huecoOrigen, int huecoDestino)
    throws PrestatgeriaNotFoundException, ProducteNotInHuecoException, InvalidHuecosException, IllegalArgumentException{
        if (id_prestatgeria == null || id_prestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la comanda no pot estar buit.");
        }
        if (nomProducte == null || nomProducte.isEmpty()) {
            throw new IllegalArgumentException("El nom del Producte no pot estar buit.");
        }
        CtrlPrestatgeria.moureProducte(id_prestatgeria, huecoOrigen, huecoDestino);
    }


    public void decrementarStockAProducte(String id_prestatgeria, String nomProducte, int quantitat)
    throws QuanitatInvalidException, ProductNotFoundPrestatgeriaException,PrestatgeriaNotFoundException, MaxMagatzemWarning, ProductNotFoundMagatzemException,
            NotEnoughQuantityPrestatgeriaWarning, IllegalArgumentException{
        if (id_prestatgeria == null || id_prestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la comanda no pot estar buit.");
        }
        if (nomProducte == null || nomProducte.isEmpty()) {
            throw new IllegalArgumentException("El nom del Producte no pot estar buit.");
        }

        int quantitatelim = CtrlPrestatgeria.decrementar_quantitat_producte(id_prestatgeria, nomProducte, quantitat);
        CtrlProducte.incrementar_stock(nomProducte, quantitatelim);
        if(quantitatelim == 0){
            throw new NotEnoughQuantityPrestatgeriaWarning(id_prestatgeria, nomProducte);
        }

    }


    public void generarDistribucioBacktracking(String id_prestatgeria)
    throws PrestatgeriaNotFoundException, IllegalArgumentException {
        if(id_prestatgeria == null || id_prestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        Vector<String> productes = CtrlPrestatgeria.getNomsProductes(id_prestatgeria);
        Set<String> fixats = CtrlPrestatgeria.getProductesFixats(id_prestatgeria);
        Vector<String> novadist = Algorismes.encontrarMejorDistribucionBacktracking(productes , fixats);
        CtrlPrestatgeria.setDistribucio(id_prestatgeria, novadist);
    }

    public void generarDistribucioHillClimbing(String id_prestatgeria)
    throws PrestatgeriaNotFoundException, IllegalArgumentException {
        if(id_prestatgeria == null || id_prestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        Vector<String> productes = CtrlPrestatgeria.getNomsProductes(id_prestatgeria);
        Set<String> fixats = CtrlPrestatgeria.getProductesFixats(id_prestatgeria);
        Vector<String> novadist = Algorismes.encontrarMejorDistribucionHillClimbing(productes , fixats);
        CtrlPrestatgeria.setDistribucio(id_prestatgeria, novadist);

    }

    //Midaprestatgeria%midaPrestatge == 0
    public void afegirPrestatgeria(String idPrestatgeria, int midaPrestatge, int midaPrestatgeria)
    throws MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException, IllegalArgumentException {
        if(idPrestatgeria == null || idPrestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        CtrlPrestatgeria.afegirPrestatgeria(idPrestatgeria, midaPrestatgeria, midaPrestatge);
    }

    public void eliminarPrestatgeria(String idPrestatgeria)
    throws PrestatgeriaNotFoundException, ProductNotFoundMagatzemException, MaxMagatzemWarning, QuanitatInvalidException, IllegalArgumentException {
        if(idPrestatgeria == null || idPrestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        Map<String,Integer> producteseliminats = CtrlPrestatgeria.eliminarPrestatgeria(idPrestatgeria);
        for(Map.Entry<String, Integer> entry : producteseliminats.entrySet()){
            String nom = entry.getKey();
            int quantitat = entry.getValue();
            CtrlProducte.incrementar_stock(nom, quantitat);
        }
    }

    public void afegirPrestatge(String idPrestatgeria)
    throws PrestatgeriaNotFoundException,IllegalArgumentException {
        if(idPrestatgeria == null || idPrestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        CtrlPrestatgeria.afegir_prestatge(idPrestatgeria);
    }

    public void eliminarPrestatge(String idPrestatgeria)
    throws PrestatgeriaNotFoundException,QuanitatInvalidException, MaxMagatzemWarning, IllegalArgumentException{
        if(idPrestatgeria == null || idPrestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        Map<String,Integer> eliminats = CtrlPrestatgeria.eliminar_prestatge(idPrestatgeria);
            for(Map.Entry<String, Integer> entry : eliminats.entrySet()){
                String nom = entry.getKey();
                int quantitat = entry.getValue();
                CtrlProducte.incrementar_stock(nom, quantitat);
            }


    }

    //FALTA ZEROSTOCK MAGATZEM
    public void reposarPrestatgeria(String id_prestatgeria)
    throws PrestatgeriaNotFoundException, ProductNotFoundMagatzemException, QuanitatInvalidException, IllegalArgumentException{
        if(id_prestatgeria == null || id_prestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        Map<String,Integer> productes = CtrlPrestatgeria.getProductesPrestatgeria(id_prestatgeria);
        for(Map.Entry<String, Integer> entry : productes.entrySet()){
            int stock = CtrlProducte.get_stock_magatzem(entry.getKey());
            int max = CtrlProducte.getmaxhueco(entry.getKey());
            int actual = entry.getValue();
            if(actual < max){
                int aafegir = max - actual;
                if(aafegir <= stock){
                    CtrlPrestatgeria.incrementar_quantitat_producte(id_prestatgeria, entry.getKey(), aafegir);
                    CtrlProducte.decrementar_stock(entry.getKey(), aafegir);
                }
                else{
                    CtrlPrestatgeria.incrementar_quantitat_producte(id_prestatgeria, entry.getKey(), stock);
                    CtrlProducte.decrementar_stock(entry.getKey(), stock);
                }
            }
        }
    }
    public void fixarProducte(String id_prestatgeria, String nomProducte)
    throws PrestatgeriaNotFoundException, ProductNotFoundPrestatgeriaException, ProducteFixatException, IllegalArgumentException {
        if(id_prestatgeria == null || id_prestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        if(nomProducte == null || nomProducte.isEmpty()) {
            throw new IllegalArgumentException("El nom del Producte no pot estar buit.");
        }
        CtrlPrestatgeria.fixarProducte(id_prestatgeria, nomProducte);
    }

    public void desfixarProducte(String id_prestatgeria, String nomProducte)
    throws PrestatgeriaNotFoundException, ProductNotFoundPrestatgeriaException, ProducteNoFixatException, IllegalArgumentException {
        if(id_prestatgeria == null || id_prestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        if(nomProducte == null || nomProducte.isEmpty()) {
            throw new IllegalArgumentException("El nom del Producte no pot estar buit.");
        }
        CtrlPrestatgeria.desfixarProducte(id_prestatgeria, nomProducte);
    }

    public void retirarProducteAMagatzem(String id_prestatgeria,String nomProducte)
    throws PrestatgeriaNotFoundException, ProductNotFoundMagatzemException, ProductNotFoundPrestatgeriaException{
        if(!CtrlProducte.existeix_producte(nomProducte)){
            throw new ProductNotFoundMagatzemException(nomProducte);
        }
        Pair<String,Integer> producteretirat = CtrlPrestatgeria.retirarProductePrestatgeria(id_prestatgeria, nomProducte);
        if(producteretirat.getKey() != null) {
            String nom = producteretirat.getKey();
            int quantitat = producteretirat.getValue();
            CtrlProducte.incrementar_stock(nom, quantitat);
        }
    }





    //Caixa
    public int afegir_producte_caixa(String nom_producte, int quantitat, String id_prestatgeria) throws QuanitatInvalidException, ProductNotFoundMagatzemException {
        if(!CtrlProducte.existeix_producte(nom_producte)){
            throw new ProductNotFoundMagatzemException(nom_producte);
        }
        int quantitat_a_afegir = CtrlPrestatgeria.decrementar_quantitat_producte(id_prestatgeria, nom_producte,quantitat);
        Caixa.afegir_producte(nom_producte, quantitat_a_afegir);
        return quantitat_a_afegir;
    }

    // Retirar producto de la caja
    public void retirar_producte_caixa(String nom_producte, int quantitat, String id_prestatgeria)
    throws QuanitatInvalidException,IllegalArgumentException, ProductNotFoundCaixaException, NotEnoughQuantityCaixaWarning {
        if(nom_producte == null || nom_producte.isEmpty()) {
            throw new IllegalArgumentException("El nom del Producte no pot estar buit.");
        }
        if(id_prestatgeria == null || id_prestatgeria.isEmpty()) {
            throw new IllegalArgumentException("El nom de la Prestatgeria no pot estar buit.");
        }
        Caixa.retirar_producte(nom_producte, quantitat);
    }

    // Pagar y vaciar la caja, decrementando la cantidad de productos en las estanterías
    public void pagar_caixa() {
        Map<String, Integer> productes = Caixa.getticket();
        for(Map.Entry<String, Integer> entry : productes.entrySet()){
            CtrlProducte.incrementar_stock(entry.getKey(), entry.getValue());
        }
        Caixa.pagar();
    }


    //Magatzem (Productes)
    public void executarComandes(String[] noms) throws ComandaNotFoundException {
        if (noms == null) {
            throw new IllegalArgumentException("Els noms no poden estar buits.");
        }

        Map<String, Comanda> comandesAExecutar = CtrlComandes.obtenirComandes(noms);

        CtrlProducte.executar_comandes(comandesAExecutar);
    }

    public void obtenirComandaAutomatica(String nomComanda) {
        if (nomComanda == null || nomComanda.isEmpty()) {
            throw new IllegalArgumentException("El nom de la comanda no pot estar buit.");
        }
        //Primero obtenemos un map de <string,int> con los productos y cantidades que faltan por rellenar;
        Map<String, Integer> productosFaltantes = CtrlProducte.obtenirComandaAutomatica();
        //Creamos una comanda con los productos
        CtrlComandes.crearComandaAutomatica(nomComanda, productosFaltantes);
    }


    public void altaProducte(String nomProducte, int max_h, int max_m) throws IllegalArgumentException, ProducteJaExisteixException{
        if (nomProducte == null || nomProducte.isEmpty()) {
            throw new IllegalArgumentException("El nom de la comanda no pot estar buit.");
        }
        if (max_h<=0) {
            throw new IllegalArgumentException("El nom del Producte no pot estar buit.");
        }
        if (max_m <=0) {
            throw new IllegalArgumentException("La quantitat no pot ser negativa o 0.");
        }
        CtrlProducte.altaProducte(nomProducte, max_h, max_m);
    }

    public void eliminar_producte(String nomProducte) throws IllegalArgumentException, ProductNotFoundMagatzemException {
        if (nomProducte == null || nomProducte.isEmpty()) {
            throw new IllegalArgumentException("El nom de la comanda no pot estar buit.");
        }
        //Lo eliminamos del almacen   //Lo eliminamos de las prestatgerias i caixa
        CtrlPrestatgeria.eliminar_producte(nomProducte);
        CtrlProducte.eliminar_producte(nomProducte);
        int qcaixa = Caixa.get_quantitat(nomProducte);
        Caixa.retirar_producte(nomProducte, qcaixa);

    }



    public void afegir_similitud(String nom1, String nom2, float value)  throws IllegalArgumentException, ProductNotFoundMagatzemException, calculMateixosProductesSimilitud {
        if (nom1 == null || nom1.isEmpty()) {
            throw new IllegalArgumentException("El nom del primer producte no pot estar buit.");
        }
        if (nom2 == null || nom2.isEmpty()) {
            throw new IllegalArgumentException("El nom del segon producte no pot estar buit.");
        }
        if (value<=0) {
            throw new IllegalArgumentException("El valor de la similitud ha de ser superior a 0");
        }
        CtrlProducte.afegir_similitud(nom1, nom2, value);
    }

    public void eliminarSimilitud(String nom1, String nom2) throws IllegalArgumentException, ProductNotFoundMagatzemException, calculMateixosProductesSimilitud{
        if (nom1 == null || nom1.isEmpty()) {
            throw new IllegalArgumentException("El nom del primer producte no pot estar buit.");
        }
        if (nom2 == null || nom2.isEmpty()) {
            throw new IllegalArgumentException("El nom del segon producte no pot estar buit.");
        }
        CtrlProducte.eliminarSimilitud(nom1, nom2);
    }

    public void modificarSimilitud(String nom1, String nom2, float value) throws IllegalArgumentException, ProductNotFoundMagatzemException, calculMateixosProductesSimilitud{
        if (nom1 == null || nom1.isEmpty()) {
            throw new IllegalArgumentException("El nom del primer producte no pot estar buit.");
        }
        if (nom2 == null || nom2.isEmpty()) {
            throw new IllegalArgumentException("El nom del segon producte no pot estar buit.");
        }
        if (value<=0) {
            throw new IllegalArgumentException("El valor de la similitud ha de ser superior a 0");
        }
        CtrlProducte.modificarSimilitud(nom1, nom2, value);
    }

//    public Producte get_producte(String nomProducte) throws IllegalArgumentException{
//        if (nomProducte == null || nomProducte.isEmpty()) {
//            throw new IllegalArgumentException("El nom del primer producte no pot estar buit.");
//        }
//        return CtrlProducte.getProducte(nomProducte);
//    }
//
//    public Map<String, Producte> getMagatzem() {
//        return CtrlProducte.getMagatzem();
//    }
}
