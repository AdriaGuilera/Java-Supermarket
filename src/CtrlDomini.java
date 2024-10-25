import java.util.Map;

public class CtrlDomini {
    public CtrlProducte CtrlProducte;
    public CtrlPrestatgeria CtrlPrestatgeria;
    public CtrlCaixa CtrlCaixa;

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


}
