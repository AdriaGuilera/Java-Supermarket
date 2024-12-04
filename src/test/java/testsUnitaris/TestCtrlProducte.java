package testsUnitaris;

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


    private CtrlProducte ctrlProducte;


    @Before
    public void setUp() {
        ctrlProducte = new CtrlProducte();
    }

    // Test altaProducte
    @Test
    public void testAltaProducte() throws ProducteAlreadyExistsException {
        ctrlProducte.altaProducte("Producto A", 100, 500,5);
        assertTrue(ctrlProducte.existeixProducte("Producto A"));
    }

    @Test(expected = ProducteAlreadyExistsException.class)
    public void testAltaProducteYaExistente() throws ProducteAlreadyExistsException {
        ctrlProducte.altaProducte("Producto A", 100, 500, 0);
        ctrlProducte.altaProducte("Producto A", 100, 500,0);
    }

    // Test eliminar_producte
    @Test
    public void testEliminarProducte() throws ProductNotFoundMagatzemException, ProducteAlreadyExistsException {
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
    public void testIncrementarStock() throws ProductNotFoundMagatzemException, ProducteAlreadyExistsException {
        ctrlProducte.altaProducte("Producto A", 100, 500,0);
        ctrlProducte.incrementarStock("Producto A", 200);
        assertEquals(200, ctrlProducte.getStockMagatzem("Producto A"));
    }

    @Test
    public void testIncrementarStockHastaMaximo() throws ProductNotFoundMagatzemException, ProducteAlreadyExistsException {
        ctrlProducte.altaProducte("Producto A", 100, 500,0);
        ctrlProducte.incrementarStock("Producto A", 600); // Excede el máximo
        assertEquals(500, ctrlProducte.getStockMagatzem("Producto A"));
    }
    @Test
    public void testGetStockMagatzemNotFound() {
        // Assert
        assertThrows(ProductNotFoundMagatzemException.class, () -> {
            ctrlProducte.getStockMagatzem("NonExistentProduct");
        });
    }

    @Test(expected = ProductNotFoundMagatzemException.class)
    public void testIncrementarStockProductoNoExistente() throws ProductNotFoundMagatzemException {
        ctrlProducte.incrementarStock("Producto B", 100);
    }

    // Test decrementar_stock
    @Test
    public void testDecrementarStock() throws ProductNotFoundMagatzemException, QuanitatInvalidException, ZeroStockMagatzemWarning, ProducteAlreadyExistsException {
        ctrlProducte.altaProducte("Producto A", 100, 500,0);
        ctrlProducte.incrementarStock("Producto A", 300);
        ctrlProducte.decrementarStock("Producto A", 100);
        assertEquals(200, ctrlProducte.getStockMagatzem("Producto A"));
    }



    // Test afegir_similitud
    @Test
    public void testAfegirSimilitud() throws ProducteAlreadyExistsException, ProductNotFoundMagatzemException, calculMateixosProductesSimilitud {
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
    public void testEliminarSimilitud() throws ProducteAlreadyExistsException, ProductNotFoundMagatzemException, calculMateixosProductesSimilitud {
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
    public void testObtenirComandaAutomatica() throws ProducteAlreadyExistsException {
        ctrlProducte.altaProducte("Producto A", 100, 500,0);
        ctrlProducte.incrementarStock("Producto A", 200);
        Map<String, Integer> comandaAutomatica = ctrlProducte.generarComandaAutomatica();
        assertEquals(300, (int) comandaAutomatica.get("Producto A"));
    }
    @Test
    public void testGetMaxHueco() throws Exception {
        ctrlProducte.altaProducte("Producte1", 10, 100, 50);
        int maxHueco = ctrlProducte.getMaxHueco("Producte1");
        assertEquals(10, maxHueco);
    }

    @Test
    public void testGetMaxHuecoNotFound() {
        assertThrows(ProductNotFoundMagatzemException.class, () -> {
            ctrlProducte.getMaxHueco("NonExistentProduct");
        });
    }

    @Test
    public void testGetMagatzem() throws Exception {
        // Setup
        ctrlProducte.altaProducte("Producte1", 10, 100, 50);
        ctrlProducte.altaProducte("Producte2", 5, 50, 20);

        // Action
        Map<String, Producte> magatzem = ctrlProducte.getMagatzem();

        // Assert
        assertEquals(2, magatzem.size());
        assertTrue(magatzem.containsKey("Producte1"));
        assertTrue(magatzem.containsKey("Producte2"));
    }

    @Test
    public void testGetProducte() throws Exception {
        // Setup
        ctrlProducte.altaProducte("Producte1", 10, 100, 50);

        // Action
        Producte producte = ctrlProducte.getProducte("Producte1");

        // Assert
        assertNotNull(producte);
        assertEquals("Producte1", producte.getNom());
    }

    @Test
    public void testGetProducteNotFound() {
        // Assert
        assertThrows(ProductNotFoundMagatzemException.class, () -> {
            ctrlProducte.getProducte("NonExistentProduct");
        });
    }

    @Test
    public void testGetSimilitudFirstProductNull() {
        // Caso donde el primer nombre es null
        double similitud = ctrlProducte.getSimilitud(null, "Producte1");
        assertEquals(0, similitud, 0.001);
    }

    @Test
    public void testGetSimilitudSecondProductNull() {
        // Caso donde el segundo nombre es null
        double similitud = ctrlProducte.getSimilitud("Producte1", null);
        assertEquals(0, similitud, 0.001);
    }

    @Test
    public void testDecrementarStockNonExistentProduct() {
        assertThrows(ProductNotFoundMagatzemException.class, () -> {
            ctrlProducte.decrementarStock("NonExistentProduct", 10);
        });
    }

    @Test
    public void testDecrementarStockNegativeQuantity() throws Exception {
        ctrlProducte.altaProducte("Producte1", 10, 100, 50);
        assertThrows(QuanitatInvalidException.class, () -> {
            ctrlProducte.decrementarStock("Producte1", -10);
        });
    }

    @Test
    public void testExecutarComandesValid() throws Exception {
        // Configuración inicial
        ctrlProducte.altaProducte("Producte1", 10, 100, 50);
        ctrlProducte.altaProducte("Producte2", 5, 50, 20);

        // Crear comandas
        Map<String, Comanda> comandes = new HashMap<>();
        Comanda comanda1 = new Comanda("comanda1");
        comanda1.afegirProducte("Producte1", 30);
        comanda1.afegirProducte("Producte2", 20);

        comandes.put("Comanda1", comanda1);

        // Ejecutar comandas
        ctrlProducte.executarComandes(comandes);

        // Verificar el resultado
        assertEquals(80, ctrlProducte.getStockMagatzem("Producte1")); // 50 + 30
        assertEquals(40, ctrlProducte.getStockMagatzem("Producte2")); // 20 + 20
    }
    @Test
    public void testExecutarComandesValid2() throws Exception {
        // Configuración inicial
        ctrlProducte.altaProducte("Producte1", 10, 10, 5);
        ctrlProducte.altaProducte("Producte2", 5, 10, 5);

        // Crear comandas
        Map<String, Comanda> comandes = new HashMap<>();
        Comanda comanda1 = new Comanda("comanda1");
        comanda1.afegirProducte("Producte1", 30);
        comanda1.afegirProducte("Producte2", 20);

        comandes.put("Comanda1", comanda1);

        // Ejecutar comandas
        ctrlProducte.executarComandes(comandes);

        // Verificar el resultado
        assertEquals(10, ctrlProducte.getStockMagatzem("Producte1")); // 50 + 30
        assertEquals(10, ctrlProducte.getStockMagatzem("Producte2")); // 20 + 20
    }
}


