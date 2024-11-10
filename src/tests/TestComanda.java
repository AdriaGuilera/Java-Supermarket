package tests;


import classes.Comanda;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class TestComanda {

    private Comanda comanda;

    @Before
    public void setUp() {
        comanda = new Comanda("Comanda Test");
    }

    @Test
    public void testGetNom() {
        assertEquals("Comanda Test", comanda.getNom());
    }

    @Test
    public void testAfegirProducte() {
        comanda.afegirProducte("Pa", 3);
        comanda.afegirProducte("Llet", 2);

        Map<String, Integer> ordres = comanda.getOrdres();
        assertEquals(2, ordres.size());
        assertTrue(ordres.containsKey("Pa"));
        assertEquals(3, (int) ordres.get("Pa"));
        assertTrue(ordres.containsKey("Llet"));
        assertEquals(2, (int) ordres.get("Llet"));
    }

    @Test
    public void testEliminarProducte() {
        comanda.afegirProducte("Cafè", 1);
        assertTrue(comanda.getOrdres().containsKey("Cafè"));

        comanda.eliminarProducte("Cafè");
        assertFalse(comanda.getOrdres().containsKey("Cafè"));
    }

    @Test
    public void testObtenirQuantitatProducte() {
        comanda.afegirProducte("Suc", 5);
        assertEquals(5, comanda.obtenirQuantitatProducte("Suc"));

        // Si el producto no existe, debe devolver 0
        assertEquals(0, comanda.obtenirQuantitatProducte("Pa"));
    }

    @Test
    public void testGetOrdres() {
        comanda.afegirProducte("Aigua", 2);
        comanda.afegirProducte("Refresc", 3);

        Map<String, Integer> ordres = comanda.getOrdres();
        assertEquals(2, ordres.size());
        assertEquals(2, (int) ordres.get("Aigua"));
        assertEquals(3, (int) ordres.get("Refresc"));
    }
}
