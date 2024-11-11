package controladors;

import java.util.Map;
import java.util.Set;
import java.util.Vector;

import classes.*;

public class CtrlDomini {
    public CtrlProducte CtrlProducte = new CtrlProducte();
    public CtrlPrestatgeria CtrlPrestatgeria = new CtrlPrestatgeria();
    public classes.Caixa Caixa = new Caixa();
    public CtrlComandes CtrlComandes = new CtrlComandes();
    public Algorismes Algorismes = new Algorismes();

    //Funciones del CtrlComandes

    public void crearComanda(String nomComanda) {

        CtrlComandes.crearComanda(nomComanda);
    }

    public void eliminarComanda(String nomComanda) {

        CtrlComandes.eliminarComanda(nomComanda);
    }


    public void afegirProducteComanda(String nomComanda, String nomProducte, int quantitat) {
        CtrlComandes.afegirProducteComanda(nomComanda,nomProducte,quantitat);
    }
    public void eliminarProducteComanda(String nomComanda, String nomProducte) {
        CtrlComandes.eliminarProducteComanda(nomComanda,nomProducte);
    }
    public Map<String, Comanda> obtenirComandes(String[] nomsComandes) {
        return CtrlComandes.obtenirComandes(nomsComandes);
    }
    public void printComandes() {
        CtrlComandes.printComandes();
    }
    public void printComandaUnica(String nomComanda) {
        CtrlComandes.printComandaUnica(nomComanda);
    }

    //Prestatgeria

    // Implementación de los métodos de Prestatgeria

    public void afegirProductePrestatgeria(String nomProducte, int quantitat, String idPrestatgeria) {
        // Código para agregar un producto a la prestatgeria
        CtrlPrestatgeria.afegirProducte(idPrestatgeria, nomProducte, quantitat);
    }

    public void moureProducteDeHueco(String id_prestatgeria, String nomProducte, int huecoOrigen, int huecoDestino) {
        // Código para mover el producto de un hueco a otro
        CtrlPrestatgeria.moureProducte(id_prestatgeria, huecoOrigen, huecoDestino);
    }
    //

    public void decrementarStockAProducte(String id_prestatgeria, String nomProducte, int quantitat) {
        // Código para decrementar el stock de un producto
        CtrlPrestatgeria.decrementar_quantitat_producte(id_prestatgeria, nomProducte, quantitat);
    }

    public void afegirStockAProducte(String id_prestatgeria,String nomProducte, int quantitat) {
        // Código para agregar stock a un producto
        int maxhueco = CtrlProducte.getmaxhueco(nomProducte);
        int stock = CtrlPrestatgeria.get_quantitat_producte(id_prestatgeria, nomProducte);
        if(stock == -1 || stock + quantitat > maxhueco){
            System.out.println("Error: No es pot afegir més stock del que hi cap a la prestatgeria");
            return;
        }
        else{
            CtrlPrestatgeria.incrementar_quantitat_producte(id_prestatgeria, nomProducte, quantitat);
        }
    }

    public void generarDistribucioBacktracking(String id_prestatgeria) {
        // Código para generar distribución usando el método Backtracking
        Vector<String> productes = CtrlPrestatgeria.getNomsProductes(id_prestatgeria);
        Set<String> fixats = CtrlPrestatgeria.getProductesFixats(id_prestatgeria);
        Vector<String> novadist = Algorismes.encontrarMejorDistribucionBacktracking(productes , fixats);
        CtrlPrestatgeria.setDistribucio(id_prestatgeria, novadist);
    }

    public void generarDistribucioHillClimbing(String id_prestatgeria) {
        Vector<String> productes = CtrlPrestatgeria.getNomsProductes(id_prestatgeria);
        Set<String> fixats = CtrlPrestatgeria.getProductesFixats(id_prestatgeria);
        Vector<String> novadist = Algorismes.encontrarMejorDistribucionHillClimbing(productes , fixats);
        CtrlPrestatgeria.setDistribucio(id_prestatgeria, novadist);

    }

    //Midaprestatgeria%midaPrestatge == 0
    public void afegirPrestatgeria(String idPrestatgeria, int midaPrestatge, int midaPrestatgeria) {
        CtrlPrestatgeria.afegirPrestatgeria(idPrestatgeria, midaPrestatgeria, midaPrestatge);;
    }

    public void eliminarPrestatgeria(String idPrestatgeria) {
        Map<String,Integer> producteseliminats = CtrlPrestatgeria.eliminarPrestatgeria(idPrestatgeria);
        for(Map.Entry<String, Integer> entry : producteseliminats.entrySet()){
            String nom = entry.getKey();
            int quantitat = entry.getValue();
            CtrlProducte.incrementar_stock(nom, quantitat);
        }
        System.out.println("Prestatgeria " + idPrestatgeria + " eliminada.");
    }

