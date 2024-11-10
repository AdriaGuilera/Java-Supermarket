package tests;


import classes.Caixa;
import classes.Pair;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class TestCaixa {

    private Caixa caixa;

    @Before
    public void setUp() {
        caixa = new Caixa(); // Inicializa la caja antes de cada test
    }

    @Test
    public void testAfegirProducte() {
        // Añadir un producto
        caixa.afegir_producte("Manzanas", 10, "A1");

        // Verificar que el producto se añadió correctamente
        assertEquals(10, caixa.get_quantitat("Manzanas", "A1"));

        // Añadir el mismo producto a la misma estantería con diferente cantidad
        caixa.afegir_producte("Manzanas", 5, "A1");

        // Verificar que la cantidad total es ahora 15
        assertEquals(15, caixa.get_quantitat("Manzanas", "A1"));
    }

    @Test
    public void testRetirarProducte() {
        // Añadir productos
        caixa.afegir_producte("Manzanas", 10, "A1");
        caixa.afegir_producte("Manzanas", 5, "B1");

        // Retirar productos correctamente
        caixa.retirar_producte("Manzanas", 5, "A1");
        assertEquals(5, caixa.get_quantitat("Manzanas", "A1"));

        // Intentar retirar más de la cantidad disponible
        caixa.retirar_producte("Manzanas", 10, "B1");  // No hay suficientes
        assertEquals(5, caixa.get_quantitat("Manzanas", "B1"));

        // Eliminar la estantería "A1"
        caixa.retirar_producte("Manzanas", 5, "A1");
        assertEquals(0, caixa.get_quantitat("Manzanas", "A1"));
    }

    @Test
    public void testGetQuantitat() {
        // Añadir productos
        caixa.afegir_producte("Manzanas", 10, "A1");

        // Verificar la cantidad del producto
        assertEquals(10, caixa.get_quantitat("Manzanas", "A1"));

        // Verificar que un producto no existente devuelve 0
        assertEquals(0, caixa.get_quantitat("Peras", "A1"));
    }

    @Test
    public void testImprimirTicket() {
        // Añadir productos
        caixa.afegir_producte("Manzanas", 10, "A1");
        caixa.afegir_producte("Peras", 5, "B1");

        // Verificar la salida del ticket impreso (redirigir System.out a un stream de prueba)
        // Esta prueba puede ser más compleja si deseas capturar la salida
        caixa.imprimir_ticket_per_prestatgeries();
        caixa.imprimirticket();
    }

    @Test
    public void testPagar() {
        // Añadir productos
        caixa.afegir_producte("Manzanas", 10, "A1");

        // Verificar antes de pagar
        assertEquals(10, caixa.get_quantitat("Manzanas", "A1"));

        // Ejecutar pagar, lo que limpia la caja
        caixa.pagar();

        // Verificar que todos los productos han sido eliminados
        assertEquals(0, caixa.get_quantitat("Manzanas", "A1"));
    }
}

