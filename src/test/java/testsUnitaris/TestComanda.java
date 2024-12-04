package testsUnitaris;

import Exepcions.QuanitatInvalidException;
import classes.Comanda;
import Exepcions.ProducteAlreadyExistsException;
import Exepcions.ProductNotFoundComandaException;
import org.junit.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import java.util.*;

public class TestComanda {
    public Comanda comanda;

    @Before
    public void setUp() {
        comanda = new Comanda("Test Order");
    }

    @Test
    public void testConstructor() {
        assertEquals("Test Order", comanda.getNom());
        assertTrue(comanda.getOrdres().isEmpty());
    }

    @Test
    public void testAfegirProducte_ProductoNuevo() throws ProducteAlreadyExistsException {
        comanda.afegirProducte("Manzana", 10);
        assertEquals(10, comanda.getQuantitat("Manzana"));
    }



    @Test
    public void testAfegirProducte_ActualizarCantidad() throws QuanitatInvalidException {
        comanda.afegirProducte("Manzana", 5);
        comanda.afegirProducte("Manzana", 3);
        assertEquals(8, comanda.getQuantitat("Manzana"));
    }
    @Test
    public void testComanda_GranCantidadDeProductos() throws QuanitatInvalidException {
        for (int i = 0; i < 10000; i++) {
            comanda.afegirProducte("Producto" + i, 1);
        }
        assertEquals(10000, comanda.getOrdres().size()); // Comprobar que se añadieron todos los productos
    }

    @Test
    public void testEliminarProducte_ProductoExistente() throws ProducteAlreadyExistsException, ProductNotFoundComandaException {
        comanda.afegirProducte("Manzana", 10);
        comanda.eliminarProducte("Manzana",10);
        assertFalse(comanda.conteProducte("Manzana"));
    }

    @Test
    public void testEliminarProducte_ProductoNoExistente() {
        assertThrows(ProductNotFoundComandaException.class, () -> comanda.eliminarProducte("Plátano",10));
    }

    @Test
    public void testEliminarProducte_MasCantidadDeLaQueExiste() throws QuanitatInvalidException, ProductNotFoundComandaException {
        comanda.afegirProducte("Manzana", 5);
        comanda.eliminarProducte("Manzana", 10);
        assertFalse(comanda.conteProducte("Manzana")); // El producto debería eliminarse por completo
    }

    @Test
    public void testEliminarProducte_AunExisteCantidad() throws QuanitatInvalidException, ProductNotFoundComandaException {
        comanda.afegirProducte("Manzana", 10);
        comanda.eliminarProducte("Manzana", 3);
        assertEquals(7,comanda.getQuantitat("Manzana")); // El producto debería eliminarse por completo
    }

    @Test
    public void testEliminarProducte_EnPasos() throws QuanitatInvalidException, ProductNotFoundComandaException {
        comanda.afegirProducte("Manzana", 10);
        comanda.eliminarProducte("Manzana", 4);
        assertEquals(6, comanda.getQuantitat("Manzana"));
        comanda.eliminarProducte("Manzana", 6);
        assertFalse(comanda.conteProducte("Manzana"));
    }

    @Test
    public void testGetNom() {
        comanda = new Comanda("Mi Primera Comanda");
        assertEquals("Mi Primera Comanda", comanda.getNom());
    }
    @Test
    public void testConteProducte_ProductoExistente() throws ProducteAlreadyExistsException {
        comanda.afegirProducte("Manzana", 10);
        assertTrue(comanda.conteProducte("Manzana"));
    }

    @Test
    public void testConteProducte_ProductoNoExistente() {
        assertFalse(comanda.conteProducte("Pera"));
    }
    @Test
    public void testGetQuantitat_ProductoExistente() throws ProducteAlreadyExistsException {
        comanda.afegirProducte("Manzana", 10);
        assertEquals(10, comanda.getQuantitat("Manzana"));
    }

    @Test
    public void testGetQuantitat_ProductoNoExistente() {
        assertThrows(ProductNotFoundComandaException.class, () -> comanda.getQuantitat("Pera"));
    }



    @Test
    public void testGetOrdres() {
        comanda.afegirProducte("Manzana", 10);
        comanda.afegirProducte("Pera", 5);
        assertEquals(2, comanda.getOrdres().size());
    }

    @Test
    public void testGetOrdres_Inmutabilidad() throws ProducteAlreadyExistsException {
        comanda.afegirProducte("Manzana", 10);
        Map<String, Integer> ordres = comanda.getOrdres();
        ordres.put("Pera", 5); // Intento de modificar la copia
        assertFalse(comanda.conteProducte("Pera")); // La comanda original no debería cambiar
    }


}