    public void afegirPrestatge(String idPrestatgeria, int prestatgeId) {
        // Código para agregar un prestatge
        System.out.println("Prestatge " + prestatgeId + " agregado a la prestatgeria " + idPrestatgeria + ".");
    }

    public void eliminarPrestatge(String idPrestatgeria, int prestatgeId) {
        // Código para eliminar un prestatge
        System.out.println("Prestatge " + prestatgeId + " eliminado de la prestatgeria " + idPrestatgeria + ".");
    }

    public void reposarPrestatgeria() {
        // Código para reponer la prestatgeria
        System.out.println("Prestatgeria repuesta.");
    }

    public void printPrestatgeria() {
        // Código para imprimir los detalles de la prestatgeria
        System.out.println("Detalles de la prestatgeria impresos.");
    }

    //Caixa
    public void afegir_producte_caixa(String nom_producte, int quantitat, String id_prestatgeria){

        if(CtrlProducte.existeix_producte(nom_producte)){
            int quantitat_ja_afegida = Caixa.get_quantitat(nom_producte, id_prestatgeria);
            if(!CtrlPrestatgeria.contains_producte(nom_producte, quantitat, id_prestatgeria, quantitat_ja_afegida)){
                System.out.println("Error: El producte no està a la prestatgeria o no hi ha suficient quantitat");
            }
            else{
                Caixa.afegir_producte(nom_producte, quantitat, id_prestatgeria);
            }

        }
        else{
            System.out.println("Error: El producte no existeix");
        }
    }
    // Retirar producto de la caja
    public void retirar_producte_caixa(String nom_producte, int quantitat, String id_prestatgeria) {
        Caixa.retirar_producte(nom_producte, quantitat, id_prestatgeria);
    }

    public void fixarProducte(String nomProducte, int hueco) {
        // Código para fijar el producto en un hueco
        System.out.println("Producto " + nomProducte + " fijado en el hueco " + hueco + ".");
    }

    public void desfixarProducte(String nomProducte) {
        // Código para desfijar el producto de un hueco
        System.out.println("Producto " + nomProducte + " desfijado del hueco.");
    }

    public void retirarProducteAMagatzem(String nomProducte) {
        // Código para retirar el producto y mandarlo a Magatzem
        System.out.println("Producto " + nomProducte + " retirado a Magatzem.");
    }

    // Consultar la cantidad de un producto específico en una estantería de la caja
    public int consultar_quantitat_caixa(String nom_producte, String id_prestatgeria) {
        return Caixa.get_quantitat(nom_producte, id_prestatgeria);
    }

    // Imprimir el ticket con el desglose de productos por estantería
    public void imprimir_ticket_per_prestatgeries() {
        Caixa.imprimir_ticket_per_prestatgeries();
    }

    // Imprimir el ticket con el total de cada producto en la caja
    public void imprimir_ticket_caixa() {
        Caixa.imprimirticket();
    }

    // Pagar y vaciar la caja, decrementando la cantidad de productos en las estanterías
    public void pagar_caixa() {
        Map<String, Map<String, Integer>> productes_venuts = Caixa.get_productes();
        for(Map.Entry<String, Map<String, Integer>> entry : productes_venuts.entrySet()){
            String id_prestatgeria = entry.getKey();
            Map<String, Integer> productes = entry.getValue();
            for(Map.Entry<String, Integer> entry2 : productes.entrySet()){
                String nom_producte = entry2.getKey();
                int quantitat = entry2.getValue();
                CtrlPrestatgeria.decrementar_quantitat_producte(id_prestatgeria, nom_producte, quantitat);
            }
        }
        Caixa.imprimirticket();
        Caixa.pagar();
    }


    //Magatzem (Productes)
    public void executarComandes(String[] noms) {
        Map<String, Comanda> comandesAExecutar = CtrlComandes.obtenirComandes(noms);
        CtrlProducte.executar_comandes(comandesAExecutar);
    }

    public void obtenirComandaAutomatica() {
        CtrlProducte.generarComandaAutomatica();
    }


    public void altaProducte(String nom, int mh, int mm) {
        CtrlProducte.altaProducte(nom, mh, mm);
    }

    public void eliminar_producte(String nom) {
        //Lo eliminamos del almacen
        CtrlProducte.eliminar_producte(nom);
        //Lo eliminamos de las prestatgerias
    }



    public void afegir_similitud(String nom1, String nom2, float value) {
        CtrlProducte.afegir_similitud(nom1, nom2, value);
    }

    public void eliminarSimilitud(String nom1, String nom2) {
        CtrlProducte.eliminarSimilitud(nom1, nom2);
    }
    public void modificarSimilitud(String nom1, String nom2, float value) {
        CtrlProducte.eliminarSimilitud(nom1, nom2);
    }

    public void print_producte(String nom) {
        CtrlProducte.imprimirProducte(nom);
    }

    public void printMagatzem() {

    }
}
