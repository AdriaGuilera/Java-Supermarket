package controladors;

import classes.Comanda;
import classes.Producte;
import classes.Tcategoria;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class CtrlProducte {

    //En aquest map hi ha tots els productes, tot i que n'hi hagi 0 al magatzem
    private static Map<String, Producte> productes_magatzem;

    public static void decrementar_stock(String nomP, int quantitat) {
        Producte p = productes_magatzem.get(nomP);
        p.decrementar_stock(quantitat);
    }

    public static void imprimirProducte(String nom) {
        Producte p = productes_magatzem.get(nom);
        if (p == null) System.out.println("Error: el producte no existeix");
        else {
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
    }

    public static int comprovaQuantitats(String nom, int quantitat) {
        Producte p = productes_magatzem.get(nom);
        if (quantitat > p.get_max_hueco() || quantitat > p.get_stock()) return -1;
        return p.get_max_hueco();
    }

    public static void generarComandaAutomatica() {
        productes_magatzem.forEach((key, value) -> {
            value.mod_stock(value.get_max_magatzem());
        });
    }

    public static String executar_comandes(Map<String, Comanda> comandes) {
        for (Map.Entry<String, Comanda> comanda : comandes.entrySet()) {
            for (Map.Entry<String, Integer> ordre : comanda.getValue().getOrdres().entrySet()) {
                String nom = ordre.getKey();
                int quant = ordre.getValue();
                Producte p = productes_magatzem.get(nom);
                if (p.get_stock() + quant > p.get_max_magatzem()) return "Error: les comandes no caben al magatzem";
                p.incrementar_stock(quant);
            }
        }
        return "Les comandes s'han executat correctament";
    }

    //Si no existia no fa res
    public static void eliminar_producte(String nom) {
        productes_magatzem.remove(nom);
    }

    public static void altaProducte(String nom, Tcategoria categoria, float pv, float pc, int mh, int mm) {
        if (productes_magatzem.containsKey(nom)) {
            System.out.println("Error: Ja existeix el producte\n");
            return;
        }
        Producte p = new Producte(nom, categoria, pv, pc, mh, mm);
        productes_magatzem.put(nom, p);
    }

    /*
       Si s'han introduït categories, es mostren els productes que pertanyin a una de les categories
       introduïdes. Si s'han introduït pvm i pvm2, es mostren els productes amb preu de venta entre pvm
       i pvm2. Idem per pcm i pcm2.
    */
    public static List<String> buscarProducte(List<Tcategoria> categoria, Float pvm, Float pvm2, Float pcm, Float pcm2) {
        Map<String, Boolean> correcte = new HashMap<String, Boolean>();
        productes_magatzem.forEach((key, value) -> {
            if (value.get_stock() != 0) correcte.put(key, true);
        });
        if (categoria != null) {
            productes_magatzem.forEach((key, value) -> {
                if (correcte.get(key)) {
                    boolean trobat = false;
                    for (Tcategoria cat : categoria) {
                        if (value.get_categoria() == cat) trobat = true;
                    }
                    if (!trobat) correcte.put(key, false);
                }
            });
        }
        if (pvm != null && pvm2 != null) {
            productes_magatzem.forEach((key, value) -> {
                if (correcte.get(key) && (value.get_preu_venda() < pvm || value.get_preu_venda() < pvm2)) correcte.put(key, false);
            });
        }
        if (pcm != null && pcm2 != null) {
            productes_magatzem.forEach((key, value) -> {
                if (correcte.get(key) && (value.get_preu_compra() < pcm || value.get_preu_compra() < pcm2)) correcte.put(key, false);
            });
        }
        List<String> s = Arrays.asList();
        correcte.forEach((key, value) -> {
            if (value) s.add(key);
        });
        return s;
    }

    public static void modificarProducte(String nom, String nou_nom, Tcategoria categoria, Float pc, Float pv, Integer mh, Integer mm, Integer sm) {
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

    public String afegir_similitud(String nom1, String nom2, float value) {
        if (nom1 == nom2) return "Error: els dos productes són el mateix";
        Producte p1 = productes_magatzem.get(nom1);
        Producte p2 = productes_magatzem.get(nom2);
        if (p1 == null || p2 == null) return "Error: algun dels productes no existeix";
        p1.afegir_similitud(nom2, value);
        p2.afegir_similitud(nom1, value);
        return "La similitud s'ha afegit correctament";
    }


    public static double obtenir_similitud(String nom1, String nom2) {
        return productes_magatzem.get(nom1).getSimilitud(nom2);
    }
}