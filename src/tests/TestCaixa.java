package tests;

import classes.Caixa;
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
        caixa.afegirProducte("Manzanas", 10);

        // Verify that the product was added correctly
        assertEquals(10, caixa.getQuantitat("Manzanas"));

        // Add the same product with a different quantity
        caixa.afegirProducte("Manzanas", 5);

        // Verify that the total quantity is now 15
        assertEquals(15, caixa.getQuantitat("Manzanas"));
    }

    @Test(expected = QuanitatInvalidException.class)
    public void testAfegirProducteQuantitatNegativa() throws QuanitatInvalidException {
        // Attempt to add a product with a negative quantity
        caixa.afegirProducte("Manzanas", -5);
    }

    @Test(expected = QuanitatInvalidException.class)
    public void testAfegirProducteQuantitatZero() throws QuanitatInvalidException {
        // Attempt to add a product with a quantity of zero
        caixa.afegirProducte("Manzanas", 0);
    }

    @Test(expected = ProductNotFoundCaixaException.class)
    public void testRetirarProducteNoExisteix() throws ProductNotFoundCaixaException {
        // Attempt to remove a product that does not exist
        caixa.retirarProducte("Peras", 5);
    }


    @Test
    public void testRetirarProducte() throws QuanitatInvalidException, ProductNotFoundCaixaException {
        // Add products
        caixa.afegirProducte("Manzanas", 10);
        caixa.afegirProducte("Manzanas", 5);

        // Verify total quantity before removal
        assertEquals(15, caixa.getQuantitat("Manzanas"));

        // Remove products correctly
        caixa.retirarProducte("Manzanas", 10);
        assertEquals(5, caixa.getQuantitat("Manzanas"));

        // Remove remaining products
        caixa.retirarProducte("Manzanas", 5);
        assertThrows( ProductNotFoundCaixaException.class, () -> caixa.getQuantitat("Manzanas"));
    }

    @Test
    public void testGetQuantitat() throws ProductNotFoundCaixaException {
        // Add products
        caixa.afegirProducte("Manzanas", 10);

        // Verify the quantity of the product
        assertEquals(10, caixa.getQuantitat("Manzanas"));

        // Verify that a non-existent product throws an exception
        try {
            caixa.getQuantitat("Peras");
            fail("Expected a ProductNotFoundCaixaException to be thrown");
        } catch (ProductNotFoundCaixaException e) {
            // Expected exception
        }
    }

    @Test
    public void testPagarCaixaBuida() {
        // Pay when the box is empty
        caixa.pagar();
        assertTrue(caixa.getTicket().isEmpty());
    }

    @Test
    public void testPagar() throws QuanitatInvalidException {
        // Add products
        caixa.afegirProducte("Manzanas", 10);
        // Execute pay, which clears the box
        caixa.pagar();
        // Verify that all products have been removed
        assertTrue(caixa.getTicket().isEmpty());
    }
}