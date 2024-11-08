package controladors;

public class CtrlCaixa {
    public classes.Caixa Caixa;
    public void afegir_producte(String nom_producte, double preu, int quantitat, String id_prestatgeria) {
        Caixa.afegir_producte(nom_producte, preu, quantitat, id_prestatgeria);
    }
    public int get_quantitat(String nom_producte, String id_prestatgeria) {
        return Caixa.get_quantitat(nom_producte, id_prestatgeria);
    }
}
