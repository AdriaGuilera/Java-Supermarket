package tests;

import classes.Prestatgeria;
import Exepcions.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TestPrestatgeria {

    private Prestatgeria prestatgeria;

    @Before
    public void setUp() {
        // Initialize a new Prestatgeria object before each test
        prestatgeria = new Prestatgeria("ID1", 10, 2);
    }

    // Afegir Producte a Prestatgeria
    @Test
    public void testAfegirProducte() throws JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegirProducte("Producte A", 5);
        assertTrue(prestatgeria.estaAPrestatgeria("Producte A"));
    }

    @Test(expected = PrestatgeriaFullException.class)
    public void testAfegirProductePrestatgeriaPlena() throws JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        for (int i = 0; i < 10; i++) {
            prestatgeria.afegirProducte("Producte " + i, 1);
        }
        prestatgeria.afegirProducte("Producte Extra", 1);
    }

    @Test(expected = JaExisteixProucteaPrestatgeriaException.class)
    public void testAfegirProducteJaExisteix() throws JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegirProducte("Producte A", 5);
        prestatgeria.afegirProducte("Producte A", 5);
    }

    // Moure Producte de Hueco
    @Test
    public void testMoureProducte() throws ProducteNotInHuecoException, InvalidHuecosException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegirProducte("Producte A", 5);
        prestatgeria.moureProducte(0, 1);
        assertEquals(1, prestatgeria.getPos("Producte A"));
    }

    @Test(expected = InvalidHuecosException.class)
    public void testMoureProducteHuecoDestiNoExisteix() throws ProducteNotInHuecoException, InvalidHuecosException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegirProducte("Producte A", 5);
        prestatgeria.moureProducte(0, 10);
    }

    // Fixar Producte
    @Test
    public void testFixarProducte() throws ProductNotFoundPrestatgeriaException, ProducteFixatException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegirProducte("Producte A", 5);
        prestatgeria.fixarProductePrestatgeria("Producte A");
        assertTrue(prestatgeria.getProductesFixats().contains("Producte A"));
    }

    @Test(expected = ProducteFixatException.class)
    public void testFixarProducteJaFixat() throws ProductNotFoundPrestatgeriaException, ProducteFixatException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegirProducte("Producte A", 5);
        prestatgeria.fixarProductePrestatgeria("Producte A");
        prestatgeria.fixarProductePrestatgeria("Producte A");
    }

    // Desfixar Producte
    @Test
    public void testDesfixarProducte() throws ProductNotFoundPrestatgeriaException, ProducteFixatException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegirProducte("Producte A", 5);
        prestatgeria.fixarProductePrestatgeria("Producte A");
        prestatgeria.desfixarProductePrestatgeria("Producte A");
        assertFalse(prestatgeria.getProductesFixats().contains("Producte A"));
    }
    @Test (expected = ProducteNoFixatException.class)
    public void testDesfixarProducteNoFixat() throws ProductNotFoundPrestatgeriaException, ProducteNoFixatException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegirProducte("Producte A", 5);
        prestatgeria.desfixarProductePrestatgeria("Producte A");
    }

    // Decrementar Stock a Producte
    @Test
    public void testDecrementarQuantitatProducte() throws QuanitatInvalidException, ProductNotFoundPrestatgeriaException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegirProducte("Producte A", 10);
        prestatgeria.decrementarQuantitat("Producte A", 5);
        assertEquals(5, prestatgeria.getQuantProducte("Producte A"));
    }

    @Test(expected = QuanitatInvalidException.class)
    public void testDecrementarQuantitatProducteQuantitatNegativa() throws QuanitatInvalidException, ProductNotFoundPrestatgeriaException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegirProducte("Producte A", 10);
        prestatgeria.decrementarQuantitat("Producte A", -5);
    }

    @Test(expected = ProductNotFoundPrestatgeriaException.class)
    public void testDecrementarQuantitatProducteNoExisteix() throws QuanitatInvalidException, ProductNotFoundPrestatgeriaException {
        prestatgeria.decrementarQuantitat("Producte B", 5);
    }

    // Afegir Prestatge
    @Test
    public void testAfegirPrestatge() {
        prestatgeria.afegirPrestatge();
        assertEquals(12, prestatgeria.getMidaPrestatgeria());
    }

    // Eliminar Prestatge
    @Test
    public void testEliminarPrestatge() {
        prestatgeria.afegirProducte("Producte A", 5);
        prestatgeria.afegirPrestatge();
        prestatgeria.eliminarPrestatge();
        assertEquals(10, prestatgeria.getMidaPrestatgeria());
    }

    @Test(expected = QuanitatInvalidException.class)
    public void testAfegirProducteQuantitatNegativa() throws JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException, QuanitatInvalidException {
        prestatgeria.afegirProducte("Producte A", -5);
    }

    @Test(expected = QuanitatInvalidException.class)
    public void AfegirProducteQuantitatZero() throws JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException, QuanitatInvalidException {
        prestatgeria.afegirProducte("Producte A", 0);
        assertEquals(0, prestatgeria.getQuantProducte("Producte A"));
    }

    // Moure Producte de Hueco
    @Test
    public void testMoureProducteHuecoJaOcupat() throws ProducteNotInHuecoException, InvalidHuecosException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegirProducte("Producte A", 5);
        prestatgeria.afegirProducte("Producte B", 5);
        prestatgeria.moureProducte(0, 1);
        assertEquals(1, prestatgeria.getPos("Producte A"));
        assertEquals(0, prestatgeria.getPos("Producte B"));
    }

    @Test
    public void testMoureProductePrestatgeriaPlena() throws ProducteNotInHuecoException, InvalidHuecosException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        for (int i = 0; i < 10; i++) {
            prestatgeria.afegirProducte("Producte " + i, 1);
        }
        prestatgeria.moureProducte(0, 1);
        assertEquals(1, prestatgeria.getPos("Producte 0"));
        assertEquals(0, prestatgeria.getPos("Producte 1"));
    }


    @Test(expected = ProductNotFoundPrestatgeriaException.class)
    public void testFixarProducteHuecoNoExisteix() throws PrestatgeriaNotFoundException, ProductNotFoundPrestatgeriaException, ProducteFixatException {
        prestatgeria.fixarProductePrestatgeria("Producte Inexistent");
    }

    // Desfixar Producte
    @Test(expected = ProductNotFoundPrestatgeriaException.class)
    public void testDesfixarProducteNoExistent() throws ProductNotFoundPrestatgeriaException, ProducteFixatException {
        prestatgeria.desfixarProductePrestatgeria("Producte Inexistent");
    }

    // Eliminar Prestatge
    @Test
    public void testEliminarPrestatgeAmbProductes() throws PrestatgeriaNotFoundException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegirProducte("Producte A", 5);
        prestatgeria.moureProducte(0, prestatgeria.getMidaPrestatgeria()-1);
         Map<String, Integer> aeliminar = new HashMap<>();
         aeliminar.put("Producte A", 5);
         assertEquals(aeliminar,prestatgeria.eliminarPrestatge());
    }


}