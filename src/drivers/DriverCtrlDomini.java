package drivers;

import Exepcions.ProductNotFoundCaixaException;
import classes.Comanda;
import classes.Prestatgeria;
import classes.Producte;
import controladors.CtrlDomini;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

/**
 * Esta es una clase driver para probar la funcionalidad de la clase CtrlDomini.
 * Proporciona una interfaz de línea de comandos para probar operaciones relacionadas con
 * Comanda, Prestatgeria, Caixa y Magatzem (Producte). El programa puede ejecutarse en modo interactivo
 * o leyendo comandos desde un archivo.
 * Uso:
 * - En modo interactivo, el usuario introduce manualmente los comandos.
 * - En modo archivo, el programa lee comandos de un archivo especificado.
 */
public class DriverCtrlDomini {


    // Comanda
    /** Comando para crear una Comanda. */
    private static final String CREAR_COMANDA = "1";

    /** Comando para eliminar una Comanda. */
    private static final String ELIMINAR_COMANDA = "2";

    /** Comando para agregar un producto a una Comanda. */
    private static final String AFEGIR_PRODUCTE_COMANDA = "3";

    /** Comando para eliminar un producto de una Comanda. */
    private static final String ELIMINAR_PRODUCTE_COMANDA = "4";

    /** Comando para consultar los detalles de una Comanda específica. */
    private static final String CONSULTAR_COMANDA_UNICA = "5";

    /** Comando para listar todas las Comandas creadas. */
    private static final String CONSULTAR_COMANDES = "6";

    /** Comando para generar una Comanda automática. */
    private static final String GENERAR_COMANDA_AUTOMATICA = "7";

    /** Comando para ejecutar las Comandas seleccionadas. */
    private static final String EXECUTAR_COMANDES = "8";


    // Prestatgeria

    /** Comando para agregar una nueva Prestatgeria. */
    private static final String AFEGIR_PRESTATGERIA = "9";

    /** Comando para agregar un Prestatge a una Prestatgeria. */
    private static final String AFEGIR_PRESTATGE = "10";

    /** Comando para eliminar una Prestatgeria. */
    private static final String ELIMINAR_PRESTATGERIA = "11";

    /** Comando para eliminar un Prestatge de una Prestatgeria. */
    private static final String ELIMINAR_PRESTATGE = "12";

    /** Comando para agregar un producto a una Prestatgeria. */
    private static final String AFEGIR_PRODUCTE_PRESTATGERIA = "13";

    /** Comando para mover un producto al Magatzem desde una Prestatgeria. */
    private static final String RETIRAR_PRODUCTE_A_MAGATZEM = "14";

    /** Comando para decrementar el stock de un producto. */
    private static final String DECREMENTAR_STOCK_A_PRODUCTE = "15";

    /** Comando para mover un producto entre espacios en una Prestatgeria. */
    private static final String MOURE_PRODUCTE_DE_HUECO = "16";

    /** Comando para fijar un producto en un espacio de una Prestatgeria. */
    private static final String FIXAR_PRODUCTE = "17";

    /** Comando para desfijar un producto en un espacio de una Prestatgeria. */
    private static final String DESFIXAR_PRODUCTE = "18";

    /** Comando para generar una distribución usando backtracking. */
    private static final String GENERAR_DISTRIBUCIO_BACKTRACKING = "19";

    /** Comando para generar una distribución usando hill climbing. */
    private static final String GENERAR_DISTRIBUCIO_HILL_CLIMBING = "20";

    /** Comando para reponer una Prestatgeria. */
    private static final String REPOSAR_PRESTATGERIA = "21";

    /** Comando para imprimir los detalles de una Prestatgeria. */
    private static final String CONSULTAR_PRESTATGERIA = "22";


    // Caixa

    /** Comando para agregar un producto a la Caixa. */
    private static final String AFEGIR_PRODUCTE_CAIXA = "23";

    /** Comando para retirar un producto de la Caixa. */
    private static final String RETIRAR_PRODUCTE_CAIXA = "24";

    /** Comando para pagar y vaciar la Caixa. */
    private static final String PAGAR = "25";

    /** Comando para imprimir los detalles de la Caixa. */
    private static final String CONSULTAR_CAIXA = "26";


