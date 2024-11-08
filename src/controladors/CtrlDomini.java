package controladors;

import java.util.List;
import java.util.Map;

import classes.*;
import classes.Tcategoria;
public class CtrlDomini {
    public CtrlProducte CtrlProducte;
    public CtrlPrestatgeria CtrlPrestatgeria;
    public controladors.CtrlCaixa CtrlCaixa;

    //Funciones del CtrlComandes

    public void crearComanda(String nomComanda) {
        String mensaje = CtrlComandes.crearComanda(nomComanda);
    }
    public void eliminarComanda(String nomComanda) {
        String mensaje = CtrlComandes.eliminarComanda(nomComanda);
    }
    public void afegirProducteComanda(String nomComanda, String nomProducte, int quantitat) {
        String mensaje = CtrlComandes.afegirProducteComanda(nomComanda,nomProducte,quantitat);
    }
    public Map<String, Comanda> obtenirComandes(String[] nomsComandes) {
        return CtrlComandes.obtenirComandes(nomsComandes);
    }

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
    public void afegir_producte_caixa(String nom_producte, int quantitat, String id_prestatgeria){

        if(CtrlProducte.existeix_producte(nom_producte)){
            int quantitat_ja_afegida = CtrlCaixa.get_quantitat(nom_producte, id_prestatgeria);
            if(!CtrlPrestatgeria.contains_producte(nom_producte, quantitat, id_prestatgeria, quantitat_ja_afegida)){
                System.out.println("Error: El producte no està a la prestatgeria o no hi ha suficient quantitat");
            }
            else{
                double preu = CtrlProducte.getpreu(nom_producte);
                CtrlCaixa.afegir_producte(nom_producte, preu, quantitat, id_prestatgeria);
            }

        }
        else{
            System.out.println("Error: El producte no existeix");
        }
    }

    public void generarComandaAutomatica() {
        CtrlProducte.generarComandaAutomatica();
    }

    public void executarComandes(String[] noms) {
        Map<String, Comanda> comandesAExecutar = CtrlComandes.obtenirComandes(noms);
        String s = CtrlProducte.executar_comandes(comandesAExecutar);
        System.out.println(s);
    }

    public void buscarProducte(List<Tcategoria> categoria, Float pvm, Float pvm2, Float pcm, Float pcm2) {
        List<String> noms = CtrlProducte.buscarProducte(categoria, pvm, pvm2, pcm, pcm2);
        if (noms.size() == 0) System.out.println("No s'han trobat productes");
        else {
            for (String nom : noms) System.out.println(nom);
        }
    }

    public void modificarProducte(String nom, String nou_nom, Tcategoria categoria, Float pc, Float pv, Integer mh, Integer mm, Integer sm) {
        CtrlProducte.modificarProducte(nom, nou_nom, categoria, pc, pv, mh, mm, sm);
    }

    public void eliminar_producte(String nom) {
        CtrlProducte.eliminar_producte(nom);
    }

    public void altaProducte(String nom, Tcategoria categoria, float pv, float pc, int mh, int mm) {
        CtrlProducte.altaProducte(nom, categoria, pv, pc, mh, mm);
    }

    public void afegir_similitud(String nom1, String nom2, float value) {
        String s = CtrlProducte.afegir_similitud(nom1, nom2, value);
        System.out.println(s);
    }

    public void imprimir_producte(String nom) {
        CtrlProducte.imprimirProducte(nom);
    }
}
