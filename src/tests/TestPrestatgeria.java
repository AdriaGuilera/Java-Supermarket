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
        prestatgeria.afegir_producte("Producte A", 5);
        assertTrue(prestatgeria.esta_a_prestatgeria("Producte A"));
    }

    @Test(expected = PrestatgeriaFullException.class)
    public void testAfegirProductePrestatgeriaPlena() throws JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        for (int i = 0; i < 10; i++) {
            prestatgeria.afegir_producte("Producte " + i, 1);
        }
        prestatgeria.afegir_producte("Producte Extra", 1);
    }

    @Test(expected = JaExisteixProucteaPrestatgeriaException.class)
    public void testAfegirProducteJaExisteix() throws JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegir_producte("Producte A", 5);
        prestatgeria.afegir_producte("Producte A", 5);
    }

    // Moure Producte de Hueco
    @Test
    public void testMoureProducte() throws ProducteNotInHuecoException, InvalidHuecosException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegir_producte("Producte A", 5);
        prestatgeria.moure_producte(0, 1);
        assertEquals(1, prestatgeria.get_pos("Producte A"));
    }

    @Test(expected = InvalidHuecosException.class)
    public void testMoureProducteHuecoDestiNoExisteix() throws ProducteNotInHuecoException, InvalidHuecosException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegir_producte("Producte A", 5);
        prestatgeria.moure_producte(0, 10);
    }

    // Fixar Producte
    @Test
    public void testFixarProducte() throws ProductNotFoundPrestatgeriaException, ProducteFixatException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegir_producte("Producte A", 5);
        prestatgeria.fixar_producte_prestatgeria("Producte A");
        assertTrue(prestatgeria.getProductesFixats().contains("Producte A"));
    }

    @Test(expected = ProducteFixatException.class)
    public void testFixarProducteJaFixat() throws ProductNotFoundPrestatgeriaException, ProducteFixatException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegir_producte("Producte A", 5);
        prestatgeria.fixar_producte_prestatgeria("Producte A");
        prestatgeria.fixar_producte_prestatgeria("Producte A");
    }

    // Desfixar Producte
    @Test
    public void testDesfixarProducte() throws ProductNotFoundPrestatgeriaException, ProducteFixatException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegir_producte("Producte A", 5);
        prestatgeria.fixar_producte_prestatgeria("Producte A");
        prestatgeria.desfixar_producte_prestatgeria("Producte A");
        assertFalse(prestatgeria.getProductesFixats().contains("Producte A"));
    }
    @Test (expected = ProducteNoFixatException.class)
    public void testDesfixarProducteNoFixat() throws ProductNotFoundPrestatgeriaException, ProducteNoFixatException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegir_producte("Producte A", 5);
        prestatgeria.desfixar_producte_prestatgeria("Producte A");
    }

    // Decrementar Stock a Producte
    @Test
    public void testDecrementarQuantitatProducte() throws QuanitatInvalidException, ProductNotFoundPrestatgeriaException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegir_producte("Producte A", 10);
        prestatgeria.decrementar_quantitat("Producte A", 5);
        assertEquals(5, prestatgeria.get_quantProducte("Producte A"));
    }

    @Test(expected = QuanitatInvalidException.class)
    public void testDecrementarQuantitatProducteQuantitatNegativa() throws QuanitatInvalidException, ProductNotFoundPrestatgeriaException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegir_producte("Producte A", 10);
        prestatgeria.decrementar_quantitat("Producte A", -5);
    }

    @Test(expected = ProductNotFoundPrestatgeriaException.class)
    public void testDecrementarQuantitatProducteNoExisteix() throws QuanitatInvalidException, ProductNotFoundPrestatgeriaException {
        prestatgeria.decrementar_quantitat("Producte B", 5);
    }

    // Afegir Prestatge
    @Test
    public void testAfegirPrestatge() {
        prestatgeria.afegir_prestatge();
        assertEquals(12, prestatgeria.getMidaPrestatgeria());
    }

    // Eliminar Prestatge
    @Test
    public void testEliminarPrestatge() {
        prestatgeria.afegir_producte("Producte A", 5);
        prestatgeria.afegir_prestatge();
        prestatgeria.eliminar_prestatge();
        assertEquals(10, prestatgeria.getMidaPrestatgeria());
    }

    @Test(expected = QuanitatInvalidException.class)
    public void testAfegirProducteQuantitatNegativa() throws JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException, QuanitatInvalidException {
        prestatgeria.afegir_producte("Producte A", -5);
    }

    @Test(expected = QuanitatInvalidException.class)
    public void AfegirProducteQuantitatZero() throws JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException, QuanitatInvalidException {
        prestatgeria.afegir_producte("Producte A", 0);
        assertEquals(0, prestatgeria.get_quantProducte("Producte A"));
    }

    // Moure Producte de Hueco
    @Test
    public void testMoureProducteHuecoJaOcupat() throws ProducteNotInHuecoException, InvalidHuecosException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegir_producte("Producte A", 5);
        prestatgeria.afegir_producte("Producte B", 5);
        prestatgeria.moure_producte(0, 1);
        assertEquals(1, prestatgeria.get_pos("Producte A"));
        assertEquals(0, prestatgeria.get_pos("Producte B"));
    }

    @Test
    public void testMoureProductePrestatgeriaPlena() throws ProducteNotInHuecoException, InvalidHuecosException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        for (int i = 0; i < 10; i++) {
            prestatgeria.afegir_producte("Producte " + i, 1);
        }
        prestatgeria.moure_producte(0, 1);
        assertEquals(1, prestatgeria.get_pos("Producte 0"));
        assertEquals(0, prestatgeria.get_pos("Producte 1"));
    }


    @Test(expected = ProductNotFoundPrestatgeriaException.class)
    public void testFixarProducteHuecoNoExisteix() throws PrestatgeriaNotFoundException, ProductNotFoundPrestatgeriaException, ProducteFixatException {
        prestatgeria.fixar_producte_prestatgeria("Producte Inexistent");
    }

    // Desfixar Producte
    @Test(expected = ProductNotFoundPrestatgeriaException.class)
    public void testDesfixarProducteNoExistent() throws ProductNotFoundPrestatgeriaException, ProducteFixatException {
        prestatgeria.desfixar_producte_prestatgeria("Producte Inexistent");
    }

    // Eliminar Prestatge
    @Test
    public void testEliminarPrestatgeAmbProductes() throws PrestatgeriaNotFoundException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        prestatgeria.afegir_producte("Producte A", 5);
        prestatgeria.moure_producte(0, prestatgeria.getMidaPrestatgeria()-1);
         Map<String, Integer> aeliminar = new HashMap<>();
         aeliminar.put("Producte A", 5);
         assertEquals(aeliminar,prestatgeria.eliminar_prestatge());
    }


}