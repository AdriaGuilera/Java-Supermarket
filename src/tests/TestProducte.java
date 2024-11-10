package tests;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import classes.Producte;


public class TestProducte {

    private Producte producte;

    @Before
    public void setUp() {
        // Este método se ejecuta antes de cada test para inicializar un nuevo objeto Producte
        producte = new Producte("Producto A", 100, 500);
    }

    @Test
    public void testConstructor() {
        assertEquals("Producto A", producte.get_nom());
        assertEquals(100, producte.get_max_hueco());
        assertEquals(500, producte.get_max_magatzem());
        assertEquals(0, producte.get_stock());
    }

    @Test
    public void testModNom() {
        producte.mod_nom("Producto B");
        assertEquals("Producto B", producte.get_nom());
    }

    @Test
    public void testModMaxHueco() {
        producte.mod_mh(150);
        assertEquals(150, producte.get_max_hueco());
    }

    @Test
    public void testModMaxMagatzem() {
        producte.mod_mm(600);
        assertEquals(600, producte.get_max_magatzem());
    }

    @Test
    public void testModStock() {
        producte.mod_stock(200);
        assertEquals(200, producte.get_stock());
    }

    @Test
    public void testIncrementarStock() {
        producte.incrementar_stock(50);
        assertEquals(50, producte.get_stock());
    }

    @Test
    public void testDecrementarStock() {
        producte.mod_stock(100); // Primero, ponemos el stock en 100
        producte.decrementar_stock(30);
        assertEquals(70, producte.get_stock());
    }

    @Test
    public void testAfegirSimilitud() {
        producte.afegir_similitud("Producto B", 0.8f);
        assertEquals(0.8f, producte.getSimilitud("Producto B"), 0.001); // Agregamos un margen de error en la comparación flotante
    }

    @Test
    public void testAfegirSimilitudSobreescribeValor() {
        producte.afegir_similitud("Producto B", 0.8f);
        producte.afegir_similitud("Producto B", 0.9f); // Sobrescribe el valor de la similitud
        assertEquals(0.9f, producte.getSimilitud("Producto B"), 0.001); // Agregamos un margen de error en la comparación flotante
    }

    @Test
    public void testImprimirSimilituds() {
        producte.afegir_similitud("Producto B", 0.8f);
        producte.afegir_similitud("Producto C", 0.5f);

        // Este test verifica que el método no lance excepciones al imprimir las similitudes
        // Para verificar la salida exacta, necesitaríamos redirigir System.out, pero aquí solo verificamos que no falle
        producte.imprimir_similituds();
    }
}

