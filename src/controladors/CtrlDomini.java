package controladors;

import java.util.Map;

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
    public Map<String, Comanda> obtenirComandes(String[] nomsComandes) {
        return CtrlComandes.obtenirComandes(nomsComandes);
    }
    public void consultarComandes() {
        CtrlComandes.consultarComandes();
    }

    //Prestatgeria
    //Mou producte del magatzem a la prestatgeria
    public void mourePrestatgeria(String nom, int quantitat, String id_prest) {
        int max_hueco = CtrlProducte.comprovaQuantitats(nom, quantitat);
        if (max_hueco == -1) System.out.println("Error: El producte no hi cap");
        else {
            if (CtrlPrestatgeria.moureProducte(nom, quantitat, id_prest, max_hueco) == -1) {
                System.out.println("Error: El producte no hi cap");
            }
            else CtrlProducte.decrementar_stock(nom, quantitat);
        }
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

    // Pagar y vaciar la caja
    public void pagar_caixa() {
        Caixa.pagar();
    }




    //Magatzem (Productes)
    public void generarComandaAutomatica() {
        CtrlProducte.generarComandaAutomatica();
    }

    public void executarComandes(String[] noms) {
        Map<String, Comanda> comandesAExecutar = CtrlComandes.obtenirComandes(noms);
        String s = CtrlProducte.executar_comandes(comandesAExecutar);
        System.out.println(s);
    }

    public void modificarProducte(String nom, String nou_nom, Integer mh, Integer mm) {
        CtrlProducte.modificarProducte(nom, nou_nom,  mh, mm);
    }

    public void eliminar_producte(String nom) {
        CtrlProducte.eliminar_producte(nom);
    }

    public void altaProducte(String nom, int mh, int mm) {
        CtrlProducte.altaProducte(nom, mh, mm);
    }

    public void afegir_similitud(String nom1, String nom2, float value) {
        CtrlProducte.afegir_similitud(nom1, nom2, value);
    }

    public void imprimir_producte(String nom) {
        CtrlProducte.imprimirProducte(nom);
    }

    public void imprimir_magatzem(String nom) {
        CtrlProducte.imprimirMagatzem(nom);
    }
}
