package controladors;

import Exepcions.MaxMagatzemWarning;
import Exepcions.ProductNotFound;
import classes.Comanda;
import classes.Producte;

import java.util.HashMap;
import java.util.Map;

public class CtrlProducte {

    //En aquest map hi ha tots els productes, tot i que n'hi hagi 0 al magatzem
    private static Map<String, Producte> productes_magatzem = new HashMap<>();

    public void decrementar_stock(String nomP, int quantitat) {
        Producte p = productes_magatzem.get(nomP);
        p.decrementar_stock(quantitat);
    }
    public void incrementar_stock(String nomP, int quantitat) throws MaxMagatzemWarning, ProductNotFound {
        if(!existeix_producte(nomP)) {
            throw new ProductNotFound(nomP);
        }
        Producte p = productes_magatzem.get(nomP);
        int stock = p.get_stock();
        int max = p.get_max_magatzem();
        if(stock + quantitat > max) {
            p.mod_stock(max);
            throw new MaxMagatzemWarning(nomP);
        }
        else p.incrementar_stock(quantitat);

    }

    public void imprimirProducte(String nom) {
        Producte p = productes_magatzem.get(nom);
        if (p == null) System.out.println("Error: el producte no existeix");
        else {
            System.out.println("Dades del producte");
            System.out.printf("Nom: %s\n", nom);
            System.out.printf("Màxima capacitat en el magatzem: %d\n", p.get_max_magatzem());
            System.out.printf("Stock al magatzem: %d\n", p.get_stock());
            System.out.printf("Màxima capacitat a l'estanteria: %d\n", p.get_max_hueco());
            p.imprimir_similituds();
        }

    }

    public void imprimirMagatzem() {
        // Recorremos todos los productos del magatzem
        for (String nom : productes_magatzem.keySet()) {
            // Llamamos a la función imprimirProducte para cada producto
            imprimirProducte(nom);
        }
    }

    public void executar_comandes(Map<String, Comanda> comandes) {
        for (Map.Entry<String, Comanda> comanda : comandes.entrySet()) {
            for (Map.Entry<String, Integer> ordre : comanda.getValue().getOrdres().entrySet()) {
                String nom = ordre.getKey();
                int quant = ordre.getValue();
                Producte p = productes_magatzem.get(nom);
                if(p==null)  {System.out.println( "El producte "+nom+" no existeix"); return;}
                if (p.get_stock() + quant > p.get_max_magatzem()) p.incrementar_stock(p.get_max_magatzem()-p.get_stock());
                else p.incrementar_stock(quant);
            }
        }
        System.out.println( "Les comandes s'han executat correctament");
    }

    public Map<String,Integer> obtenirComandaAutomatica() {
        Map<String,Integer> productosRestantes = new HashMap<>();
        productes_magatzem.forEach((key, value) -> {
            int maximo = value.get_max_magatzem();
            int diferencia = maximo - value.get_stock();
            productosRestantes.put(key,diferencia);
        });
        return productosRestantes;
    }



    //Si no existia no fa res
    public void eliminar_producte(String nom) {
        if (productes_magatzem.containsKey(nom)) {
            productes_magatzem.remove(nom);
            System.out.println("Producto '" + nom + "' eliminado correctamente.");
        } else {
            System.out.println("Producto '" + nom + "' no encontrado en el almacén.");
        }
    }

    public void altaProducte(String nom, int mh, int mm) {
        if (productes_magatzem.containsKey(nom)) {
            System.out.println("Error: Ja existeix el producte\n");
            return;
        }
        Producte p = new Producte(nom, mh, mm);
        productes_magatzem.put(nom, p);
        System.out.println("Producto añadido al Almacén!");
    }






    public void modificarProducte(String nom, String nou_nom, Integer mh, Integer mm) {
        Producte p = productes_magatzem.get(nom);
        if (nou_nom != null) p.mod_nom(nou_nom);
        if (mh != null) p.mod_mh(mh);
        if (mm != null) p.mod_mm(mm);
    }

    public boolean existeix_producte(String nom) {
        return productes_magatzem.containsKey(nom);
    }



    public void afegir_similitud(String nom1, String nom2, float value) {
        if (nom1 == nom2) System.out.println( "Error: els dos productes són el mateix");
        Producte p1 = productes_magatzem.get(nom1);
        Producte p2 = productes_magatzem.get(nom2);
        if (p1 == null || p2 == null) System.out.println( "Error: algun dels productes no existeix");
        else {
            p1.afegir_similitud(nom2, value);
            p2.afegir_similitud(nom1, value);
            System.out.println( "La similitud s'ha afegit correctament");
        }
    }
    public void eliminarSimilitud(String nom1, String nom2) {
        if (nom1 == nom2) System.out.println( "Error: els dos productes són el mateix");
        Producte p1 = productes_magatzem.get(nom1);
        Producte p2 = productes_magatzem.get(nom2);
        if (p1 == null || p2 == null) System.out.println( "Error: algun dels productes no existeix");
        else {
            p1.eliminarSimilitud(nom2);
            p2.eliminarSimilitud(nom1);
            System.out.println( "La similitud s'ha afegit correctament");
        }
    }

    public void modificarSimilitud(String nom1, String nom2, float value) {
        if (nom1 == nom2) System.out.println( "Error: els dos productes són el mateix");
        Producte p1 = productes_magatzem.get(nom1);
        Producte p2 = productes_magatzem.get(nom2);
        if (p1 == null || p2 == null) System.out.println( "Error: algun dels productes no existeix");
        else {
            p1.modificarSimilitud(nom2, value);
            p2.modificarSimilitud(nom1, value);
            System.out.println( "La similitud s'ha afegit correctament");
        }
    }


    public static double obtenir_similitud(String nom1, String nom2) {
        if(nom1==null ||nom2==null) return 0;
        return productes_magatzem.get(nom1).getSimilitud(nom2);
    }
    public int getmaxhueco(String nom) throws ProductNotFound {
        if(productes_magatzem.get(nom)==null) throw new ProductNotFound(nom);
        return productes_magatzem.get(nom).get_max_hueco();
    }
    public int get_stock_magatzem(String nom) throws ProductNotFound {
        if(productes_magatzem.get(nom)==null) throw new ProductNotFound(nom);
        return productes_magatzem.get(nom).get_stock();
    }
}