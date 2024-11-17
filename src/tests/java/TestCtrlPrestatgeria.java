package tests;

import controladors.CtrlPrestatgeria;
import Exepcions.*;
        import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TestCtrlPrestatgeria {

    private CtrlPrestatgeria ctrlPrestatgeria;

    @Before
    public void setUp() {
        // Initialize a new CtrlPrestatgeria object before each test
        ctrlPrestatgeria = new CtrlPrestatgeria();
    }

    // Afegir Prestatgeria
    @Test
    public void testAfegirPrestatgeria() throws MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        assertNotNull(ctrlPrestatgeria.getPrestatgeria("ID1"));
    }

    @Test(expected = MidaPrestatgeriaInvalidException.class)
    public void testAfegirPrestatgeriaMidaNegativa() throws MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException {
        ctrlPrestatgeria.afegirPrestatgeria("ID2", -5, 2);
    }

    @Test(expected = MidaPrestatgeriaInvalidException.class)
    public void testAfegirPrestatgeriaMidaZero() throws MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException {
        ctrlPrestatgeria.afegirPrestatgeria("ID3", 0, 2);
    }

    @Test(expected = PrestatgeriaJaExisteixException.class)
    public void testAfegirPrestatgeriaJaExisteix() throws MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
    }

    // Eliminar Prestatgeria
    @Test
    public void testEliminarPrestatgeria() throws PrestatgeriaNotFoundException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        ctrlPrestatgeria.eliminarPrestatgeria("ID1");
        try {
            ctrlPrestatgeria.getPrestatgeria("ID1");
            fail("Expected a PrestatgeriaNotFoundException to be thrown");
        } catch (PrestatgeriaNotFoundException e) {
            // Expected exception
        }
    }

    @Test(expected = PrestatgeriaNotFoundException.class)
    public void testEliminarPrestatgeriaNoExisteix() throws PrestatgeriaNotFoundException {
        ctrlPrestatgeria.eliminarPrestatgeria("ID2");
    }

    // Afegir Producte a Prestatgeria
    @Test
    public void testAfegirProducte() throws PrestatgeriaNotFoundException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        ctrlPrestatgeria.afegirProducte("ID1", "Producte A", 5);
        assertTrue(ctrlPrestatgeria.containsProducte("ID1", "Producte A"));
    }

    @Test(expected = PrestatgeriaNotFoundException.class)
    public void testAfegirProductePrestatgeriaNoExisteix() throws PrestatgeriaNotFoundException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        ctrlPrestatgeria.afegirProducte("ID2", "Producte A", 5);
    }

    @Test(expected = JaExisteixProucteaPrestatgeriaException.class)
    public void testAfegirProducteJaExisteix() throws PrestatgeriaNotFoundException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        ctrlPrestatgeria.afegirProducte("ID1", "Producte A", 5);
        ctrlPrestatgeria.afegirProducte("ID1", "Producte A", 5);
    }

    @Test(expected = PrestatgeriaFullException.class)
    public void testAfegirProductePrestatgeriaPlena() throws PrestatgeriaNotFoundException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 2, 1);
        ctrlPrestatgeria.afegirProducte("ID1", "Producte A", 1);
        ctrlPrestatgeria.afegirProducte("ID1", "Producte B", 1);
        ctrlPrestatgeria.afegirProducte("ID1", "Producte C", 1);
    }

    // Moure Producte de Hueco
    @Test
    public void testMoureProducte() throws PrestatgeriaNotFoundException, ProducteNotInHuecoException, InvalidHuecosException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        ctrlPrestatgeria.afegirProducte("ID1", "Producte A", 5);
        ctrlPrestatgeria.moureProducte("ID1", 0, 1);
        assertEquals(1, ctrlPrestatgeria.getPrestatgeria("ID1").getPos("Producte A"));
    }

    @Test(expected = PrestatgeriaNotFoundException.class)
    public void testMoureProductePrestatgeriaNoExisteix() throws PrestatgeriaNotFoundException, ProducteNotInHuecoException, InvalidHuecosException {
        ctrlPrestatgeria.moureProducte("ID2", 0, 1);
    }

    @Test(expected = ProducteNotInHuecoException.class)
    public void testMoureProducteHuecoOrigenNoExisteix() throws PrestatgeriaNotFoundException, ProducteNotInHuecoException, InvalidHuecosException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        ctrlPrestatgeria.moureProducte("ID1", 0, 1);
    }

    @Test(expected = InvalidHuecosException.class)
    public void testMoureProducteHuecoDestiNoExisteix() throws PrestatgeriaNotFoundException, ProducteNotInHuecoException, InvalidHuecosException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        ctrlPrestatgeria.afegirProducte("ID1", "Producte A", 5);
        ctrlPrestatgeria.moureProducte("ID1", 0, 10);
    }

    // Fixar Producte
    @Test
    public void testFixarProducte() throws PrestatgeriaNotFoundException, ProductNotFoundPrestatgeriaException, ProducteFixatException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        ctrlPrestatgeria.afegirProducte("ID1", "Producte A", 5);
        ctrlPrestatgeria.fixarProducte("ID1", "Producte A");
        assertTrue(ctrlPrestatgeria.getProductesFixats("ID1").contains("Producte A"));
    }

    @Test(expected = PrestatgeriaNotFoundException.class)
    public void testFixarProductePrestatgeriaNoExisteix() throws PrestatgeriaNotFoundException, ProductNotFoundPrestatgeriaException, ProducteFixatException {
        ctrlPrestatgeria.fixarProducte("ID2", "Producte A");
    }

    @Test(expected = ProductNotFoundPrestatgeriaException.class)
    public void testFixarProducteNoExisteix() throws PrestatgeriaNotFoundException, ProductNotFoundPrestatgeriaException, ProducteFixatException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        ctrlPrestatgeria.fixarProducte("ID1", "Producte B");
    }

    @Test(expected = ProducteFixatException.class)
    public void testFixarProducteJaFixat() throws PrestatgeriaNotFoundException, ProductNotFoundPrestatgeriaException, ProducteFixatException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        ctrlPrestatgeria.afegirProducte("ID1", "Producte A", 5);
        ctrlPrestatgeria.fixarProducte("ID1", "Producte A");
        ctrlPrestatgeria.fixarProducte("ID1", "Producte A");
    }

    // Desfixar Producte
    @Test
    public void testDesfixarProducte() throws PrestatgeriaNotFoundException, ProductNotFoundPrestatgeriaException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException, ProducteFixatException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        ctrlPrestatgeria.afegirProducte("ID1", "Producte A", 5);
        ctrlPrestatgeria.fixarProducte("ID1", "Producte A");
        ctrlPrestatgeria.desfixarProducte("ID1", "Producte A");
        assertFalse(ctrlPrestatgeria.getProductesFixats("ID1").contains("Producte A"));
    }

    @Test(expected = PrestatgeriaNotFoundException.class)
    public void testDesfixarProductePrestatgeriaNoExisteix() throws PrestatgeriaNotFoundException, ProductNotFoundPrestatgeriaException {
        ctrlPrestatgeria.desfixarProducte("ID2", "Producte A");
    }

    @Test(expected = ProductNotFoundPrestatgeriaException.class)
    public void testDesfixarProducteNoExisteix() throws PrestatgeriaNotFoundException, ProductNotFoundPrestatgeriaException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        ctrlPrestatgeria.desfixarProducte("ID1", "Producte B");
    }

    // Decrementar Stock a Producte
    @Test
    public void testDecrementarQuantitatProducte() throws PrestatgeriaNotFoundException, QuanitatInvalidException, ProductNotFoundPrestatgeriaException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        ctrlPrestatgeria.afegirProducte("ID1", "Producte A", 10);
        ctrlPrestatgeria.decrementarQuantitatProducte("ID1", "Producte A", 5);
        assertEquals(5, ctrlPrestatgeria.getQuantitatProducte("ID1", "Producte A"));
    }

    @Test(expected = PrestatgeriaNotFoundException.class)
    public void testDecrementarQuantitatProductePrestatgeriaNoExisteix() throws PrestatgeriaNotFoundException, QuanitatInvalidException, ProductNotFoundPrestatgeriaException {
        ctrlPrestatgeria.decrementarQuantitatProducte("ID2", "Producte A", 5);
    }

    @Test(expected = ProductNotFoundPrestatgeriaException.class)
    public void testDecrementarQuantitatProducteNoExisteix() throws PrestatgeriaNotFoundException, QuanitatInvalidException, ProductNotFoundPrestatgeriaException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        ctrlPrestatgeria.decrementarQuantitatProducte("ID1", "Producte B", 5);
    }

    @Test(expected = QuanitatInvalidException.class)
    public void testDecrementarQuantitatProducteQuantitatNegativa() throws PrestatgeriaNotFoundException, QuanitatInvalidException, ProductNotFoundPrestatgeriaException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        ctrlPrestatgeria.afegirProducte("ID1", "Producte A", 10);
        ctrlPrestatgeria.decrementarQuantitatProducte("ID1", "Producte A", -5);
    }

    // Afegir Prestatge
    @Test
    public void testAfegirPrestatge() throws PrestatgeriaNotFoundException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        ctrlPrestatgeria.afegirPrestatge("ID1");
        assertEquals(12, ctrlPrestatgeria.getPrestatgeria("ID1").getMidaPrestatgeria());
    }

    @Test(expected = PrestatgeriaNotFoundException.class)
    public void testAfegirPrestatgePrestatgeriaNoExisteix() throws PrestatgeriaNotFoundException {
        ctrlPrestatgeria.afegirPrestatge("ID2");
    }

    // Eliminar Prestatge
    @Test
    public void testEliminarPrestatge() throws PrestatgeriaNotFoundException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        ctrlPrestatgeria.afegirPrestatge("ID1");
        ctrlPrestatgeria.eliminarPrestatge("ID1");
        assertEquals(10, ctrlPrestatgeria.getPrestatgeria("ID1").getMidaPrestatgeria());
    }

    @Test(expected = PrestatgeriaNotFoundException.class)
    public void testEliminarPrestatgePrestatgeriaNoExisteix() throws PrestatgeriaNotFoundException {
        ctrlPrestatgeria.eliminarPrestatge("ID2");
    }

    @Test(expected = QuanitatInvalidException.class)
    public void testAfegirProducteQuantitatNegativa() throws PrestatgeriaNotFoundException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException, QuanitatInvalidException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        ctrlPrestatgeria.afegirProducte("ID1", "Producte A", -5);
    }

    // Moure Producte de Hueco
    @Test(expected = PrestatgeriaNotFoundException.class)
    public void testMoureProducteHuecoPrestatgeriaNoExistent() throws PrestatgeriaNotFoundException, ProducteNotInHuecoException, InvalidHuecosException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        ctrlPrestatgeria.moureProducte("ID1", 0, 1);
    }

    // Decrementar Stock a Producte
    @Test
    public void testDecrementarQuantitatProducteQuantitatMesGranQueStock() throws PrestatgeriaNotFoundException, QuanitatInvalidException, ProductNotFoundPrestatgeriaException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        ctrlPrestatgeria.afegirProducte("ID1", "Producte A", 10);
        assertEquals(10, ctrlPrestatgeria.decrementarQuantitatProducte("ID1", "Producte A", 15));
    }

    @Test
    public void testDecrementarQuantitatProducteStockZero() throws PrestatgeriaNotFoundException, QuanitatInvalidException, ProductNotFoundPrestatgeriaException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        ctrlPrestatgeria.afegirProducte("ID1", "Producte A", 10);
        System.out.println(ctrlPrestatgeria.decrementarQuantitatProducte("ID1", "Producte A", 10));
        assertEquals(0, ctrlPrestatgeria.decrementarQuantitatProducte("ID1", "Producte A", 5));
    }

    // Afegir Prestatgeria
    @Test(expected = MidaPrestatgeriaInvalidException.class)
    public void testAfegirPrestatgeriaMidaPrestatgesZero() throws MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException {
        ctrlPrestatgeria.afegirPrestatgeria("ID4", 10, 0);
    }

    // Eliminar Prestatgeria
    @Test
    public void testEliminarPrestatgeriaAmbProductesIStock() throws PrestatgeriaNotFoundException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException, JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        ctrlPrestatgeria.afegirProducte("ID1", "Producte A", 5);
        ctrlPrestatgeria.afegirProducte("ID1", "Producte B", 5);
        ctrlPrestatgeria.afegirProducte("ID1", "Producte C", 5);
        Map<String,Integer> aeliminar = new HashMap<>();
        aeliminar.put("Producte A", 5);
        aeliminar.put("Producte B", 5);
        aeliminar.put("Producte C", 5);
        assertEquals(aeliminar, ctrlPrestatgeria.eliminarPrestatgeria("ID1"));
    }

    @Test
    public void testEliminarPrestatgeriaSenseProductes() throws PrestatgeriaNotFoundException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException {
        ctrlPrestatgeria.afegirPrestatgeria("ID1", 10, 2);
        Map<String,Integer> aeliminar = new HashMap<>();
        assertEquals(aeliminar, ctrlPrestatgeria.eliminarPrestatgeria("ID1"));
    }

    @Test (expected = PrestatgeriaNotFoundException.class)
    public void testEliminiarPrestgeriaNoExisteix() throws PrestatgeriaNotFoundException {
        ctrlPrestatgeria.eliminarPrestatgeria("ID1");
    }
}