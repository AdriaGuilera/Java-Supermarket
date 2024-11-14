package tests;

import classes.Comanda;
import Exepcions.ProducteJaExisteixException;
import Exepcions.ProductNotFoundComandaException;
import org.junit.*;

import static org.junit.Assert.*;
import java.util.*;

public class TestComanda {
    private Comanda comanda;

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
    public void testAfegirProducte_ProductoNuevo() throws ProducteJaExisteixException {
        comanda.afegirProducte("Manzana", 10);
        assertEquals(10, comanda.getQuantitat("Manzana"));
    }

    @Test
    public void testAfegirProducte_ProductoExistente() {
        assertThrows(ProducteJaExisteixException.class, () -> {
            comanda.afegirProducte("Manzana", 10);
            comanda.afegirProducte("Manzana", 5); // Debería lanzar la excepción
        });
    }

    @Test
    public void testAfegirProducte_CantidadInvalida() {
        assertThrows(IllegalArgumentException.class, () -> comanda.afegirProducte("Pera", 0));
        assertThrows(IllegalArgumentException.class, () -> comanda.afegirProducte("Pera", -5));
    }

    @Test
    public void testEliminarProducte_ProductoExistente() throws ProducteJaExisteixException, ProductNotFoundComandaException {
        comanda.afegirProducte("Manzana", 10);
        comanda.eliminarProducte("Manzana");
        assertFalse(comanda.conteProducte("Manzana"));
    }

    @Test
    public void testEliminarProducte_ProductoNoExistente() {
        assertThrows(ProductNotFoundComandaException.class, () -> comanda.eliminarProducte("Plátano"));
    }

    @Test
    public void testGetQuantitat_ProductoExistente() throws ProducteJaExisteixException {
        comanda.afegirProducte("Manzana", 10);
        assertEquals(10, comanda.getQuantitat("Manzana"));
    }

    @Test
    public void testGetQuantitat_ProductoNoExistente() {
        assertThrows(ProductNotFoundComandaException.class, () -> comanda.getQuantitat("Pera"));
    }

    @Test
    public void testConteProducte_ProductoExistente() throws ProducteJaExisteixException {
        comanda.afegirProducte("Manzana", 10);
        assertTrue(comanda.conteProducte("Manzana"));
    }

    @Test
    public void testConteProducte_ProductoNoExistente() {
        assertFalse(comanda.conteProducte("Pera"));
    }

    @Test
    public void testGetOrdres() {
        comanda.afegirProducte("Manzana", 10);
        comanda.afegirProducte("Pera", 5);
        assertEquals(2, comanda.getOrdres().size());
    }

    @Test
    public void testGetOrdres_Inmutabilidad() throws ProducteJaExisteixException {
        comanda.afegirProducte("Manzana", 10);
        Map<String, Integer> ordres = comanda.getOrdres();
        ordres.put("Pera", 5); // Intento de modificar la copia
        assertFalse(comanda.conteProducte("Pera")); // La comanda original no debería cambiar
    }

    @Test
    public void testAfegirProducte_CantidadNegativa() {
        assertThrows(IllegalArgumentException.class, () -> {
            comanda.afegirProducte("Manzana", -10);
        });
    }

}
