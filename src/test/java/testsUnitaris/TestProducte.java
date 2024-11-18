package testsUnitaris;

import Exepcions.QuanitatInvalidException;
import Exepcions.StockTooBigException;
import Exepcions.ZeroStockMagatzemWarning;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import classes.Producte;

public class TestProducte {

    private Producte producte;

    @Before
    public void setUp() {
        producte = new Producte("Producto A", 100, 500, 0);
    }

    @Test
    public void testConstructor() {
        assertEquals("Producto A", producte.getNom());
        assertEquals(100, producte.getMaxHueco());
        assertEquals(500, producte.getMaxMagatzem());
        assertEquals(0, producte.getStock());
    }

    @Test (expected = QuanitatInvalidException.class)
    public void testConstructor2() {
        producte = new Producte("Producto A", 100, 500, -10);
    }

    @Test (expected = StockTooBigException.class)
    public void testConstructor3() {
        producte = new Producte("Producto A", 100, 500, 600);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructor4() {
        producte = new Producte("Producto A", -100, 500, 10);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testConstructor5() {
        producte = new Producte("Producto A", 100, -500, 10);
    }



    //Getters
    @Test
    public void testGetNom() {
        // Verifica que el nombre del producto sea el esperado
        assertEquals("Producto A", producte.getNom());
    }

    @Test
    public void testGetMaxHueco() {
        // Verifica que el valor máximo por compartimento sea el esperado
        assertEquals(100, producte.getMaxHueco());
    }

    @Test
    public void testGetMaxMagatzem() {
        // Verifica que el valor máximo del almacén sea el esperado
        assertEquals(500, producte.getMaxMagatzem());
    }

    @Test
    public void testGetStockInicial() {
        // Verifica que el stock inicial sea 0
        assertEquals(0, producte.getStock());
    }

    @Test
    public void testGetStockDespuesDeModificacion() {
        // Cambia el stock y verifica que se actualice correctamente
        producte.modStock(200);
        assertEquals(200, producte.getStock());
    }

    @Test
    public void testGetSimilitudProductoNoExistente() {
        // Si el producto no tiene similitud, debe retornar 0
        assertEquals(0.0f, producte.getSimilitud("Producto B"), 0.000);
    }

    @Test
    public void testGetSimilitudProductoExistente() {
        // Añadimos una similitud y comprobamos que se devuelva correctamente
        producte.afegirSimilitud("Producto B", 0.85f);
        assertEquals(0.85f, producte.getSimilitud("Producto B"), 0.000);
    }

    @Test
    public void testGetSimilitudProductoEliminado() {
        // Añadimos y luego eliminamos la similitud, debe retornar 0
        producte.afegirSimilitud("Producto B", 0.85f);
        producte.eliminarSimilitud("Producto B");
        assertEquals(0.0f, producte.getSimilitud("Producto B"), 0.000);
    }

    @Test
    public void testModStock() {
        producte.modStock(200);
        assertEquals(200, producte.getStock());
    }

    @Test
    public void testIncrementarStockDentroDeLimite() throws QuanitatInvalidException {
        producte.incrementarStock(50);
        assertEquals(50, producte.getStock());
    }

    @Test
    public void testIncrementarStockHastaLimite() throws QuanitatInvalidException {
        producte.incrementarStock(500); // Límite máximo del almacén
        assertEquals(500, producte.getStock());
    }

    @Test
    public void testIncrementarStockExcedeLimite() throws QuanitatInvalidException {
        producte.incrementarStock(600); // Excede el límite
        assertEquals(500, producte.getStock()); // Se ajusta al límite máximo
    }

    @Test()
    public void testIncrementarStockCantidadNegativa() throws QuanitatInvalidException {
        assertThrows(QuanitatInvalidException.class, () -> producte.incrementarStock(-10)); // Esto debe lanzar una excepción
    }

    @Test
    public void testDecrementarStockNormal() {
        producte.modStock(100); // Primero, ponemos el stock en 100
        producte.decrementarStock(30);
        assertEquals(70, producte.getStock());
    }


    @Test
    public void testDecrementarStockCantidadNegativa() {
        producte.modStock(-50);
        assertThrows(QuanitatInvalidException.class, () -> producte.decrementarStock(-10)); // Esto debe lanzar una excepción
    }

    @Test
    public void testAfegirSimilitud() {
        producte.afegirSimilitud("Producto B", 0.8f);
        assertEquals(0.8f, producte.getSimilitud("Producto B"), 0.000); // Agregamos un margen de error en la comparación flotante
    }

    @Test
    public void testAfegirSimilitudSobreescribeValor() {
        producte.afegirSimilitud("Producto B", 0.8f);
        producte.afegirSimilitud("Producto B", 0.9f); // Sobrescribe el valor de la similitud
        assertEquals(0.9f, producte.getSimilitud("Producto B"), 0.000); // Agregamos un margen de error en la comparación flotante
    }

    @Test
    public void testEliminarSimilitudExistente() {
        producte.afegirSimilitud("Producto B", 0.8f);
        producte.eliminarSimilitud("Producto B");
        assertEquals(0.0f, producte.getSimilitud("Producto B"), 0.000); // Similitud debe volver a 0
    }

    @Test
    public void testEliminarSimilitudNoExistente() {
        producte.eliminarSimilitud("Producto C"); // No existe en el mapa
        assertEquals(0.0f, producte.getSimilitud("Producto C"), 0.000);
    }
}
