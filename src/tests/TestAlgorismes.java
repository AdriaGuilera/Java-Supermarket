package tests;

import classes.Algorismes;
import classes.Producte;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class TestAlgorismes {

    private Algorismes algorismes;

    @Before
    public void setUp() {
        algorismes = new Algorismes();
    }

    @Test
    public void testEncontrarMejorDistribucionBacktracking1() {
        // Configuración inicial
        Vector<String> distribucion = new Vector<>(Arrays.asList("A", "B", "C", "D", "E"));
        Set<String> productosFijos = new HashSet<>(Arrays.asList("A")); // Producto fijo en la posición inicial
        Map<String, Producte> productesMagatzem = new HashMap<>();

        // Creando productos con similitudes
        Producte a = new Producte("A", 10, 100,0);
        Producte b = new Producte("B", 10, 100,0);
        Producte c = new Producte("C", 10, 100,0);
        Producte d = new Producte("D", 10, 100,0);
        Producte e = new Producte("E", 10, 100,0);

        a.afegir_similitud("B", 0.9f);
        a.afegir_similitud("C", 0.8f);
        a.afegir_similitud("D", 0.7f);
        a.afegir_similitud("E", 0.6f);

        b.afegir_similitud("A", 0.9f);
        b.afegir_similitud("C", 0.85f);
        b.afegir_similitud("D", 0.8f);
        b.afegir_similitud("E", 0.75f);

        c.afegir_similitud("A", 0.8f);
        c.afegir_similitud("B", 0.85f);
        c.afegir_similitud("D", 0.9f);
        c.afegir_similitud("E", 0.88f);

        d.afegir_similitud("A", 0.7f);
        d.afegir_similitud("B", 0.8f);
        d.afegir_similitud("C", 0.9f);
        d.afegir_similitud("E", 0.95f);

        e.afegir_similitud("A", 0.6f);
        e.afegir_similitud("B", 0.75f);
        e.afegir_similitud("C", 0.88f);
        e.afegir_similitud("D", 0.95f);

        productesMagatzem.put("A", a);
        productesMagatzem.put("B", b);
        productesMagatzem.put("C", c);
        productesMagatzem.put("D", d);
        productesMagatzem.put("E", e);

        // Llamar al método
        Vector<String> mejorDistribucion = algorismes.encontrarMejorDistribucionBacktracking(distribucion, productosFijos, productesMagatzem);
        Vector<String> esperado = new Vector<>(Arrays.asList("A", "B", "D", "E", "C"));

        // Verificar resultado
        assertNotNull(mejorDistribucion);
        assertTrue(mejorDistribucion.containsAll(distribucion)); // Verificar que contiene los mismos productos
        assertEquals("A", mejorDistribucion.get(0)); // Producto fijo sigue en su lugar
        assertEquals( esperado,mejorDistribucion);
    }


    @Test
    public void testEncontrarMejorDistribucionBacktrackingConProductosFijos() {
        Vector<String> distribucion = new Vector<>(Arrays.asList("A", "B", "C", "D", "E"));
        Set<String> productosFijos = new HashSet<>(Arrays.asList("A", "C")); // "A" y "C" están fijos
        Map<String, Producte> productesMagatzem = new HashMap<>();

        // Creando productos con similitudes
        Producte a = new Producte("A", 10, 100,0);
        Producte b = new Producte("B", 10, 100,0);
        Producte c = new Producte("C", 10, 100,0);
        Producte d = new Producte("D", 10, 100,0);
        Producte e = new Producte("E", 10, 100,0);

        a.afegir_similitud("D", 1.0f);
        d.afegir_similitud("A",0.3f);

        a.afegir_similitud("E", 0.8f);
        e.afegir_similitud("A",0.8f);


        productesMagatzem.put("A", a);
        productesMagatzem.put("B", b);
        productesMagatzem.put("C", c);
        productesMagatzem.put("D", d);
        productesMagatzem.put("E", e);

        Vector<String> mejorDistribucion = algorismes.encontrarMejorDistribucionBacktracking(distribucion, productosFijos, productesMagatzem);

        assertNotNull(mejorDistribucion);
        assertEquals("A", mejorDistribucion.get(0)); // Verificar que "A" está fijo
        assertEquals("C", mejorDistribucion.get(2)); // Verificar que "C" está fijo
    }


    @Test
    public void testDistribucionMixtaBacktracking1() {
        Vector<String> distribucion = new Vector<>(Arrays.asList("A", null, null, "D", "E"));
        Set<String> productosFijos = new HashSet<>(Arrays.asList());
        Map<String, Producte> productesMagatzem = new HashMap<>();

        Producte a = new Producte("A", 10, 100,0);
        Producte b = new Producte("B", 10, 100,0);
        Producte c = new Producte("C", 10, 100,0);
        Producte d = new Producte("D", 10, 100,0);
        Producte e = new Producte("E", 10, 100,0);

        a.afegir_similitud("D", 1.0f);
        b.afegir_similitud("A",0.3f);

        a.afegir_similitud("E", 0.8f);
        d.afegir_similitud("A",0.8f);


        productesMagatzem.put("A", a);
        productesMagatzem.put("B", b);
        productesMagatzem.put("C", c);
        productesMagatzem.put("D", d);
        productesMagatzem.put("E", e);

        Vector<String> mejorDistribucion = algorismes.encontrarMejorDistribucionBacktracking(distribucion, productosFijos, productesMagatzem);
        Vector<String> esperado1 = new Vector<>(Arrays.asList("A", "D" , null, null, "E"));

        // Validaciones
        assertNotNull(mejorDistribucion);
        assertFalse(mejorDistribucion.contains("B") || mejorDistribucion.contains("C"));
        assertEquals(esperado1, mejorDistribucion);
    }


    @Test
    public void testDistribucionMixtaBacktracking2() {
        Vector<String> distribucion = new Vector<>(Arrays.asList("A", "D", null, "E", "C"));
        Set<String> productosFijos = new HashSet<>(Arrays.asList("D"));
        Map<String, Producte> productesMagatzem = new HashMap<>();

        // Crear productos
        Producte a = new Producte("A", 10, 100,0);
        Producte b = new Producte("B", 10, 100,0);
        Producte c = new Producte("C", 10, 100,0);
        Producte d = new Producte("D", 10, 100,0);
        Producte e = new Producte("E", 10, 100,0);

        // Definir relaciones explícitas
        a.afegir_similitud("B", 1.4f);
        a.afegir_similitud("C", 1.6f);
        a.afegir_similitud("D", 0.9f);
        a.afegir_similitud("E", 1.0f);

        b.afegir_similitud("A", 1.4f);
        b.afegir_similitud("C", 0.3f);
        b.afegir_similitud("D", 1.4f);
        b.afegir_similitud("E", 1.9f);

        c.afegir_similitud("A", 1.6f);
        c.afegir_similitud("B", 0.3f);
        c.afegir_similitud("D", 0.6f);
        c.afegir_similitud("E", 1.7f);

        d.afegir_similitud("A", 0.9f);
        d.afegir_similitud("B", 1.4f);
        d.afegir_similitud("C", 0.6f);
        d.afegir_similitud("E", 1.4f);

        e.afegir_similitud("A", 1.0f);
        e.afegir_similitud("B", 1.9f);
        e.afegir_similitud("C", 1.7f);
        e.afegir_similitud("D", 1.4f);

        productesMagatzem.put("A", a);
        productesMagatzem.put("B", b);
        productesMagatzem.put("C", c);
        productesMagatzem.put("D", d);
        productesMagatzem.put("E", e);

        Vector<String> mejorDistribucion = algorismes.encontrarMejorDistribucionBacktracking(distribucion, productosFijos, productesMagatzem);
        Vector<String> esperado1 = new Vector<>(Arrays.asList(null, "D", "E", "C", "A"));


        // Validaciones
        assertNotNull(mejorDistribucion);
        assertEquals(mejorDistribucion,esperado1);
    }

    @Test
    public void testDistribucionMixtaHillClimbing1() {
        Vector<String> distribucion = new Vector<>(Arrays.asList("A", "D", null, "E", "C"));
        Set<String> productosFijos = new HashSet<>(Arrays.asList("D"));
        Map<String, Producte> productesMagatzem = new HashMap<>();

        // Crear productos
        Producte a = new Producte("A", 10, 100,0);
        Producte b = new Producte("B", 10, 100,0);
        Producte c = new Producte("C", 10, 100,0);
        Producte d = new Producte("D", 10, 100,0);
        Producte e = new Producte("E", 10, 100,0);

        // Definir relaciones explícitas
        a.afegir_similitud("B", 1.4f);
        a.afegir_similitud("C", 1.6f);
        a.afegir_similitud("D", 0.9f);
        a.afegir_similitud("E", 1.0f);

        b.afegir_similitud("A", 1.4f);
        b.afegir_similitud("C", 0.3f);
        b.afegir_similitud("D", 1.4f);
        b.afegir_similitud("E", 1.9f);

        c.afegir_similitud("A", 1.6f);
        c.afegir_similitud("B", 0.3f);
        c.afegir_similitud("D", 0.6f);
        c.afegir_similitud("E", 1.7f);

        d.afegir_similitud("A", 0.9f);
        d.afegir_similitud("B", 1.4f);
        d.afegir_similitud("C", 0.6f);
        d.afegir_similitud("E", 1.4f);

        e.afegir_similitud("A", 1.0f);
        e.afegir_similitud("B", 1.9f);
        e.afegir_similitud("C", 1.7f);
        e.afegir_similitud("D", 1.4f);

        productesMagatzem.put("A", a);
        productesMagatzem.put("B", b);
        productesMagatzem.put("C", c);
        productesMagatzem.put("D", d);
        productesMagatzem.put("E", e);

        Vector<String> mejorDistribucion = algorismes.encontrarMejorDistribucionHillClimbing(distribucion, productosFijos, productesMagatzem);


        // Validaciones
        assertNotNull(mejorDistribucion);
        assertTrue(algorismes.calcularSimilitudTotal(mejorDistribucion,productesMagatzem)>=algorismes.calcularSimilitudTotal(distribucion,productesMagatzem));
    }


    @Test
    public void testDistribucionMixtaHillClimbing2() {
        Vector<String> distribucion = new Vector<>(Arrays.asList("A", "D", null, "E", "C"));
        Set<String> productosFijos = new HashSet<>(Arrays.asList());
        Map<String, Producte> productesMagatzem = new HashMap<>();

        // Crear productos
        Producte a = new Producte("A", 10, 100,0);
        Producte b = new Producte("B", 10, 100,0);
        Producte c = new Producte("C", 10, 100,0);
        Producte d = new Producte("D", 10, 100,0);
        Producte e = new Producte("E", 10, 100,0);

        // Definir relaciones explícitas

        a.afegir_similitud("B", 1.4f);
        a.afegir_similitud("D", 0.9f);


        b.afegir_similitud("A", 1.4f);
        b.afegir_similitud("C", 0.3f);


        c.afegir_similitud("B", 0.3f);
        c.afegir_similitud("E", 1.7f);

        d.afegir_similitud("A", 0.9f);
        d.afegir_similitud("E", 1.4f);

        e.afegir_similitud("C", 1.7f);
        e.afegir_similitud("D", 1.4f);


        productesMagatzem.put("A", a);
        productesMagatzem.put("B", b);
        productesMagatzem.put("C", c);
        productesMagatzem.put("D", d);
        productesMagatzem.put("E", e);

        Vector<String> mejorDistribucion = algorismes.encontrarMejorDistribucionHillClimbing(distribucion, productosFijos, productesMagatzem);


        // Validaciones
        assertNotNull(mejorDistribucion);
        assertTrue(algorismes.calcularSimilitudTotal(mejorDistribucion,productesMagatzem)>=algorismes.calcularSimilitudTotal(distribucion,productesMagatzem));
    }
}
