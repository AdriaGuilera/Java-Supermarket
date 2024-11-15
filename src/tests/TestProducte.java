package tests;


import Exepcions.QuanitatInvalidException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import classes.Producte;


public class TestProducte {

    private Producte producte;

    @Before
    public void setUp() {
        // Este método se ejecuta antes de cada test para inicializar un nuevo objeto Producte
        producte = new Producte("Producto A", 100, 500,0);
    }

    @Test
    public void testConstructor() {
        assertEquals("Producto A", producte.get_nom());
        assertEquals(100, producte.get_max_hueco());
        assertEquals(500, producte.get_max_magatzem());
        assertEquals(0, producte.get_stock());
    }

    //Getters
    @Test
    public void testGetNom() {
        // Verifica que el nombre del producto sea el esperado
        assertEquals("Producto A", producte.get_nom());
    }

    @Test
    public void testGetMaxHueco() {
        // Verifica que el valor máximo por compartimento sea el esperado
        assertEquals(100, producte.get_max_hueco());
    }

    @Test
    public void testGetMaxMagatzem() {
        // Verifica que el valor máximo del almacén sea el esperado
        assertEquals(500, producte.get_max_magatzem());
    }

    @Test
    public void testGetStockInicial() {
        // Verifica que el stock inicial sea 0
        assertEquals(0, producte.get_stock());
    }

    @Test
    public void testGetStockDespuesDeModificacion() {
        // Cambia el stock y verifica que se actualice correctamente
        producte.mod_stock(200);
        assertEquals(200, producte.get_stock());
    }

    @Test
    public void testGetSimilitudProductoNoExistente() {
        // Si el producto no tiene similitud, debe retornar 0
        assertEquals(0.0f, producte.getSimilitud("Producto B"), 0.000);
    }

    @Test
    public void testGetSimilitudProductoExistente() {
        // Añadimos una similitud y comprobamos que se devuelva correctamente
        producte.afegir_similitud("Producto B", 0.85f);
        assertEquals(0.85f, producte.getSimilitud("Producto B"), 0.000);
    }

    @Test
    public void testGetSimilitudProductoEliminado() {
        // Añadimos y luego eliminamos la similitud, debe retornar 0
        producte.afegir_similitud("Producto B", 0.85f);
        producte.eliminarSimilitud("Producto B");
        assertEquals(0.0f, producte.getSimilitud("Producto B"), 0.000);
    }

    @Test
    public void testModStock() {
        producte.mod_stock(200);
        assertEquals(200, producte.get_stock());
    }

    @Test
    public void testIncrementarStockDentroDeLimite() throws QuanitatInvalidException {
        producte.incrementar_stock(50);
        assertEquals(50, producte.get_stock());
    }

    @Test
    public void testIncrementarStockHastaLimite() throws QuanitatInvalidException {
        producte.incrementar_stock(500); // Límite máximo del almacén
        assertEquals(500, producte.get_stock());
    }

    @Test
    public void testIncrementarStockExcedeLimite() throws QuanitatInvalidException {
        producte.incrementar_stock(600); // Excede el límite
        assertEquals(500, producte.get_stock()); // Se ajusta al límite máximo
    }

    @Test()
    public void testIncrementarStockCantidadNegativa() throws QuanitatInvalidException {
        assertThrows(QuanitatInvalidException.class, () -> producte.incrementar_stock(-10)); // Esto debe lanzar una excepción
    }

    @Test
    public void testDecrementarStockNormal() {
        producte.mod_stock(100); // Primero, ponemos el stock en 100
        producte.decrementar_stock(30);
        assertEquals(70, producte.get_stock());
    }

    @Test
    public void testDecrementarStockHastaCero() {
        producte.mod_stock(100);
        producte.decrementar_stock(100); // Exactamente al límite
        assertEquals(0, producte.get_stock());
    }

    @Test
    public void testDecrementarStockPorDebajoDeCero() {
        producte.mod_stock(50);
        producte.decrementar_stock(100); // Más de lo disponible
        assertEquals(0, producte.get_stock()); // No debe ser negativo
    }
    @Test
    public void testDecrementarStockCantidadNegativa() {
        producte.mod_stock(-50);
        assertThrows(QuanitatInvalidException.class, () -> producte.decrementar_stock(-10)); // Esto debe lanzar una excepción
    }

    @Test
    public void testAfegirSimilitud() {
        producte.afegir_similitud("Producto B", 0.8f);
        assertEquals(0.8f, producte.getSimilitud("Producto B"), 0.000); // Agregamos un margen de error en la comparación flotante
    }

    @Test
    public void testAfegirSimilitudSobreescribeValor() {
        producte.afegir_similitud("Producto B", 0.8f);
        producte.afegir_similitud("Producto B", 0.9f); // Sobrescribe el valor de la similitud
        assertEquals(0.9f, producte.getSimilitud("Producto B"), 0.000); // Agregamos un margen de error en la comparación flotante
    }

    @Test
    public void testEliminarSimilitudExistente() {
        producte.afegir_similitud("Producto B", 0.8f);
        producte.eliminarSimilitud("Producto B");
        assertEquals(0.0f, producte.getSimilitud("Producto B"), 0.000); // Similitud debe volver a 0
    }

    @Test
    public void testEliminarSimilitudNoExistente() {
        producte.eliminarSimilitud("Producto C"); // No existe en el mapa
        assertEquals(0.0f, producte.getSimilitud("Producto C"), 0.000);
    }


}

