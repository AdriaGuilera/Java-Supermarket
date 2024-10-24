import java.util.Map;

public class CtrlDomini {

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

    public void mourePrestatgeria(String nom, int quantitat, String id_prest) {
        int max_hueco = CtrlProducte.comprovaQuantitats(nom, quantitat);
        if (max_hueco == -1) System.out.println("Error: El producte no es pot col·locar");
        else {
            CtrlPrestatgeria
        }
    }
}
