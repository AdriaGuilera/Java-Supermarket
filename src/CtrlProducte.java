import java.util.Map;
import java.util.HashMap;

public class CtrlProducte {

    //En aquest map hi ha tots els productes, tot i que n'hi hagi 0 al magatzem
    private static Map<String, Producte> productes_magatzem;

    public static void incrementar_stock(String nomP, int quantitat) {

    }

    public static void decrementar_stock(String nomP, int quantitat) {

    }

    public static void imprimirProducte(String nom) {

    }

    public static int comprovaQuantitats(String nom, int quantitat) {
        Producte p = productes_magatzem.get(nom);
        if (quantitat > p.get_max_hueco() || quantitat > p.get_stock()) return -1;
        return p.get_max_hueco();
    }

    public static void generarComandaAutomatica() {

    }

    public static void executar_comandes(Map<String, Comanda> comandes) {

    }

    public static void eliminar_producte(String nom) {

    }

    public static void altaproducte(String nom, Tcategoria categoria, float pv, float pc, int mh) {

    }

    public static List<String> Buscarproducte(List<Tcategoria> categoria, float pvm, float pvm2, float pcm, float pcm2) {

    }

    public static void modificarproducte(String nom, Tcategoria categoria, float pv, float pc, int mh) {

    }

}