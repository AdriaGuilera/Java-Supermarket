package tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import classes.Prestatgeria;
import java.util.Map;
import java.util.Vector;
import java.util.Set;

public class TestPrestatgeria {

    private Prestatgeria prestatgeria;

    @Before
    public void setUp() {
        // Este método se ejecuta antes de cada test para inicializar un nuevo objeto Prestatgeria
        prestatgeria = new Prestatgeria("ID1", 2, 1);
    }

    @Test
    public void testConstructor() {
        assertEquals("ID1", prestatgeria.getid());
        assertEquals(2, prestatgeria.getMidaPrestatgeria());
        assertEquals(0, prestatgeria.getProductesSize()); // La prestatgeria debería estar vacía al principio
    }

    @Test
    public void testAfegirProducte() {
        prestatgeria.afegir_producte("Producto A", 10);
        assertTrue(prestatgeria.esta_a_prestatgeria("Producto A"));
        assertEquals(Integer.valueOf(10), prestatgeria.get_productes().get("Producto A"));
    }

    @Test
    public void testEliminarProducte() {
        prestatgeria.afegir_producte("Producto A", 10);
        prestatgeria.eliminar_producte("Producto A");
        assertFalse(prestatgeria.esta_a_prestatgeria("Producto A"));
    }

    @Test
    public void testIncrementarQuantitat() {
        prestatgeria.afegir_producte("Producto A", 10);
        prestatgeria.incrementar_quantitat("Producto A", 5);
        assertEquals(Integer.valueOf(15), prestatgeria.get_productes().get("Producto A"));
    }

    @Test
    public void testDecrementarQuantitat() {
        prestatgeria.afegir_producte("Producto A", 10);
        prestatgeria.decrementar_quantitat("Producto A", 5);
        assertEquals(Integer.valueOf(5), prestatgeria.get_productes().get("Producto A"));
    }

    @Test
    public void testFixarProducte() {
        prestatgeria.afegir_producte("Producto A", 10);
        prestatgeria.fixar_producte_prestatgeria("Producto A");
        Set<String> fixats = prestatgeria.getProductesFixats();
        assertTrue(fixats.contains("Producto A"));
    }

    @Test
    public void testDesfixarProducte() {
        prestatgeria.afegir_producte("Producto A", 10);
        prestatgeria.fixar_producte_prestatgeria("Producto A");
        prestatgeria.desfixar_producte_prestatgeria("Producto A");
        Set<String> fixats = prestatgeria.getProductesFixats();
        assertFalse(fixats.contains("Producto A"));
    }

    @Test
    public void testAfegirPrestatge() {

        prestatgeria.afegir_prestatge();
        assertEquals(2 + 1, prestatgeria.getMidaPrestatgeria());
    }

    @Test
    public void testEliminarPrestatge() {
        prestatgeria.afegir_producte("Producto A", 10);
        prestatgeria.afegir_producte("Producto B", 20);
        Map<String, Integer> eliminados = prestatgeria.eliminar_prestatge();
        assertEquals(1, eliminados.size()); // Debería eliminar un producto
        assertFalse(prestatgeria.esta_a_prestatgeria("Producto B"));
    }

    @Test
    public void testMoureProducte() {
        prestatgeria.afegir_producte("Producto A", 10);

        prestatgeria.moure_producte(0, 1); // Mover "Producto A" a la posición 1
        assertEquals(1, prestatgeria.get_pos("Producto A"));
    }

    @Test
    public void testSetDistribucio() {
        prestatgeria.afegir_producte("Producto A", 10);
        prestatgeria.afegir_producte("Producto B", 20);
        Vector<String> nuevaDistribucion = new Vector<>();
        nuevaDistribucion.add("Producto B");
        nuevaDistribucion.add("Producto A");
        prestatgeria.setDistribucio(nuevaDistribucion);
        // Verificar que la distribución ha cambiado
        assertEquals("Producto B", prestatgeria.getNomsProductes().get(0));
        assertEquals("Producto A", prestatgeria.getNomsProductes().get(1));
    }

    @Test
    public void testImprimirDistribucio() {
        prestatgeria.afegir_producte("Producto A", 10);
        prestatgeria.afegir_producte("Producto B", 20);
        // Este test verifica que no se lancen excepciones, sin capturar System.out.
        prestatgeria.imprimirdistribucio();
    }
}
