import java.util.Map;
import java.util.HashMap;

public class CtrlProducte {

    //En aquest map hi ha tots els productes, tot i que n'hi hagi 0 al magatzem
    private static Map<String, Producte> productes_magatzem;

    public static void incrementar_stock(String nomP, int quantitat) {
        Producte p = productes_magatzem.get(nomP);
        p.mod_stock(p.get_stock() + quantitat);
    }

    public static void decrementar_stock(String nomP, int quantitat) {
        Producte p = productes_magatzem.get(nomP);
        p.mod_stock(p.get_stock() - quantitat);
    }

    public static void imprimirProducte(String nom) {
        Producte p = productes_magatzem.get(nom);
        System.out.println("Dades del producte");
        System.out.printf("Nom: %s\n", nom);
        System.out.printf("Categoria: %s\n", p.get_categoria());
        System.out.printf("Preu compra: %f\n", p.get_preu_compra());
        System.out.printf("Preu venda: %f\n", p.get_preu_venda());
        System.out.printf("Màxima capacitat en un forat: %d\n", p.get_max_hueco());
        System.out.printf("Stock al magatzem: %d\n", p.get_stock());
        System.out.printf("Màxima capacitat al magatzem: %d\n", p.get_max_magatzem());
        p.imprimir_similituds();
    }

    public static int comprovaQuantitats(String nom, int quantitat) {
        Producte p = productes_magatzem.get(nom);
        if (quantitat > p.get_max_hueco() || quantitat > p.get_stock()) return -1;
        return p.get_max_hueco();
    }

    public static void generarComandaAutomatica() {
        productes_magatzem.forEach((value) -> {
            value.mod_stock(value.get_max_magatzem());
        });
    }

    //pq map de string comanda i no llista de comandes?
    public static void executar_comandes(Map<String, Comanda> comandes) {
        comandes.forEach((value) -> {
            value.getOrdres()
        });
    }

    //Si no existia no fa res
    public static void eliminar_producte(String nom) {
        productes_magatzem.remove(nom);
    }

    public static void altaproducte(String nom, Tcategoria categoria, float pv, float pc, int mh, int mm) {
        if (productes_magatzem.containsKey(nom)) {
            System.out.println("Error: Ja existeix el producte\n");
            return;
        }
        Producte p = new Producte(nom, categoria, pv, pc, mh, mm);
        productes_magatzem.put(nom, p);
    }

    public static List<String> Buscarproducte(List<Tcategoria> categoria, float pvm, float pvm2, float pcm, float pcm2) {

    }

    public static void modificarproducte(String nom, String nou_nom, Tcategoria categoria, Float pc, Float pv, Integer mh, Integer mm, Integer sm) {
        Producte p = productes_magatzem.get(nom);
        if (nou_nom != null) p.mod_nom(nou_nom);
        if (categoria != null) p.mod_cat(categoria);
        if (pc != null) p.mod_preu_compra(pc);
        if (pv != null) p.mod_preu_venda(pv);
        if (mh != null) p.mod_mh(mh);
        if (mm != null) p.mod_mm(mm);
        if (sm != null) p.mod_stock(sm);
    }

    public boolean existeix_producte(String nom) {
        return productes_magatzem.containsKey(nom);
    }

    public double getpreu(String nom) {
        return productes_magatzem.get(nom).get_preu_venda();
    }

}