    // Magatzem (Producte)

    /** Comando para dar de alta un nuevo producto en el Magatzem. */
    private static final String ALTA_PRODUCTE = "27";

    /** Comando para eliminar un producto del Magatzem. */
    private static final String ELIMINAR_PRODUCTE = "28";

    /** Comando para agregar similitud entre productos. */
    private static final String AFEGIR_SIMILITUD = "29";

    /** Comando para eliminar similitud entre productos. */
    private static final String ELIMINA_SIMILITUD = "30";

    /** Comando para consultar los detalles de un producto. */
    private static final String CONSULTAR_PRODUCTE = "31";

    /** Comando para consultar los detalles de todos los productos en el Magatzem. */
    private static final String CONSULTAR_MAGATZEM = "32";


    // Comandos adicionales

    /** Comando para mostrar el texto de ayuda. */
    private static final String HELP = "help";

    /** Comando para salir del programa. */
    private static final String EXIT = "exit";

    /** Texto de ayuda mostrado al usuario. */
    private static final String HELPTEXT = "Seleccione un comando (con el número):\n" +
            "GESTIÓ DE COMANDES:\n" +
            "   " + CREAR_COMANDA + " - Crear una Comanda\n" +
            "   " + ELIMINAR_COMANDA + " - Eliminar una Comanda\n" +
            "   " + AFEGIR_PRODUCTE_COMANDA + " - Agregar producto a Comanda\n" +
            "   " + ELIMINAR_PRODUCTE_COMANDA + " - Eliminar producto de Comanda\n" +
            "   " + CONSULTAR_COMANDA_UNICA + " - Obtener Comanda por nombre\n" +
            "   " + CONSULTAR_COMANDES + " - Consultar todas las Comandas creadas\n" +
            "   " + GENERAR_COMANDA_AUTOMATICA + " - Generar una Comanda automática\n" +
            "   " + EXECUTAR_COMANDES + " - Ejecutar Comandas seleccionadas\n" +
            "\n" +
            "GESTIÓ DE PRESTATGERIA:\n" +
            "   " + AFEGIR_PRODUCTE_PRESTATGERIA + " - Agregar un producto a Prestatgeria\n" +
            "   " + MOURE_PRODUCTE_DE_HUECO + " - Mover producto de un hueco a otro\n" +
            "   " + FIXAR_PRODUCTE + " - Fijar un producto en un hueco\n" +
            "   " + DESFIXAR_PRODUCTE + " - Desfijar un producto de un hueco\n" +
            "   " + RETIRAR_PRODUCTE_A_MAGATZEM + " - Retirar producto a Magatzem\n" +
            "   " + DECREMENTAR_STOCK_A_PRODUCTE + " - Decrementar stock de un producto\n" +
            "   " + GENERAR_DISTRIBUCIO_BACKTRACKING + " - Generar distribución (Backtracking)\n" +
            "   " + GENERAR_DISTRIBUCIO_HILL_CLIMBING + " - Generar distribución (Hill Climbing)\n" +
            "   " + AFEGIR_PRESTATGERIA + " - Agregar una nueva Prestatgeria\n" +
            "   " + ELIMINAR_PRESTATGERIA + " - Eliminar una Prestatgeria\n" +
            "   " + AFEGIR_PRESTATGE + " - Agregar un prestatge a Prestatgeria\n" +
            "   " + ELIMINAR_PRESTATGE + " - Eliminar un prestatge de Prestatgeria\n" +
            "   " + REPOSAR_PRESTATGERIA + " - Reposar Prestatgeria\n" +
            "   " + CONSULTAR_PRESTATGERIA + " - Imprimir detalles de Prestatgeria\n" +
            "\n" +
            "GESTIÓ DE CAIXA:\n" +
            "   " + AFEGIR_PRODUCTE_CAIXA + " - Agregar producto a Caixa\n" +
            "   " + RETIRAR_PRODUCTE_CAIXA + " - Retirar producto de la Caixa\n" +
            "   " + PAGAR + " - Pagar y vaciar la Caixa\n" +
            "   " + CONSULTAR_CAIXA + " - Imprimir detalles de Caixa\n" +
            "\n" +
            "GESTIÓ DE MAGATZEM (PRODUCTES):\n" +
            "   " + ALTA_PRODUCTE + " - Dar de alta un nuevo producto\n" +
            "   " + ELIMINAR_PRODUCTE + " - Eliminar un producto\n" +
            "   " + AFEGIR_SIMILITUD + " - Agregar similitud entre productos\n" +
            "   " + ELIMINA_SIMILITUD + " - Eliminar similitud entre productos\n" +
            "   " + CONSULTAR_PRODUCTE + " - Imprimir detalles de producto\n" +
            "   " + CONSULTAR_MAGATZEM + " - Imprimir detalles de Magatzem\n" +
            "\n" +
            "EXTRAS:\n" +
            "   " + HELP + " - Mostrar comandos disponibles\n" +
            "   " + EXIT + " - Salir\n";


