package controladors;


import Exepcions.ProductNotFoundMagatzemException;
import Exepcions.ProducteJaExisteixException;
import Exepcions.calculMateixosProductesSimilitud;
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
    public void incrementar_stock(String nomP, int quantitat) {
        Producte p = productes_magatzem.get(nomP);
        int stock = p.get_stock();
        int max = p.get_max_magatzem();
        if(stock + quantitat > max) {
            p.mod_stock(max);
            System.out.println("Error: la quantitat a afegir supera la capacitat del magatzem, stock set to max");
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
    public void eliminar_producte(String nomProducte) throws ProductNotFoundMagatzemException {
        if (productes_magatzem.containsKey(nomProducte)) {
            productes_magatzem.remove(nomProducte);
            System.out.println("Producto '" + nomProducte + "' eliminado correctamente.");
        } else {
            throw new ProductNotFoundMagatzemException(nomProducte);
        }
    }

    public void altaProducte(String nomProducte, int mh, int mm) throws ProducteJaExisteixException{
        if (productes_magatzem.containsKey(nomProducte)) {
            throw new ProducteJaExisteixException(nomProducte);
        }
        Producte p = new Producte(nomProducte, mh, mm);
        productes_magatzem.put(nomProducte, p);
        System.out.println("Producto añadido al Almacén!");
    }






    public void modificarProducte(String nom, String nou_nom, Integer mh, Integer mm) {
        Producte p = productes_magatzem.get(nom);
        if (nou_nom != null) p.mod_nom(nou_nom);
        if (mh != null) p.mod_max_hueco(mh);
        if (mm != null) p.mod_max_magatzem(mm);
    }

    public boolean existeix_producte(String nom) {
        return productes_magatzem.containsKey(nom);
    }



    public void afegir_similitud(String nom1, String nom2, float value) throws ProductNotFoundMagatzemException, calculMateixosProductesSimilitud {
        if (nom1 == nom2) throw new calculMateixosProductesSimilitud(nom1);
        Producte p1 = productes_magatzem.get(nom1);
        Producte p2 = productes_magatzem.get(nom2);
        if (!productes_magatzem.containsKey(nom1) ) throw new ProductNotFoundMagatzemException(nom1);
        else if(!productes_magatzem.containsKey(nom2)) throw new ProductNotFoundMagatzemException(nom2);
        else {
            p1.afegir_similitud(nom2, value);
            p2.afegir_similitud(nom1, value);
            System.out.println( "La similitud s'ha afegit correctament");
        }
    }
    public void eliminarSimilitud(String nom1, String nom2) throws ProductNotFoundMagatzemException, calculMateixosProductesSimilitud{
        if (nom1 == nom2) throw new calculMateixosProductesSimilitud(nom1);
        Producte p1 = productes_magatzem.get(nom1);
        Producte p2 = productes_magatzem.get(nom2);
        if (!productes_magatzem.containsKey(nom1) ) throw new ProductNotFoundMagatzemException(nom1);
        else if(!productes_magatzem.containsKey(nom2)) throw new ProductNotFoundMagatzemException(nom2);
        else {
            p1.eliminarSimilitud(nom2);
            p2.eliminarSimilitud(nom1);
            System.out.println( "La similitud s'ha eliminat correctament");
        }
    }

    public void modificarSimilitud(String nom1, String nom2, float value) throws  ProductNotFoundMagatzemException, calculMateixosProductesSimilitud {
        if (nom1 == nom2) throw new calculMateixosProductesSimilitud(nom1);
        Producte p1 = productes_magatzem.get(nom1);
        Producte p2 = productes_magatzem.get(nom2);
        if (!productes_magatzem.containsKey(nom1) ) throw new ProductNotFoundMagatzemException(nom1);
        else if(!productes_magatzem.containsKey(nom2)) throw new ProductNotFoundMagatzemException(nom2);
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
    public int getmaxhueco(String nom) {
        return productes_magatzem.get(nom).get_max_hueco();
    }
    public int get_stock(String nom) {
        return productes_magatzem.get(nom).get_stock();
    }
    public int getmaxmagatzem(String nom) {
        return productes_magatzem.get(nom).get_max_magatzem();
    }

    public int get_stock_magatzem(String key) throws ProductNotFoundMagatzemException {
        Producte producte = productes_magatzem.get(key);
        if (producte == null) {
            throw new ProductNotFoundMagatzemException(key);
        }
        return producte.get_stock();
    }
}