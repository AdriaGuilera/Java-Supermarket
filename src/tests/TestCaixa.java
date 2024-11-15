package tests;

import classes.Caixa;
import Exepcions.NotEnoughQuantityCaixaWarning;
import Exepcions.ProductNotFoundCaixaException;
import Exepcions.QuanitatInvalidException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCaixa {

    private Caixa caixa;

    @Before
    public void setUp() {
        caixa = new Caixa(); // Initialize the box before each test
    }

    @Test
    public void testAfegirProducte() throws QuanitatInvalidException {
        // Add a product
        caixa.afegir_producte("Manzanas", 10);

        // Verify that the product was added correctly
        assertEquals(10, caixa.get_quantitat("Manzanas"));

        // Add the same product with a different quantity
        caixa.afegir_producte("Manzanas", 5);

        // Verify that the total quantity is now 15
        assertEquals(15, caixa.get_quantitat("Manzanas"));
    }

    @Test(expected = QuanitatInvalidException.class)
    public void testAfegirProducteQuantitatNegativa() throws QuanitatInvalidException {
        // Attempt to add a product with a negative quantity
        caixa.afegir_producte("Manzanas", -5);
    }

    @Test(expected = QuanitatInvalidException.class)
    public void testAfegirProducteQuantitatZero() throws QuanitatInvalidException {
        // Attempt to add a product with a quantity of zero
        caixa.afegir_producte("Manzanas", 0);
    }

    @Test(expected = ProductNotFoundCaixaException.class)
    public void testRetirarProducteNoExisteix() throws NotEnoughQuantityCaixaWarning, ProductNotFoundCaixaException {
        // Attempt to remove a product that does not exist
        caixa.retirar_producte("Peras", 5);
    }

    @Test(expected = NotEnoughQuantityCaixaWarning.class)
    public void testRetirarProducteQuantitatGran() throws QuanitatInvalidException, NotEnoughQuantityCaixaWarning, ProductNotFoundCaixaException {
        // Add a product
        caixa.afegir_producte("Manzanas", 10);
        // Attempt to remove more than the available quantity
        caixa.retirar_producte("Manzanas", 15);
    }

    @Test(expected = QuanitatInvalidException.class)
    public void testRetirarProducteQuantitatNegativa() throws NotEnoughQuantityCaixaWarning, ProductNotFoundCaixaException, QuanitatInvalidException {
        // Add a product
        caixa.afegir_producte("Manzanas", 10);
        // Attempt to remove a negative quantity
        caixa.retirar_producte("Manzanas", -5);
    }

    @Test
    public void testRetirarProducte() throws QuanitatInvalidException, NotEnoughQuantityCaixaWarning, ProductNotFoundCaixaException {
        // Add products
        caixa.afegir_producte("Manzanas", 10);
        caixa.afegir_producte("Manzanas", 5);

        // Verify total quantity before removal
        assertEquals(15, caixa.get_quantitat("Manzanas"));

        // Remove products correctly
        caixa.retirar_producte("Manzanas", 10);
        assertEquals(5, caixa.get_quantitat("Manzanas"));

        // Remove remaining products
        caixa.retirar_producte("Manzanas", 5);
        assertEquals(0, caixa.get_quantitat("Manzanas"));
    }

    @Test
    public void testGetQuantitat() throws ProductNotFoundCaixaException {
        // Add products
        caixa.afegir_producte("Manzanas", 10);

        // Verify the quantity of the product
        assertEquals(10, caixa.get_quantitat("Manzanas"));

        // Verify that a non-existent product throws an exception
        try {
            caixa.get_quantitat("Peras");
            fail("Expected a ProductNotFoundCaixaException to be thrown");
        } catch (ProductNotFoundCaixaException e) {
            // Expected exception
        }
    }

    @Test
    public void testPagarCaixaBuida() {
        // Pay when the box is empty
        caixa.pagar();
        assertTrue(caixa.getticket().isEmpty());
    }

    @Test
    public void testPagar() throws QuanitatInvalidException {
        // Add products
        caixa.afegir_producte("Manzanas", 10);
        // Execute pay, which clears the box
        caixa.pagar();
        // Verify that all products have been removed
        assertTrue(caixa.getticket().isEmpty());
    }
}