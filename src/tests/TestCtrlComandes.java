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
    public CtrlComandes ctrlComandes;

    @Before
    public void setUp() { ctrlComandes = new CtrlComandes(); }

    @Test
    public void testCrearComanda() {

        ctrlComandes.crearComanda("Comanda1");
        assertTrue(ctrlComandes.existeixComanda("Comanda1"));
    }

    @Test
    public void testCrearComandaDuplicadaLanzaExcepcion() {

        ctrlComandes.crearComanda("Comanda1");
        assertThrows(IllegalArgumentException.class, () -> ctrlComandes.crearComanda("Comanda1"));
    }


    @Test
    public void testCrearComandaAutomatica() throws QuanitatInvalidException {

        Map<String, Integer> productos = new HashMap<>();
        productos.put("Manzana", 5);
        productos.put("Pera", 3);

        ctrlComandes.crearComandaAutomatica("ComandaAuto", productos);
        Comanda comanda = ctrlComandes.getComandaUnica("ComandaAuto");

        assertEquals(5, comanda.getQuantitat("Manzana"));
        assertEquals(3, comanda.getQuantitat("Pera"));
    }

    @Test
    public void testCrearComandaAutomaticaConProductosCantidadNegativaLanzaExcepcion() throws QuanitatInvalidException {

        Map<String, Integer> productos = new HashMap<>();
        productos.put("Manzana", -1);

        assertThrows(QuanitatInvalidException.class, () -> ctrlComandes.crearComandaAutomatica("ComandaAuto", productos));
    }

    @Test
    public void testCrearComandaAutomaticaConProductosCantidadCeroYaExiste() {

        ctrlComandes.crearComanda("ComandaAuto");

        Map<String, Integer> productos = new HashMap<>();
        productos.put("Manzana", 0);

        // Intenta crear una comanda automática con el mismo nombre
        assertThrows(IllegalArgumentException.class, () -> ctrlComandes.crearComandaAutomatica("ComandaAuto", productos));
    }

    @Test
    public void testEliminarComanda() {

        ctrlComandes.crearComanda("Comanda1");
        ctrlComandes.eliminarComanda("Comanda1");
        assertFalse(ctrlComandes.existeixComanda("Comanda1"));
    }
    @Test
    public void testEliminarComandaNoExistenteLanzaExcepcion() throws ComandaNotFoundException {

        assertThrows(ComandaNotFoundException.class, () -> ctrlComandes.eliminarComanda("ComandaNoExiste"));
    }

    @Test
    public void testAfegirProducteComanda() throws QuanitatInvalidException {

        ctrlComandes.crearComanda("Comanda1");
        ctrlComandes.afegirProducteComanda("Comanda1", "Manzana", 10);
        assertEquals(10, ctrlComandes.getComandaUnica("Comanda1").getQuantitat("Manzana"));
    }

    @Test
    public void testAfegirProducteCantidadNegativaLanzaExcepcion() {

        ctrlComandes.crearComanda("Comanda1");
        assertThrows(QuanitatInvalidException.class, () -> ctrlComandes.afegirProducteComanda("Comanda1", "Manzana", -5));
    }

    @Test
    public void testEliminarGranCantidadProducteComanda() throws QuanitatInvalidException, ProductNotFoundComandaException {

        ctrlComandes.crearComanda("Comanda1");
        ctrlComandes.afegirProducteComanda("Comanda1", "Manzana", 10);
        ctrlComandes.eliminarProducteComanda("Comanda1", "Manzana", 50);
        assertFalse(ctrlComandes.getComandaUnica("Comanda1").conteProducte("Manzana"));
    }

    @Test
    public void testEliminarProductoConCantidadExactaEliminaProducto() throws QuanitatInvalidException, ProductNotFoundComandaException {

        ctrlComandes.crearComanda("Comanda1");
        ctrlComandes.afegirProducteComanda("Comanda1", "Manzana", 10);
        ctrlComandes.eliminarProducteComanda("Comanda1", "Manzana", 10);
        assertFalse(ctrlComandes.getComandaUnica("Comanda1").conteProducte("Manzana"));
    }
    @Test
    public void testEliminarProductoInexistenteLanzaExcepcionNoExistsProduct() throws QuanitatInvalidException {

        ctrlComandes.crearComanda("Comanda1");
        ctrlComandes.afegirProducteComanda("Comanda1", "Manzana", 5);
        assertThrows(ProductNotFoundComandaException.class, () -> ctrlComandes.eliminarProducteComanda("Comanda1", "Pera", 3));
    }



    @Test
    public void testObtenirComandesInexistentesLanzaExcepcion() throws ComandaNotFoundException {

        String[] nomsComandes = {"Comanda1", "Comanda2"};
        assertThrows(ComandaNotFoundException.class, () -> ctrlComandes.obtenirComandes(nomsComandes));
    }


    @Test
    public void testGetComandaUnica() throws ComandaNotFoundException {

        ctrlComandes.crearComanda("Comanda1");
        Comanda comanda = ctrlComandes.getComandaUnica("Comanda1");
        assertNotNull(comanda);
        assertEquals("Comanda1", comanda.getNom() );
    }

    @Test
    public void testGetComandaUnicaInexistenteLanzaExcepcion() {

        assertThrows(ComandaNotFoundException.class, () -> ctrlComandes.getComandaUnica("NoExiste"));
    }

    @Test
    public void testGetComandes() {

        ctrlComandes.crearComanda("Comanda1");
        ctrlComandes.crearComanda("Comanda2");
        assertEquals(2, ctrlComandes.getComandes().size());
    }



}
