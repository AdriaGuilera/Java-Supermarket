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
        int maxhueco = CtrlProducte.getmaxhueco(nomProducte);
        int stock = CtrlProducte.get_stock(nomProducte);

        if(stock >= quantitat) {
            if (CtrlPrestatgeria.contains_producte(idPrestatgeria, nomProducte)) {
                int actual = CtrlPrestatgeria.get_quantitat_producte(idPrestatgeria, nomProducte);

                if (quantitat + actual > maxhueco) {
                    CtrlPrestatgeria.incrementar_quantitat_producte(idPrestatgeria, nomProducte, maxhueco - actual);
                    CtrlProducte.decrementar_stock(nomProducte, maxhueco - actual);
                } else {
                    CtrlPrestatgeria.incrementar_quantitat_producte(idPrestatgeria, nomProducte, quantitat);
                    CtrlProducte.decrementar_stock(nomProducte, quantitat);
                }
            } else {
                if (quantitat > maxhueco) {
                    CtrlPrestatgeria.afegirProducte(idPrestatgeria, nomProducte, maxhueco);
                    CtrlProducte.decrementar_stock(nomProducte, maxhueco);
                } else {
                    CtrlPrestatgeria.afegirProducte(idPrestatgeria, nomProducte, quantitat);
                    CtrlProducte.decrementar_stock(nomProducte, quantitat);
                }
            }
        }
        else{
            System.out.println("Error: No hi ha suficient stock del producte");
        }
        return;

    }

    public void moureProducteDeHueco(String id_prestatgeria, String nomProducte, int huecoOrigen, int huecoDestino) {
        // Código para mover el producto de un hueco a otro
        CtrlPrestatgeria.moureProducte(id_prestatgeria, huecoOrigen, huecoDestino);
    }
    //

    public void decrementarStockAProducte(String id_prestatgeria, String nomProducte, int quantitat) {
        // Código para decrementar el stock de un producto
        CtrlPrestatgeria.decrementar_quantitat_producte(id_prestatgeria, nomProducte, quantitat);
        CtrlProducte.incrementar_stock(nomProducte, quantitat);
    }


    public void generarDistribucioBacktracking(String id_prestatgeria) {
        // Código para generar distribución usando el método Backtracking
        Vector<String> productes = CtrlPrestatgeria.getNomsProductes(id_prestatgeria);
        Set<String> fixats = CtrlPrestatgeria.getProductesFixats(id_prestatgeria);
        System.out.println(productes);
        Vector<String> novadist = Algorismes.encontrarMejorDistribucionBacktracking(productes , fixats);
        System.out.println(novadist);
        CtrlPrestatgeria.setDistribucio(id_prestatgeria, novadist);
    }

    public void generarDistribucioHillClimbing(String id_prestatgeria) {
        Vector<String> productes = CtrlPrestatgeria.getNomsProductes(id_prestatgeria);
        Set<String> fixats = CtrlPrestatgeria.getProductesFixats(id_prestatgeria);
        System.out.println("HOLA");
        Vector<String> novadist = Algorismes.encontrarMejorDistribucionHillClimbing(productes , fixats);
        System.out.println("HOLA");
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
    }

    public void afegirPrestatge(String idPrestatgeria) {
        // Código para agregar un prestatge
        CtrlPrestatgeria.afegir_prestatge(idPrestatgeria);
    }

    public void eliminarPrestatge(String idPrestatgeria) {
        // Código para eliminar un prestatge
        Map<String,Integer> eliminats = CtrlPrestatgeria.eliminar_prestatge(idPrestatgeria);
        System.out.println(eliminats);

            for(Map.Entry<String, Integer> entry : eliminats.entrySet()){
                System.out.println("manuel");
                String nom = entry.getKey();
                int quantitat = entry.getValue();
                CtrlProducte.incrementar_stock(nom, quantitat);
            }


    }

    public void reposarPrestatgeria(String id) {
        Map<String,Integer> productes = CtrlPrestatgeria.getProductesPrestatgeria(id);
        for(Map.Entry<String, Integer> entry : productes.entrySet()){
            int stock = CtrlProducte.get_stock(entry.getKey());
            int max = CtrlProducte.getmaxhueco(entry.getKey());
            int actual = entry.getValue();
            if(actual < max){
                int aafegir = max - actual;
                if(aafegir <= stock){
                    CtrlPrestatgeria.incrementar_quantitat_producte(id, entry.getKey(), aafegir);
                    CtrlProducte.decrementar_stock(entry.getKey(), aafegir);
                }
                else{
                    CtrlPrestatgeria.incrementar_quantitat_producte(id, entry.getKey(), stock);
                    CtrlProducte.decrementar_stock(entry.getKey(), stock);
                }
            }
        }
    }
    public void fixarProducte(String id, String nomProducte) {
        CtrlPrestatgeria.fixarProducte(id, nomProducte);
    }

    public void desfixarProducte(String id, String nomProducte) {
        CtrlPrestatgeria.desfixarProducte(id, nomProducte);
    }

    public void retirarProducteAMagatzem(String id,String nomProducte) {
        Pair<String,Integer> producteretirat = CtrlPrestatgeria.retirarProductePrestatgeria(id, nomProducte);
        if(producteretirat.getKey() != null){
            String nom = producteretirat.getKey();
            int quantitat = producteretirat.getValue();
            CtrlProducte.incrementar_stock(nom, quantitat);
        }
    }

    public void printPrestatgeria(String id) {
        CtrlPrestatgeria.printPrestatgeria(id);
    }

    //Caixa
    public void afegir_producte_caixa(String nom_producte, int quantitat, String id_prestatgeria){

        if(CtrlProducte.existeix_producte(nom_producte)){
            int quantitat_ja_afegida = Caixa.get_quantitat(nom_producte, id_prestatgeria);
            if(!CtrlPrestatgeria.contains_quantitat(nom_producte, quantitat, id_prestatgeria, quantitat_ja_afegida)){
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

    public void obtenirComandaAutomatica(String nomComanda) {
        //Primero obtenemos un map de <string,int> con los productos y cantidades que faltan por rellenar;
        Map<String, Integer> productosFaltantes = CtrlProducte.obtenirComandaAutomatica();
        //Creamos una comanda con los productos
        CtrlComandes.crearComandaAutomatica(nomComanda, productosFaltantes);
    }


    public void altaProducte(String nom, int mh, int mm) {
        CtrlProducte.altaProducte(nom, mh, mm);
    }

    public void eliminar_producte(String nom) {
        //Lo eliminamos del almacen
        CtrlPrestatgeria.eliminar_producte(nom);
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
        CtrlProducte.imprimirMagatzem();
    }
}
