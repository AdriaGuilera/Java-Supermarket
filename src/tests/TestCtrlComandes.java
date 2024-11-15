package tests;


import Exepcions.ComandaNotFoundException;
import Exepcions.ProductNotFoundComandaException;
import Exepcions.QuanitatInvalidException;
import classes.Comanda;
import controladors.CtrlComandes;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;


public class TestCtrlComandes {
    private CtrlComandes ctrl;

    @Test
    public void testCrearComanda() {
        CtrlComandes ctrl = new CtrlComandes();
        ctrl.crearComanda("Comanda1");
        assertTrue(ctrl.existeixComanda("Comanda1"));
    }
    @Test
    public void testCrearComandaDuplicadaLanzaExcepcion() {
        CtrlComandes ctrl = new CtrlComandes();
        ctrl.crearComanda("Comanda1");
        assertThrows(IllegalArgumentException.class, () -> ctrl.crearComanda("Comanda1"));
    }


    @Test
    public void testCrearComandaAutomatica() throws QuanitatInvalidException {
        CtrlComandes ctrl = new CtrlComandes();
        Map<String, Integer> productos = new HashMap<>();
        productos.put("Manzana", 5);
        productos.put("Pera", 3);

        ctrl.crearComandaAutomatica("ComandaAuto", productos);
        Comanda comanda = ctrl.getComandaUnica("ComandaAuto");

        assertEquals(5, comanda.getQuantitat("Manzana"));
        assertEquals(3, comanda.getQuantitat("Pera"));
    }

    @Test
    public void testEliminarComanda() {
        CtrlComandes ctrl = new CtrlComandes();
        ctrl.crearComanda("Comanda1");
        ctrl.eliminarComanda("Comanda1");
        assertFalse(ctrl.existeixComanda("Comanda1"));
    }
    @Test
    public void testEliminarComandaNoExistente() {
        CtrlComandes ctrl = new CtrlComandes();
        assertThrows(ComandaNotFoundException.class, () -> ctrl.eliminarComanda("ComandaNoExiste"));
    }

    @Test
    public void testAfegirProducteComanda() throws QuanitatInvalidException {
        CtrlComandes ctrl = new CtrlComandes();
        ctrl.crearComanda("Comanda1");
        ctrl.afegirProducteComanda("Comanda1", "Manzana", 10);
        assertEquals(10, ctrl.getComandaUnica("Comanda1").getQuantitat("Manzana"));
    }

    @Test
    public void testAfegirProducteCantidadNegativaLanzaExcepcion() {
        CtrlComandes ctrl = new CtrlComandes();
        ctrl.crearComanda("Comanda1");
        assertThrows(QuanitatInvalidException.class, () -> ctrl.afegirProducteComanda("Comanda1", "Manzana", -5));
    }

    @Test
    public void testEliminarProducteComanda() throws QuanitatInvalidException, ProductNotFoundComandaException {
        CtrlComandes ctrl = new CtrlComandes();
        ctrl.crearComanda("Comanda1");
        ctrl.afegirProducteComanda("Comanda1", "Manzana", 10);
        ctrl.eliminarProducteComanda("Comanda1", "Manzana", 10);
        assertFalse(ctrl.getComandaUnica("Comanda1").conteProducte("Manzana"));
    }

    @Test
    public void testEliminarProductoInexistenteLanzaExcepcionNoExistsProduct() throws QuanitatInvalidException {
        CtrlComandes ctrl = new CtrlComandes();
        ctrl.crearComanda("Comanda1");
        ctrl.afegirProducteComanda("Comanda1", "Manzana", 5);
        assertThrows(ProductNotFoundComandaException.class, () -> ctrl.eliminarProducteComanda("Comanda1", "Pera", 3));
    }



    @Test
    public void testObtenirComandesInexistentes() {
        CtrlComandes ctrl = new CtrlComandes();
        String[] nomsComandes = {"Comanda1", "Comanda2"};
        assertThrows(ComandaNotFoundException.class, () -> ctrl.obtenirComandes(nomsComandes));
    }


    @Test
    public void testGetComandaUnica() throws ComandaNotFoundException {
        CtrlComandes ctrl = new CtrlComandes();
        ctrl.crearComanda("Comanda1");
        Comanda comanda = ctrl.getComandaUnica("Comanda1");
        assertNotNull(comanda);
        assertEquals("Comanda1", comanda.getNom() );
    }

    @Test
    public void testGetComandaUnicaInexistenteLanzaExcepcion() {
        CtrlComandes ctrl = new CtrlComandes();
        assertThrows(ComandaNotFoundException.class, () -> ctrl.getComandaUnica("NoExiste"));
    }

    @Test
    public void testGetComandes() {
        CtrlComandes ctrl = new CtrlComandes();
        ctrl.crearComanda("Comanda1");
        ctrl.crearComanda("Comanda2");
        assertEquals(2, ctrl.getComandes().size());
    }



}
