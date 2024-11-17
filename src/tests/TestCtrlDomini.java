package tests;

import classes.Comanda;
import controladors.CtrlDomini;
import Exepcions.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCtrlDomini {

    private CtrlDomini ctrlDomini;

    @Before
    public void setUp() {
        ctrlDomini = new CtrlDomini();
    }
    @Test
    public void testReposarPrestatgeria()
    throws PrestatgeriaNotFoundException, ProductNotFoundMagatzemException,
            QuanitatInvalidException, MidaPrestatgeriaInvalidException, PrestatgeriaJaExisteixException,
            JaExisteixProucteaPrestatgeriaException, PrestatgeriaFullException {

        ctrlDomini.altaProducte("Producte A", 10, 55,55);
        ctrlDomini.altaProducte("Producte B", 10, 50,50);

        ctrlDomini.afegirPrestatgeria("Prestatgeria1", 2, 10);

        ctrlDomini.afegirProductePrestatgeria("Producte A", 5, "Prestatgeria1");
        ctrlDomini.afegirProductePrestatgeria("Producte B", 3, "Prestatgeria1");

        // Reposem la prestatgeria
        ctrlDomini.reposarPrestatgeria("Prestatgeria1");

        // Verifiquem que els productes s'han reposat correctament
        assertEquals(10, ctrlDomini.ctrlPrestatgeria.getQuantitatProducte("Prestatgeria1", "Producte A"));
        assertEquals(10, ctrlDomini.ctrlPrestatgeria.getQuantitatProducte("Prestatgeria1", "Producte B"));
        assertEquals(45, ctrlDomini.ctrlProducte.getStockMagatzem("Producte A"));
        assertEquals(40, ctrlDomini.ctrlProducte.getStockMagatzem("Producte B"));
    }
    @Test
    public void testAfegirProductePrestatgeria() throws Exception {
        // Set up initial state
        ctrlDomini.altaProducte("Producte A", 10, 100, 50);
        ctrlDomini.afegirPrestatgeria("Prestatgeria1", 2, 10);

        // Perform the action
        ctrlDomini.afegirProductePrestatgeria("Producte A", 5, "Prestatgeria1");

        // Verify the results
        assertEquals(5, ctrlDomini.ctrlPrestatgeria.getQuantitatProducte("Prestatgeria1", "Producte A"));
        assertEquals(45, ctrlDomini.ctrlProducte.getStockMagatzem("Producte A"));
    }

    @Test(expected = QuanitatInvalidException.class)
    public void testAfegirProductePrestatgeriaQuantitatZero() throws Exception {
        ctrlDomini.altaProducte("Producte A", 10, 100, 50);
        ctrlDomini.afegirProductePrestatgeria("Producte A", 0, "Prestatgeria1");
    }

    @Test(expected = QuanitatInvalidException.class)
    public void testAfegirProductePrestatgeriaQuantitatNegativa() throws Exception {
        ctrlDomini.altaProducte("Producte A", 10, 100, 50);
        ctrlDomini.afegirProductePrestatgeria("Producte A", -1, "Prestatgeria1");
    }

    @Test(expected = PrestatgeriaNotFoundException.class)
    public void testAfegirProductePrestatgeriaPrestatgeriaNotFound() throws Exception {
        ctrlDomini.altaProducte("Producte A", 10, 100, 50);
        ctrlDomini.afegirProductePrestatgeria("Producte A", 5, "PrestatgeriaInexistent");
    }

    @Test(expected = ProductNotFoundMagatzemException.class)
    public void testAfegirProductePrestatgeriaProductNotFound() throws Exception {
        ctrlDomini.altaProducte("Producte A", 10, 100, 50);
        ctrlDomini.afegirProductePrestatgeria("Producte Inexistent", 5, "Prestatgeria1");
    }
    @Test
    public void testDecrementarProductePrestatgeria(){
        // Set up initial state
        ctrlDomini.altaProducte("Producte A", 10, 100, 50);
        ctrlDomini.afegirPrestatgeria("Prestatgeria1", 2, 10);
        ctrlDomini.afegirProductePrestatgeria("Producte A", 5, "Prestatgeria1");

        // Perform the action
        ctrlDomini.decrementarStockAProducte("Prestatgeria1", "Producte A", 3);

        // Verify the results
        assertEquals(2, ctrlDomini.ctrlPrestatgeria.getQuantitatProducte("Prestatgeria1", "Producte A"));
        assertEquals(48, ctrlDomini.ctrlProducte.getStockMagatzem("Producte A"));
    }
    @Test
    public void testRetirarProducteAMagatzem(){
        ctrlDomini.altaProducte("Producte A", 10, 100, 50);
        ctrlDomini.afegirPrestatgeria("Prestatgeria1", 2, 10);
        ctrlDomini.afegirProductePrestatgeria("Producte A", 5, "Prestatgeria1");

        // Perform the action
        ctrlDomini.retirarProducteAMagatzem("Prestatgeria1", "Producte A");

        // Verify the results
        assertFalse(ctrlDomini.ctrlPrestatgeria.containsProducte("Prestatgeria1", "Producte A"));
        assertEquals(50, ctrlDomini.ctrlProducte.getStockMagatzem("Producte A"));
    }

    @Test
    public void testExecutarComandaAutomaitca(){
        // Set up products
        ctrlDomini.altaProducte("Producto A", 100, 500,200);
        ctrlDomini.altaProducte("Producto B", 50, 300,100);

        // Create automatic order
        ctrlDomini.generarComandaAutomatica("ComandaAuto");

        // Verify the automatic order
        Comanda comandaAuto = ctrlDomini.getComandaUnica("ComandaAuto");
        assertNotNull(comandaAuto);
        assertEquals(300, comandaAuto.getQuantitat("Producto A"));
        assertEquals(200, comandaAuto.getQuantitat("Producto B"));
    }
    @Test
    public void testAfegirProducteCaixaSuccess(){
        ctrlDomini.altaProducte("Producte A", 10, 100,15);
        ctrlDomini.afegirPrestatgeria("Prestatgeria1", 2, 10);
        ctrlDomini.afegirProductePrestatgeria("Producte A", 5, "Prestatgeria1");

        int quantitatAfegida = ctrlDomini.afegir_producte_caixa("Producte A", 3, "Prestatgeria1");

        assertEquals(3, quantitatAfegida);
        assertEquals(2, ctrlDomini.ctrlPrestatgeria.getQuantitatProducte("Prestatgeria1", "Producte A"));
        assertEquals(3, ctrlDomini.caixa.getQuantitat("Producte A"));
    }

    @Test(expected = ProductNotFoundMagatzemException.class)
    public void testAfegirProducteCaixaProductNotFound() throws Exception {
        ctrlDomini.afegirPrestatgeria("Prestatgeria1", 2, 10);
        ctrlDomini.afegir_producte_caixa("Producte Inexistent", 3, "Prestatgeria1");
    }

    @Test(expected = PrestatgeriaNotFoundException.class)
    public void testAfegirProducteCaixaPrestatgeriaNotFound() throws Exception {
        ctrlDomini.altaProducte("Producte A", 10, 100,15);
        ctrlDomini.afegir_producte_caixa("Producte A", 3, "PrestatgeriaInexistent");
    }

    @Test
    public void testRetirarProducteCaixa(){
        ctrlDomini.altaProducte("Producte A", 10, 100,15);
        ctrlDomini.afegirPrestatgeria("Prestatgeria1", 2, 10);
        ctrlDomini.afegirProductePrestatgeria("Producte A", 5, "Prestatgeria1");
        ctrlDomini.afegir_producte_caixa("Producte A", 3, "Prestatgeria1");

        ctrlDomini.retirar_producte_caixa("Producte A", 2, "Prestatgeria1");

        assertEquals(1, ctrlDomini.caixa.getQuantitat("Producte A"));
        assertEquals(2, ctrlDomini.ctrlPrestatgeria.getQuantitatProducte("Prestatgeria1", "Producte A"));
        assertEquals(12, ctrlDomini.ctrlProducte.getStockMagatzem("Producte A"));
    }


    @Test(expected = QuanitatInvalidException.class)
    public void testAfegirProducteCaixaQuantitatInvalid() throws Exception {
        ctrlDomini.altaProducte("Producte A", 10, 100,15);
        ctrlDomini.afegirPrestatgeria("Prestatgeria1", 2, 10);
        ctrlDomini.afegirProductePrestatgeria("Producte A", 5, "Prestatgeria1");

        ctrlDomini.afegir_producte_caixa("Producte A", -1, "Prestatgeria1");
    }

    @Test(expected = ProductNotFoundPrestatgeriaException.class)
    public void testAfegirProducteCaixaProductNotInPrestatgeria() throws Exception {
        ctrlDomini.altaProducte("Producte A", 10, 100,15);
        ctrlDomini.afegirPrestatgeria("Prestatgeria1", 2, 10);
        ctrlDomini.afegir_producte_caixa("Producte A", 3, "Prestatgeria1");
    }

    @Test
    public void testAfegirProducteComandaSuccess() throws Exception {
        ctrlDomini.crearComanda("Comanda1");
        ctrlDomini.altaProducte("Producte A", 10, 100, 50);

        ctrlDomini.afegirProducteComanda("Comanda1", "Producte A", 5);

        Comanda comanda = ctrlDomini.getComandaUnica("Comanda1");
        assertEquals(5, comanda.getQuantitat("Producte A"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCrearComandaNomBuit() {
        ctrlDomini.crearComanda("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEliminarComandaNomBuit() {
        ctrlDomini.eliminarComanda("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAfegirProducteComandaNomComandaBuit() throws Exception {
        ctrlDomini.afegirProducteComanda("", "Producte A", 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAfegirProducteComandaNomProducteBuit() throws Exception {
        ctrlDomini.afegirProducteComanda("Comanda1", "", 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAfegirProducteComandaQuantitatZero() throws Exception {
        ctrlDomini.afegirProducteComanda("Comanda1", "Producte A", 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAfegirProductePrestatgeriaNomPrestatgeriaBuit() throws Exception {
        ctrlDomini.afegirProductePrestatgeria("Producte A", 5, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAfegirProductePrestatgeriaNomProducteBuit() throws Exception {
        ctrlDomini.afegirProductePrestatgeria("", 5, "Prestatgeria1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAfegirPrestatgeriaNomBuit() throws Exception {
        ctrlDomini.afegirPrestatgeria("", 2, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEliminarPrestatgeriaNomBuit() throws Exception {
        ctrlDomini.eliminarPrestatgeria("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAfegirProducteCaixaNomProducteBuit() throws Exception {
        ctrlDomini.afegir_producte_caixa("", 3, "Prestatgeria1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAfegirProducteCaixaNomPrestatgeriaBuit() throws Exception {
        ctrlDomini.afegir_producte_caixa("Producte A", 3, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRetirarProducteCaixaNomProducteBuit() throws Exception {
        ctrlDomini.retirar_producte_caixa("", 3, "Prestatgeria1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRetirarProducteCaixaNomPrestatgeriaBuit() throws Exception {
        ctrlDomini.retirar_producte_caixa("Producte A", 3, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testObtenirComandaAutomaticaNomBuit() {
        ctrlDomini.generarComandaAutomatica("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltaProducteNomBuit() throws Exception {
        ctrlDomini.altaProducte("", 10, 100, 50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltaProducteMaxHuecoZero() throws Exception {
        ctrlDomini.altaProducte("Producte A", 0, 100, 50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltaProducteMaxMagatzemZero() throws Exception {
        ctrlDomini.altaProducte("Producte A", 10, 0, 50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEliminarProducteNomBuit() throws Exception {
        ctrlDomini.eliminarProducte("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAfegirSimilitudNom1Buit() throws Exception {
        ctrlDomini.afegir_similitud("", "Producte B", 0.5f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAfegirSimilitudNom2Buit() throws Exception {
        ctrlDomini.afegir_similitud("Producte A", "", 0.5f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAfegirSimilitudValorZero() throws Exception {
        ctrlDomini.afegir_similitud("Producte A", "Producte B", 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEliminarSimilitudNom1Buit() throws Exception {
        ctrlDomini.eliminarSimilitud("", "Producte B");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEliminarSimilitudNom2Buit() throws Exception {
        ctrlDomini.eliminarSimilitud("Producte A", "");
    }

}