    /** Instancia de CtrlDomini para manejar la lógica del dominio. */
    static CtrlDomini ctrlDomini = new CtrlDomini();

    /**
     * Lee una línea de entrada desde el Scanner especificado.
     *
     * @param scanner el Scanner para leer la entrada
     * @return la línea de entrada
     */
    private static String readLine(Scanner scanner) {
        return scanner.nextLine();
    }

    // Métodos de prueba para cada funcionalidad


    //Comandes

    /**
     * Prueba la funcionalidad de crear una nueva Comanda.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testCrearComanda(Scanner scanner) {
        System.out.println("Nombre de la Comanda:");
        String nomComanda = readLine(scanner);
        try {
            ctrlDomini.crearComanda(nomComanda);
            System.out.println("Comanda " + nomComanda + " creada correctament.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de eliminar una Comanda existente.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testEliminarComanda(Scanner scanner) {
        System.out.println("Nombre de la Comanda a eliminar:");
        String nomComanda = readLine(scanner);
        try {
            ctrlDomini.eliminarComanda(nomComanda);
            System.out.println("Comanda eliminada correctament.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de agregar un producto a una Comanda existente.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testAfegirProducteComanda(Scanner scanner) {
        System.out.println("Nombre de la Comanda:");
        String nomComanda = readLine(scanner);
        System.out.println("Nombre del Producto:");
        String nomProducte = readLine(scanner);
        System.out.println("Cantidad:");
        int quantitat = 0;
        try {
            quantitat = Integer.parseInt(readLine(scanner));
        } catch (Exception e) {
            System.out.println("Error: Quantitat no pot ser buit");
            return;
        }
        try {
            ctrlDomini.afegirProducteComanda(nomComanda, nomProducte, quantitat);
            System.out.println("Producte afegit correctament a la comanda.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de eliminar un producto de una Comanda existente.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testEliminarProducteComanda(Scanner scanner) {
        System.out.println("Nombre de la Comanda:");
        String nomComanda = readLine(scanner);
        System.out.println("Nombre del Producto:");
        String nomProducte = readLine(scanner);
        System.out.println("Quantitat:");
        int quantitat = 0;
        try {
            quantitat = Integer.parseInt(readLine(scanner));
        } catch (Exception e) {
            System.out.println("Error: Quantitat no pot ser buit");
            return;
        }
        try {
            ctrlDomini.eliminarProducteComanda(nomComanda, nomProducte, quantitat);
            System.out.println("Producte eliminat correctament de la comanda.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de obtener los detalles de una Comanda específica.
     *
     * @param scanner Scanner para leer los datos de entrada.
     * @return La Comanda solicitada, o una nueva Comanda vacía si ocurre un error.
     */
    public static Comanda testGetComandaUnica(Scanner scanner) {
        System.out.println("Nombre de la Comanda:");
        String nomComanda = readLine(scanner);
        try {
            return ctrlDomini.getComandaUnica(nomComanda);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return new Comanda(null);
    }

    /**
     * Prueba la funcionalidad de obtener todas las Comandas creadas.
     *
     * @return Un mapa de todas las Comandas creadas en el sistema.
     */
    public static Map<String, Comanda> testGetComandes() {
        return ctrlDomini.getComandes();
    }

    /**
     * Prueba la funcionalidad de generar una Comanda automáticamente.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testGenerarComandaAutomatica(Scanner scanner) {
        System.out.println("Nombre de la Comanda:");
        String nom = readLine(scanner);
        try {
            ctrlDomini.generarComandaAutomatica(nom);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de ejecutar múltiples Comandas especificadas.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testExecutarComandes(Scanner scanner) {
        System.out.println("Nombres de las Comandas a ejecutar (separados por comas):");
        String[] noms = readLine(scanner).split(",");
        try {
            ctrlDomini.executarComandes(noms);
            System.out.println("Comandes " + Arrays.toString(noms) + " executades correctament!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    //Prestatgeria

    /**
     * Prueba la funcionalidad de agregar una nueva Prestatgeria.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testAfegirPrestatgeria(Scanner scanner) {
        System.out.println("ID de la nueva Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        int midaPrestatge = 0;
        int midaPrestatgeria = 0;
        try {
            System.out.println("Mida prestage:");
            midaPrestatge = Integer.parseInt(readLine(scanner));
            System.out.println("Mida prestatgeria:");
            midaPrestatgeria = Integer.parseInt(readLine(scanner));
        } catch (Exception e) {
            System.out.println("Error: Les mides no poden ser buides.");
            return;
        }
        try {
            ctrlDomini.afegirPrestatgeria(idPrestatgeria, midaPrestatge, midaPrestatgeria);
            System.out.println("Prestatgeria añadida.");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de agregar un Prestatge a una Prestatgeria existente.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testAfegirPrestatge(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        try{
            ctrlDomini.afegirPrestatge(idPrestatgeria);
            System.out.println("Prestatge añadido.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de eliminar una Prestatgeria.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testEliminarPrestatgeria(Scanner scanner) {
        System.out.println("ID de la Prestatgeria a eliminar:");
        String idPrestatgeria = readLine(scanner);
        try{
            ctrlDomini.eliminarPrestatgeria(idPrestatgeria);
            System.out.println("Prestatgeria eliminada.");
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de eliminar un Prestatge de una Prestatgeria.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testEliminarPrestatge(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        try{
            ctrlDomini.eliminarPrestatge(idPrestatgeria);
            System.out.println("Prestatge eliminado de la prestatgeria.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de agregar un producto a una Prestatgeria.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testAfegirProductePrestatgeria(Scanner scanner) {
        System.out.println("Nombre del Producto:");
        String nomProducte = readLine(scanner);
        System.out.println("Cantidad:");
        int quantitat = 0;
        try {
            quantitat = Integer.parseInt(readLine(scanner));
        } catch (Exception e) {
            System.out.println("Error: Quantitat no pot ser buit.");
            return;
        }
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        try {
            ctrlDomini.afegirProductePrestatgeria(nomProducte, quantitat, idPrestatgeria);
            System.out.println("Producto añadido a la prestatgeria correctamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de retirar un producto de una Prestatgeria al Magatzem.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testRetirarProducteAMagatzem(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        System.out.println("Nombre del Producto:");
        String nomProducte = readLine(scanner);
        try {
            ctrlDomini.retirarProducteAMagatzem(idPrestatgeria, nomProducte);
            System.out.println("Producto " + nomProducte + " de prestatgeria " + idPrestatgeria + " retirado al magatzem.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de decrementar el stock de un producto en una Prestatgeria.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testDecrementarStockAProductePrestatgeria(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        System.out.println("Nombre del Producto:");
        String nomProducte = readLine(scanner);
        int cantidad = 0;
        try {
            System.out.println("Cantidad a decrementar:");
            cantidad = Integer.parseInt(readLine(scanner));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            ctrlDomini.decrementarStockAProducte(idPrestatgeria, nomProducte, cantidad);
            System.out.println("Stock decrementado.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de mover un producto entre espacios en una Prestatgeria.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testMoureProducteDeHueco(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        System.out.println("Nombre del Producto:");
        String nomProducte = readLine(scanner);
        int huecoOrigen = 0;
        int huecoDestino = 0;
        try {
            System.out.println("Hueco de origen:");
            huecoOrigen = Integer.parseInt(readLine(scanner));
            System.out.println("Hueco de destino:");
            huecoDestino = Integer.parseInt(readLine(scanner));
        } catch (Exception e) {
            System.out.println("Error: Los huecos no pueden estar vacíos.");
            return;
        }
        try {
            ctrlDomini.moureProducteDeHueco(idPrestatgeria, nomProducte, huecoOrigen, huecoDestino);
            System.out.println("Producto movido de hueco.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de fijar un producto en un espacio de una Prestatgeria.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testFixarProducte(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        System.out.println("Nombre del Producto:");
        String nomProducte = readLine(scanner);
        try {
            ctrlDomini.fixarProducte(idPrestatgeria, nomProducte);
            System.out.println("Producto fijado en la prestatgeria.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de desfijar un producto en un espacio de una Prestatgeria.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testDesfixarProducte(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        System.out.println("Nombre del Producto:");
        String nomProducte = readLine(scanner);
        try {
            ctrlDomini.desfixarProducte(idPrestatgeria, nomProducte);
            System.out.println("Producto desfijado de la prestatgeria.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de generar una distribución utilizando Backtracking.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testGenerarDistribucioBacktracking(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        try {
            ctrlDomini.generarDistribucioBacktracking(idPrestatgeria);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de generar una distribución utilizando Hill Climbing.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testGenerarDistribucioHillClimbing(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        try{
            ctrlDomini.generarDistribucioHillClimbing(idPrestatgeria);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de reponer una Prestatgeria.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testReposarPrestatgeria(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        try {
            ctrlDomini.reposarPrestatgeria(idPrestatgeria);
            System.out.println("Prestatgeria repuesta.");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de obtener los detalles de una Prestatgeria específica.
     *
     * @param scanner Scanner para leer los datos de entrada.
     * @return La Prestatgeria solicitada.
     */
    public static Prestatgeria testGetPrestatgeria(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        try{
            return ctrlDomini.getPrestatgeria(idPrestatgeria);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }


    //Caixa

    /**
     * Prueba la funcionalidad de agregar un producto a la Caixa desde una Prestatgeria.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testAfegirProducteCaixa(Scanner scanner) {
        System.out.println("Nombre del Producto:");
        String nomProducte = readLine(scanner);
        int quantitat = 0;
        try {
            System.out.println("Cantidad:");
            quantitat = Integer.parseInt(readLine(scanner));
        }
        catch(Exception e) {
            System.out.println("Error: La quantitat no pot estar buida.");
            return;
        }
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        try {
            int quantitat_afegida = ctrlDomini.afegir_producte_caixa(nomProducte, quantitat, idPrestatgeria);
            System.out.println(quantitat_afegida + " unidades del producto " + nomProducte + " de la prestatgeria " + idPrestatgeria + " añadidos a la Caja!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de retirar un producto de la Caixa y devolverlo a la Prestatgeria.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testRetirarProducteCaixa(Scanner scanner) {
        System.out.println("Nombre del Producto:");
        String nomProducte = readLine(scanner);
        int quantitat = 0;
        try {
            System.out.println("Cantidad:");
            quantitat = Integer.parseInt(readLine(scanner));
        } catch (Exception e) {
            System.out.println("Error: La cantidad no puede estar vacía.");
            return;
        }
        try {
            int retirat = ctrlDomini.retirar_producte_caixa(nomProducte, quantitat);
            System.out.println(retirat + " unidades del producto " + nomProducte + " retirados de la Caja!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de pagar todos los productos en la Caixa y vaciarla.
     */
    public static void testPagar() {
        ctrlDomini.pagar_caixa();
        System.out.println("Caja pagada y vaciada!");
    }

    /**
     * Prueba la funcionalidad de obtener el ticket actual de la Caixa.
     *
     * @return Un mapa con los detalles del ticket, donde las claves son los nombres de los productos
     *         y los valores son las cantidades correspondientes.
     */
    public static Map<String, Integer> testGetCaixa() {
        return ctrlDomini.getTicket();
    }


    //Magatzem(Productes)

    /**
     * Prueba la funcionalidad de dar de alta un nuevo producto en el sistema.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testAltaProducte(Scanner scanner) {
        System.out.println("Nombre del Producto:");
        String nom = readLine(scanner);

        System.out.println("Stock Máximo posible en Prestatgeria:");
        int max_h = 0;
        try {
            max_h = Integer.parseInt(readLine(scanner));
        } catch (Exception e) {
            System.out.println("Error: El stock máximo en prestatgeria no puede estar vacío.");
            return;
        }

        System.out.println("Stock Máximo posible en Magatzem:");
        int max_m = 0;
        try {
            max_m = Integer.parseInt(readLine(scanner));
        } catch (Exception e) {
            System.out.println("Error: El stock máximo en magatzem no puede estar vacío.");
            return;
        }

        int stock = 0;
        System.out.println("Cantidad inicial de stock:");
        try {
            stock = Integer.parseInt(readLine(scanner));
        } catch (Exception e) {
            System.out.println("Error: El stock inicial no puede estar vacío.");
            return;
        }

        try {
            ctrlDomini.altaProducte(nom, max_h, max_m, stock);
            System.out.println("Producto " + nom + " dado de alta correctamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de eliminar un producto del sistema.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testEliminarProducte(Scanner scanner) {
        System.out.println("Nombre del Producto:");
        String nom = readLine(scanner);
        try {
            ctrlDomini.eliminarProducte(nom);
            System.out.println("Producto " + nom + " eliminado correctamente.");
        } catch (Exception e){
                System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de agregar similitud entre dos productos.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testAfegirSimilitud(Scanner scanner) {
        System.out.println("Nombre del primer Producto:");
        String nom1 = readLine(scanner);
        System.out.println("Nombre del segundo Producto:");
        String nom2 = readLine(scanner);
        System.out.println("Valor de similitud:");
        float value = 0.0F;
        boolean vacio = false;
        try {
            value = Float.parseFloat(readLine(scanner));
        } catch (Exception e) {
            System.out.println("Error: La similitud no puede estar vacía.");
            vacio = true;
            return;
        }
        try {
            ctrlDomini.afegir_similitud(nom1, nom2, value);
            System.out.println("Similitud agregada correctamente.");
        } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de eliminar similitud entre dos productos.
     *
     * @param scanner Scanner para leer los datos de entrada.
     */
    public static void testEliminarSimilitud(Scanner scanner) {
        System.out.println("Nombre del primer Producto:");
        String nom1 = readLine(scanner);
        System.out.println("Nombre del segundo Producto:");
        String nom2 = readLine(scanner);
        try {
            ctrlDomini.eliminarSimilitud(nom1, nom2);
            System.out.println("Similitud entre " + nom1 + " y " + nom2 + " ahora es 0.");
        } catch (Exception e) {
            System.out.println("Error: " +e.getMessage());
        }
    }

    /**
     * Prueba la funcionalidad de obtener los detalles de un producto.
     *
     * @param scanner Scanner para leer los datos de entrada.
     * @return El Producto solicitado o null si ocurre un error.
     */
    public static Producte testGetProducte(Scanner scanner) {
        System.out.println("Nombre del Producto:");
        String nom = readLine(scanner);
        try {
            return ctrlDomini.get_producte(nom);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Prueba la funcionalidad de obtener todos los productos del Magatzem.
     *
     * @return Un mapa con todos los productos del Magatzem.
     */
    public static Map<String, Producte> testGetMagatzem() {
        return ctrlDomini.getMagatzem();
    }


    /**
     * Ejecuta un comando específico basado en la entrada del usuario.
     *
     * @param command el comando a ejecutar
     * @param scanner el Scanner para leer la entrada
     * @return {@code true} si el programa debe continuar ejecutándose; {@code false} en caso contrario
     */
    public static boolean commands(String command, Scanner scanner) {
        switch (command) {

            // Comanda
            case CREAR_COMANDA:
                testCrearComanda(scanner);
                break;
            case ELIMINAR_COMANDA:
                testEliminarComanda(scanner);
                break;
            case AFEGIR_PRODUCTE_COMANDA:
                testAfegirProducteComanda(scanner);
                break;
            case ELIMINAR_PRODUCTE_COMANDA:
                testEliminarProducteComanda(scanner);
                break;
            case CONSULTAR_COMANDA_UNICA:
                Comanda comanda = testGetComandaUnica(scanner);
                break;
            case CONSULTAR_COMANDES:
                Map<String, Comanda> comandes = testGetComandes();
                break;
            case GENERAR_COMANDA_AUTOMATICA:
                testGenerarComandaAutomatica(scanner);
                break;
            case EXECUTAR_COMANDES:
                testExecutarComandes(scanner);
                break;

            // Prestatgeria
            case AFEGIR_PRODUCTE_PRESTATGERIA:
                testAfegirProductePrestatgeria(scanner);
                break;
            case MOURE_PRODUCTE_DE_HUECO:
                testMoureProducteDeHueco(scanner);
                break;
            case FIXAR_PRODUCTE:
                testFixarProducte(scanner);
                break;
            case DESFIXAR_PRODUCTE:
                testDesfixarProducte(scanner);
                break;
            case RETIRAR_PRODUCTE_A_MAGATZEM:
                testRetirarProducteAMagatzem(scanner);
                break;
            case DECREMENTAR_STOCK_A_PRODUCTE:
                testDecrementarStockAProductePrestatgeria(scanner);
                break;
            case GENERAR_DISTRIBUCIO_BACKTRACKING:
                testGenerarDistribucioBacktracking(scanner);
                break;
            case GENERAR_DISTRIBUCIO_HILL_CLIMBING:
                testGenerarDistribucioHillClimbing(scanner);
                break;
            case AFEGIR_PRESTATGERIA:
                testAfegirPrestatgeria(scanner);
                break;
            case ELIMINAR_PRESTATGERIA:
                testEliminarPrestatgeria(scanner);
                break;
            case AFEGIR_PRESTATGE:
                testAfegirPrestatge(scanner);
                break;
            case ELIMINAR_PRESTATGE:
                testEliminarPrestatge(scanner);
                break;
            case REPOSAR_PRESTATGERIA:
                testReposarPrestatgeria(scanner);
                break;
            case CONSULTAR_PRESTATGERIA:
                Prestatgeria prestatgeria = testGetPrestatgeria(scanner);
                break;

            // Caixa
            case AFEGIR_PRODUCTE_CAIXA:
                testAfegirProducteCaixa(scanner);
                break;
            case RETIRAR_PRODUCTE_CAIXA:
                testRetirarProducteCaixa(scanner);
                break;
            case PAGAR:
                testPagar();
                break;
            case CONSULTAR_CAIXA:
                Map<String,Integer> caixa = testGetCaixa();
                break;

            // Magatzem (Producte)
            case ALTA_PRODUCTE:
                testAltaProducte(scanner);
                break;
            case ELIMINAR_PRODUCTE:
                testEliminarProducte(scanner);
                break;
            case AFEGIR_SIMILITUD:
                testAfegirSimilitud(scanner);
                break;
            case ELIMINA_SIMILITUD:
                testEliminarSimilitud(scanner);
                break;
            case CONSULTAR_PRODUCTE:
                Producte producte=testGetProducte(scanner);
                break;
            case CONSULTAR_MAGATZEM:
                Map<String, Producte> magatzem=testGetMagatzem();
                break;

            // Extras
            case HELP:
                System.out.println(HELPTEXT);
                break;
            case EXIT:
                System.out.println("Saliendo del programa...");
                return false;

            default:
                break;
        }
        System.out.println();
        return true;
    }


    /**
     * Método principal. Inicia el programa en modo interactivo o en modo archivo.
     *
     * @param args archivo opcional para leer comandos
     */
    public static void main(String[] args) {
        boolean run = true;

        // Check for file input
        if (args.length != 0) {
            try {
                File file = new File(args[0]);
                Scanner myReader = new Scanner(file);
                while (myReader.hasNextLine() && run)
                    run = commands(myReader.nextLine(), myReader);
                myReader.close();
            } catch (Exception e) {
                System.out.println("ERROR: Arxiu no admès");
            }
        }

        // Interactive mode
        if (run) System.out.println(HELPTEXT);
        Scanner in = new Scanner(System.in);
        while (run)
            run = commands(in.nextLine(), in);

        System.out.println("Gràcies per utilitzar el programa DriverCtrlDomini!");
    }

}