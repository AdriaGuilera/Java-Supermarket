package tests;

import Exepcions.*;
import classes.Comanda;
import classes.Producte;
import controladors.CtrlProducte;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TestCtrlProducte {
/*
    private CtrlProducte ctrlProducte;

    @Before
    public void setUp() {
        // Inicializa un nuevo objeto CtrlProducte antes de cada prueba
        ctrlProducte = new CtrlProducte();
    }

    @Test
    public void testAltaProducte() throws ProducteJaExisteixException {
        ctrlProducte.altaProducte("Producto A", 100, 500);
        assertTrue(CtrlProducte.existeix_producte("Producto A"));
    }

    @Test(expected = ProducteJaExisteixException.class)
    public void testAltaProducteDuplicado() throws ProducteJaExisteixException {
        ctrlProducte.altaProducte("Producto A", 100, 500);
        ctrlProducte.altaProducte("Producto A", 200, 600); // Debe lanzar una excepción
    }

    @Test
    public void testEliminarProducte() throws ProducteJaExisteixException, ProductNotFoundMagatzemException {
        ctrlProducte.altaProducte("Producto A", 100, 500);
        ctrlProducte.eliminar_producte("Producto A");
        assertFalse(CtrlProducte.existeix_producte("Producto A"));
    }

    @Test(expected = ProductNotFoundMagatzemException.class)
    public void testEliminarProducteNoExistente() throws ProductNotFoundMagatzemException {
        ctrlProducte.eliminar_producte("Producto Inexistente");
    }

    @Test
    public void testExecutarComandes() throws ProducteJaExisteixException {
        ctrlProducte.altaProducte("Producto A", 100, 500);
        ctrlProducte.altaProducte("Producto B", 50, 300);

        Map<String, Comanda> comandes = new HashMap<>();
        Comanda comanda1 = new Comanda();
        comanda1.afegirOrdre("Producto A", 200);
        comanda1.afegirOrdre("Producto B", 150);

        comandes.put("Comanda 1", comanda1);
        ctrlProducte.executar_comandes(comandes);

        assertEquals(200, ctrlProducte.get_stock_magatzem("Producto A"));
        assertEquals(150, ctrlProducte.get_stock_magatzem("Producto B"));
    }

    @Test
    public void testObtenirComandaAutomatica() throws ProducteJaExisteixException {
        ctrlProducte.altaProducte("Producto A", 100, 500);
        ctrlProducte.altaProducte("Producto B", 50, 300);

        ctrlProducte.incrementar_stock("Producto A", 200); // Quedan 300 por llenar
        ctrlProducte.incrementar_stock("Producto B", 100); // Quedan 200 por llenar

        Map<String, Integer> comandaAutomatica = ctrlProducte.obtenirComandaAutomatica();
        assertEquals(300, (int) comandaAutomatica.get("Producto A"));
        assertEquals(200, (int) comandaAutomatica.get("Producto B"));
    }

    @Test
    public void testIncrementarStockDentroDeLimite() throws ProducteJaExisteixException, ProductNotFoundMagatzemException {
        ctrlProducte.altaProducte("Producto A", 100, 500);
        ctrlProducte.incrementar_stock("Producto A", 300);
        assertEquals(300, ctrlProducte.get_stock_magatzem("Producto A"));
    }

    @Test
    public void testIncrementarStockExcedeLimite() throws ProducteJaExisteixException, ProductNotFoundMagatzemException {
        ctrlProducte.altaProducte("Producto A", 100, 500);
        ctrlProducte.incrementar_stock("Producto A", 600); // Excede el límite
        assertEquals(500, ctrlProducte.get_stock_magatzem("Producto A")); // Se ajusta al límite máximo
    }

    @Test(expected = ProductNotFoundMagatzemException.class)
    public void testIncrementarStockProductoNoExistente() throws ProductNotFoundMagatzemException {
        ctrlProducte.incrementar_stock("Producto Inexistente", 100); // Debe lanzar una excepción
    }

    @Test
    public void testDecrementarStock() throws ProducteJaExisteixException, ProductNotFoundMagatzemException, QuanitatInvalidException {
        ctrlProducte.altaProducte("Producto A", 100, 500);
        ctrlProducte.incrementar_stock("Producto A", 300);
        ctrlProducte.decrementar_stock("Producto A", 100);
        assertEquals(200, ctrlProducte.get_stock_magatzem("Producto A"));
    }

    @Test(expected = ProductNotFoundMagatzemException.class)
    public void testDecrementarStockProductoNoExistente() throws ProductNotFoundMagatzemException, QuanitatInvalidException {
        ctrlProducte.decrementar_stock("Producto Inexistente", 100); // Debe lanzar una excepción
    }

    @Test(expected = QuanitatInvalidException.class)
    public void testDecrementarStockCantidadNegativa() throws ProducteJaExisteixException, ProductNotFoundMagatzemException, QuanitatInvalidException {
        ctrlProducte.altaProducte("Producto A", 100, 500);
        ctrlProducte.decrementar_stock("Producto A", -50); // Debe lanzar una excepción
    }

    @Test
    public void testAfegirSimilitud() throws ProducteJaExisteixException, ProductNotFoundMagatzemException, calculMateixosProductesSimilitud {
        ctrlProducte.altaProducte("Producto A", 100, 500);
        ctrlProducte.altaProducte("Producto B", 50, 300);

        ctrlProducte.afegir_similitud("Producto A", "Producto B", 0.75f);
        assertEquals(0.75f, CtrlProducte.obtenir_similitud("Producto A", "Producto B"), 0.001);
    }

    @Test(expected = calculMateixosProductesSimilitud.class)
    public void testAfegirSimilitudMismoProducto() throws ProducteJaExisteixException, calculMateixosProductesSimilitud, ProductNotFoundMagatzemException {
        ctrlProducte.altaProducte("Producto A", 100, 500);
        ctrlProducte.afegir_similitud("Producto A", "Producto A", 0.75f); // Debe lanzar una excepción
    }

    @Test(expected = ProductNotFoundMagatzemException.class)
    public void testAfegirSimilitudProductoNoExistente() throws calculMateixosProductesSimilitud, ProductNotFoundMagatzemException {
        ctrlProducte.afegir_similitud("Producto A", "Producto Inexistente", 0.75f); // Debe lanzar una excepción
    }

    @Test
    public void testEliminarSimilitud() throws ProducteJaExisteixException, ProductNotFoundMagatzemException, calculMateixosProductesSimilitud {
        ctrlProducte.altaProducte("Producto A", 100, 500);
        ctrlProducte.altaProducte("Producto B", 50, 300);

        ctrlProducte.afegir_similitud("Producto A", "Producto B", 0.75f);
        ctrlProducte.eliminarSimilitud("Producto A", "Producto B");
        assertEquals(0.0f, CtrlProducte.obtenir_similitud("Producto A", "Producto B"), 0.001);
    }
    */
}
