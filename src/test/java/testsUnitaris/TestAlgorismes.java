package testsUnitaris;

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

        a.afegirSimilitud("B", 0.9f);
        a.afegirSimilitud("C", 0.8f);
        a.afegirSimilitud("D", 0.7f);
        a.afegirSimilitud("E", 0.6f);

        b.afegirSimilitud("A", 0.9f);
        b.afegirSimilitud("C", 0.85f);
        b.afegirSimilitud("D", 0.8f);
        b.afegirSimilitud("E", 0.75f);

        c.afegirSimilitud("A", 0.8f);
        c.afegirSimilitud("B", 0.85f);
        c.afegirSimilitud("D", 0.9f);
        c.afegirSimilitud("E", 0.88f);

        d.afegirSimilitud("A", 0.7f);
        d.afegirSimilitud("B", 0.8f);
        d.afegirSimilitud("C", 0.9f);
        d.afegirSimilitud("E", 0.95f);

        e.afegirSimilitud("A", 0.6f);
        e.afegirSimilitud("B", 0.75f);
        e.afegirSimilitud("C", 0.88f);
        e.afegirSimilitud("D", 0.95f);

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

        a.afegirSimilitud("D", 1.0f);
        d.afegirSimilitud("A",0.3f);

        a.afegirSimilitud("E", 0.8f);
        e.afegirSimilitud("A",0.8f);


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

        a.afegirSimilitud("D", 1.0f);
        b.afegirSimilitud("A",0.3f);

        a.afegirSimilitud("E", 0.8f);
        d.afegirSimilitud("A",0.8f);


        productesMagatzem.put("A", a);
        productesMagatzem.put("B", b);
        productesMagatzem.put("C", c);
        productesMagatzem.put("D", d);
        productesMagatzem.put("E", e);

        Vector<String> mejorDistribucion = algorismes.encontrarMejorDistribucionBacktracking(distribucion, productosFijos, productesMagatzem);
        Vector<String> esperado1 = new Vector<>(Arrays.asList("A", "D" , null, null, "E"));

        // Validaciones
        assertNotNull(mejorDistribucion);
        assertFalse(mejorDistribucion.contains("B"));
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
        a.afegirSimilitud("B", 1.4f);
        a.afegirSimilitud("C", 1.6f);
        a.afegirSimilitud("D", 0.9f);
        a.afegirSimilitud("E", 1.0f);

        b.afegirSimilitud("A", 1.4f);
        b.afegirSimilitud("C", 0.3f);
        b.afegirSimilitud("D", 1.4f);
        b.afegirSimilitud("E", 1.9f);

        c.afegirSimilitud("A", 1.6f);
        c.afegirSimilitud("B", 0.3f);
        c.afegirSimilitud("D", 0.6f);
        c.afegirSimilitud("E", 1.7f);

        d.afegirSimilitud("A", 0.9f);
        d.afegirSimilitud("B", 1.4f);
        d.afegirSimilitud("C", 0.6f);
        d.afegirSimilitud("E", 1.4f);

        e.afegirSimilitud("A", 1.0f);
        e.afegirSimilitud("B", 1.9f);
        e.afegirSimilitud("C", 1.7f);
        e.afegirSimilitud("D", 1.4f);

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
        // Arrange
        Vector<String> distribucion = new Vector<>(Arrays.asList("A", "D", null, "E", "C"));
        Set<String> productosFijos = new HashSet<>(Arrays.asList("D"));
        Map<String, Producte> productesMagatzem = new HashMap<>();

        // Crear productos
        Producte a = new Producte("A", 10, 100, 0);
        Producte b = new Producte("B", 10, 100, 0);
        Producte c = new Producte("C", 10, 100, 0);
        Producte d = new Producte("D", 10, 100, 0);
        Producte e = new Producte("E", 10, 100, 0);

        // Definir relaciones explícitas
        a.afegirSimilitud("B", 1.4f);
        a.afegirSimilitud("C", 1.6f);
        a.afegirSimilitud("D", 0.9f);
        a.afegirSimilitud("E", 1.0f);

        b.afegirSimilitud("A", 1.4f);
        b.afegirSimilitud("C", 0.3f);
        b.afegirSimilitud("D", 1.4f);
        b.afegirSimilitud("E", 1.9f);

        c.afegirSimilitud("A", 1.6f);
        c.afegirSimilitud("B", 0.3f);
        c.afegirSimilitud("D", 0.6f);
        c.afegirSimilitud("E", 1.7f);

        d.afegirSimilitud("A", 0.9f);
        d.afegirSimilitud("B", 1.4f);
        d.afegirSimilitud("C", 0.6f);
        d.afegirSimilitud("E", 1.4f);

        e.afegirSimilitud("A", 1.0f);
        e.afegirSimilitud("B", 1.9f);
        e.afegirSimilitud("C", 1.7f);
        e.afegirSimilitud("D", 1.4f);

        productesMagatzem.put("A", a);
        productesMagatzem.put("B", b);
        productesMagatzem.put("C", c);
        productesMagatzem.put("D", d);
        productesMagatzem.put("E", e);

        Vector<String> mejorDistribucion = algorismes.encontrarMejorDistribucionHillClimbing(distribucion, productosFijos, productesMagatzem);

        assertNotNull(mejorDistribucion);
        assertEquals(distribucion.size(), mejorDistribucion.size());

        for (String productoFijo : productosFijos) {
            assertTrue(mejorDistribucion.contains(productoFijo));
        }
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

        a.afegirSimilitud("B", 1.4f);
        a.afegirSimilitud("D", 0.2f);


        b.afegirSimilitud("A", 1.4f);
        b.afegirSimilitud("C", 0.3f);


        c.afegirSimilitud("B", 0.3f);
        c.afegirSimilitud("E", 1.7f);

        d.afegirSimilitud("A", 0.2f);
        d.afegirSimilitud("E", 1.3f);

        e.afegirSimilitud("C", 1.7f);
        e.afegirSimilitud("D", 1.3f);


        productesMagatzem.put("A", a);
        productesMagatzem.put("B", b);
        productesMagatzem.put("C", c);
        productesMagatzem.put("D", d);
        productesMagatzem.put("E", e);

        Vector<String> mejorDistribucion = algorismes.encontrarMejorDistribucionHillClimbing(distribucion, productosFijos, productesMagatzem);


        assertNotNull(mejorDistribucion);
        assertEquals(distribucion.size(), mejorDistribucion.size());


    }


}
