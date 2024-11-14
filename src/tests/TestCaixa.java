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
        caixa.afegir_producte("Manzanas", 10);

        // Verificar que el producto se añadió correctamente
        assertEquals(10, caixa.get_quantitat("Manzanas"));

        // Añadir el mismo producto a la misma estantería con diferente cantidad
        caixa.afegir_producte("Manzanas", 5);

        // Verificar que la cantidad total es ahora 15
        assertEquals(15, caixa.get_quantitat("Manzanas"));
    }

    @Test
    public void testRetirarProducte() {
        // Añadir productos
        caixa.afegir_producte("Manzanas", 10);
        caixa.afegir_producte("Manzanas", 5);

        // Retirar productos correctamente
        assertEquals(15, caixa.get_quantitat("Manzanas"));

        // Intentar retirar más de la cantidad disponible
        caixa.retirar_producte("Manzanas", 10);  // No hay suficientes
        assertEquals(5, caixa.get_quantitat("Manzanas"));

        // Eliminar la estantería "A1"
        caixa.retirar_producte("Manzanas", 5);
        assertEquals(0, caixa.get_quantitat("Manzanas"));
    }

    @Test
    public void testGetQuantitat() {
        // Añadir productos
        caixa.afegir_producte("Manzanas", 10);

        // Verificar la cantidad del producto
        assertEquals(10, caixa.get_quantitat("Manzanas"));

        // Verificar que un producto no existente devuelve 0
        assertEquals(0, caixa.get_quantitat("Peras"));
    }

    @Test
    public void testImprimirTicket() {
        // Añadir productos
        caixa.afegir_producte("Peras", 5);
        caixa.afegir_producte("Peras", 5);

        // Verificar la salida del ticket impreso (redirigir System.out a un stream de prueba)
        // Esta prueba puede ser más compleja si deseas capturar la salida
        // caixa.imprimir_ticket_per_prestatgeries();
        // caixa.imprimirticket();
    }

    @Test
    public void testPagar() {
        // Añadir productos
        caixa.afegir_producte("Manzanas", 10);

        // Verificar antes de pagar
        assertEquals(10, caixa.get_quantitat("Manzanas"));

        // Ejecutar pagar, lo que limpia la caja
        caixa.pagar();

        // Verificar que todos los productos han sido eliminados
        assertEquals(0, caixa.get_quantitat("Manzanas"));
    }
}

