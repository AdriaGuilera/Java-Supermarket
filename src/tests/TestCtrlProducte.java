package tests;

import Exepcions.*;
import controladors.CtrlProducte;
import org.junit.Before;
import org.junit.Test;


import java.util.Map;

import static org.junit.Assert.*;

public class TestCtrlProducte {

    private CtrlProducte ctrlProducte;

    @Before
    public void setUp() {
        ctrlProducte = new CtrlProducte();
    }

    // Test altaProducte
    @Test
    public void testAltaProducte() throws ProducteJaExisteixException {
        ctrlProducte.altaProducte("Producto A", 100, 500,5);
        assertTrue(ctrlProducte.existeixProducte("Producto A"));
    }

    @Test(expected = ProducteJaExisteixException.class)
    public void testAltaProducteYaExistente() throws ProducteJaExisteixException {
        ctrlProducte.altaProducte("Producto A", 100, 500, 0);
        ctrlProducte.altaProducte("Producto A", 100, 500,0);
    }

    // Test eliminar_producte
    @Test
    public void testEliminarProducte() throws ProductNotFoundMagatzemException, ProducteJaExisteixException {
        ctrlProducte.altaProducte("Producto A", 100, 500,0);
        ctrlProducte.eliminarProducte("Producto A");
        assertFalse(ctrlProducte.existeixProducte("Producto A"));
    }

    @Test(expected = ProductNotFoundMagatzemException.class)
    public void testEliminarProducteNoExistente() throws ProductNotFoundMagatzemException {
        ctrlProducte.eliminarProducte("Producto B");
    }

    // Test incrementar_stock
    @Test
    public void testIncrementarStock() throws ProductNotFoundMagatzemException, ProducteJaExisteixException {
        ctrlProducte.altaProducte("Producto A", 100, 500,0);
        ctrlProducte.incrementarStock("Producto A", 200);
        assertEquals(200, ctrlProducte.getStockMagatzem("Producto A"));
    }

    @Test
    public void testIncrementarStockHastaMaximo() throws ProductNotFoundMagatzemException, ProducteJaExisteixException {
        ctrlProducte.altaProducte("Producto A", 100, 500,0);
        ctrlProducte.incrementarStock("Producto A", 600); // Excede el máximo
        assertEquals(500, ctrlProducte.getStockMagatzem("Producto A"));
    }

    @Test(expected = ProductNotFoundMagatzemException.class)
    public void testIncrementarStockProductoNoExistente() throws ProductNotFoundMagatzemException {
        ctrlProducte.incrementarStock("Producto B", 100);
    }

    // Test decrementar_stock
    @Test
    public void testDecrementarStock() throws ProductNotFoundMagatzemException, QuanitatInvalidException, ZeroStockMagatzemWarning, ProducteJaExisteixException {
        ctrlProducte.altaProducte("Producto A", 100, 500,0);
        ctrlProducte.incrementarStock("Producto A", 300);
        ctrlProducte.decrementarStock("Producto A", 100);
        assertEquals(200, ctrlProducte.getStockMagatzem("Producto A"));
    }

    @Test(expected = ZeroStockMagatzemWarning.class)
    public void testDecrementarStockHastaCero() throws ProductNotFoundMagatzemException, QuanitatInvalidException, ZeroStockMagatzemWarning, ProducteJaExisteixException {
        ctrlProducte.altaProducte("Producto A", 100, 500,0);
        ctrlProducte.incrementarStock("Producto A", 100);
        ctrlProducte.decrementarStock("Producto A", 100); // Exactamente al límite
    }


    // Test afegir_similitud
    @Test
    public void testAfegirSimilitud() throws ProducteJaExisteixException, ProductNotFoundMagatzemException, calculMateixosProductesSimilitud {
        ctrlProducte.altaProducte("Producto A", 100, 500,0);
        ctrlProducte.altaProducte("Producto B", 100, 500,0);
        ctrlProducte.afegirSimilitud("Producto A", "Producto B", 0.8f);
        assertEquals(0.8, ctrlProducte.getSimilitud("Producto B", "Producto A"), 0.001);
    }

    // Test afegir_similitud: Excepción calculMateixosProductesSimilitud cuando nom1 == nom2
    @Test
    public void testAfegirSimilitudMismoProducto() {
        assertThrows(calculMateixosProductesSimilitud.class, () -> {
            ctrlProducte.afegirSimilitud("Producto A", "Producto A", 0.5f);
        });
    }

    // Test afegir_similitud: Excepción ProductNotFoundMagatzemException cuando nom1 no existe
    @Test
    public void testAfegirSimilitudProducto1NoExiste() {
        assertThrows(ProductNotFoundMagatzemException.class, () -> {
            ctrlProducte.afegirSimilitud("Producto A", "Producto B", 0.5f);
        });
    }

    // Test afegir_similitud: Excepción ProductNotFoundMagatzemException cuando nom2 no existe
    @Test
    public void testAfegirSimilitudProducto2NoExiste() throws Exception {
        ctrlProducte.altaProducte("Producto A", 100, 500,0);
        assertThrows(ProductNotFoundMagatzemException.class, () -> {
            ctrlProducte.afegirSimilitud("Producto A", "Producto B", 0.5f);
        });
    }


    // Test eliminarSimilitud
    @Test
    public void testEliminarSimilitud() throws ProducteJaExisteixException, ProductNotFoundMagatzemException, calculMateixosProductesSimilitud {
        ctrlProducte.altaProducte("Producto A", 100, 500,0);
        ctrlProducte.altaProducte("Producto B", 100, 500,0);
        ctrlProducte.afegirSimilitud("Producto A", "Producto B", 0.8f);
        ctrlProducte.eliminarSimilitud("Producto A", "Producto B");
        assertEquals(0.0, ctrlProducte.getSimilitud("Producto A", "Producto B"), 0.000);
    }
    // Test eliminarSimilitud: Excepción calculMateixosProductesSimilitud cuando nom1 == nom2
    @Test
    public void testEliminarSimilitudMismoProducto() {
        assertThrows(calculMateixosProductesSimilitud.class, () -> {
            ctrlProducte.eliminarSimilitud("Producto A", "Producto A");
        });
    }

    // Test eliminarSimilitud: Excepción ProductNotFoundMagatzemException cuando nom1 no existe
    @Test
    public void testEliminarSimilitudProducto1NoExiste() {
        assertThrows(ProductNotFoundMagatzemException.class, () -> {
            ctrlProducte.eliminarSimilitud("Producto A", "Producto B");
        });
    }

    // Test eliminarSimilitud: Excepción ProductNotFoundMagatzemException cuando nom2 no existe
    @Test
    public void testEliminarSimilitudProducto2NoExiste() throws Exception {
        ctrlProducte.altaProducte("Producto A", 100, 500,0);
        assertThrows(ProductNotFoundMagatzemException.class, () -> {
            ctrlProducte.eliminarSimilitud("Producto A", "Producto B");
        });
    }

    // Test obtenirComandaAutomatica
    @Test
    public void testObtenirComandaAutomatica() throws ProducteJaExisteixException {
        ctrlProducte.altaProducte("Producto A", 100, 500,0);
        ctrlProducte.incrementarStock("Producto A", 200);
        Map<String, Integer> comandaAutomatica = ctrlProducte.generarComandaAutomatica();
        assertEquals(300, (int) comandaAutomatica.get("Producto A"));
    }
}
