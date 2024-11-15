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

    private CtrlProducte ctrlProducte;

    @Before
    public void setUp() {
        // Inicializa un nuevo objeto CtrlProducte antes de cada prueba
        ctrlProducte = new CtrlProducte();
    }

    @Test
    public void testAltaProducte() throws ProducteJaExisteixException {
        ctrlProducte.altaProducte("Producto A", 100, 500,20);
        assertTrue(CtrlProducte.existeix_producte("Producto A"));
    }



    @Test
    public void testEliminarProducte() throws ProducteJaExisteixException, ProductNotFoundMagatzemException {
        ctrlProducte.altaProducte("Producto A", 100, 500,0);
        ctrlProducte.eliminar_producte("Producto A");
        assertFalse(CtrlProducte.existeix_producte("Producto A"));
    }

    @Test(expected = ProductNotFoundMagatzemException.class)
    public void testEliminarProducteNoExistente() throws ProductNotFoundMagatzemException {
        ctrlProducte.eliminar_producte("Producto Inexistente");
    }

    @Test
    public void testExecutarComandes() throws ProducteJaExisteixException {
        ctrlProducte.altaProducte("Producto A", 100, 500,0);
        ctrlProducte.altaProducte("Producto B", 50, 300,0);

        Map<String, Comanda> comandes = new HashMap<>();
        Comanda comanda1 = new Comanda("Comanda 1");
        //comanda1.afegirOrdre("Producto A", 200);
        //comanda1.afegirOrdre("Producto B", 150);

        comandes.put("Comanda 1", comanda1);
        ctrlProducte.executar_comandes(comandes);

        assertEquals(200, ctrlProducte.get_stock_magatzem("Producto A"));
        assertEquals(150, ctrlProducte.get_stock_magatzem("Producto B"));
    }

    @Test(expected = ProducteJaExisteixException.class)
    public void testAltaProducteDuplicat() throws ProducteJaExisteixException {
        ctrlProducte.altaProducte("Producto A", 100, 500,0);
        ctrlProducte.altaProducte("Producto A", 50, 300,0); // Debe lanzar una excepción
    }

    @Test
    public void testObtenirComandaAutomatica() throws ProducteJaExisteixException {
        ctrlProducte.altaProducte("Producto A", 100, 500,200);
        ctrlProducte.altaProducte("Producto B", 50, 300,100);


        Map<String, Integer> comandaAutomatica = ctrlProducte.obtenirComandaAutomatica();
        assertEquals(300, (int) comandaAutomatica.get("Producto A"));
        assertEquals(200, (int) comandaAutomatica.get("Producto B"));
    }

    @Test
    public void testIncrementarStockDentroDeLimite() throws ProducteJaExisteixException, ProductNotFoundMagatzemException {
        ctrlProducte.altaProducte("Producto A", 100, 500,0);
        ctrlProducte.incrementar_stock("Producto A", 300);
        assertEquals(300, ctrlProducte.get_stock_magatzem("Producto A"));
    }

    @Test
    public void testIncrementarStockExcedeLimite() throws ProducteJaExisteixException, ProductNotFoundMagatzemException {
        ctrlProducte.altaProducte("Producto A", 100, 500,0);
        ctrlProducte.incrementar_stock("Producto A", 600); // Excede el límite
        assertEquals(500, ctrlProducte.get_stock_magatzem("Producto A")); // Se ajusta al límite máximo
    }

    @Test(expected = ProductNotFoundMagatzemException.class)
    public void testIncrementarStockProductoNoExistente() throws ProductNotFoundMagatzemException {
        ctrlProducte.incrementar_stock("Producto Inexistente", 100); // Debe lanzar una excepción
    }

    @Test
    public void testDecrementarStock() throws ProducteJaExisteixException, ProductNotFoundMagatzemException, QuanitatInvalidException {
        ctrlProducte.altaProducte("Producto A", 100, 500,300);
        ctrlProducte.decrementar_stock("Producto A", 100);
        assertEquals(200, ctrlProducte.get_stock_magatzem("Producto A"));
    }

    @Test(expected = ProductNotFoundMagatzemException.class)
    public void testDecrementarStockProductoNoExistente() throws ProductNotFoundMagatzemException, QuanitatInvalidException {
        ctrlProducte.decrementar_stock("Producto Inexistente", 100); // Debe lanzar una excepción
    }


}
