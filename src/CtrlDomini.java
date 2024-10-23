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







}
