package drivers;

import controladors.CtrlDomini;

import java.io.File;
import java.util.Scanner;

public class DriverCtrlDomini {

    // Comandos para el menú
    // Comanda
    private static final String CREAR_COMANDA = "1";
    private static final String ELIMINAR_COMANDA = "2";
    private static final String AFEGIR_PRODUCTE_COMANDA = "3";
    private static final String ELIMINAR_PRODUCTE_COMANDA = "4";
    private static final String PRINT_COMANDA_UNICA = "5";
    private static final String PRINT_COMANDES = "6";


    // Prestatgeria
    private static final String AFEGIR_PRODUCTE_PRESTATGERIA = "7";
    private static final String MOURE_PRODUCTE_DE_HUECO = "8";
    private static final String FIXAR_PRODUCTE = "9";
    private static final String DESFIXAR_PRODUCTE = "10";
    private static final String RETIRAR_PRODUCTE_A_MAGATZEM = "11";
    private static final String DECREMENTAR_STOCK_A_PRODUCTE = "12";
    private static final String GENERAR_DISTRIBUCIO_BACKTRACKING = "15";
    private static final String GENERAR_DISTRIBUCIO_HILL_CLIMBING = "16";
    private static final String AFEGIR_PRESTATGERIA = "17";
    private static final String ELIMINAR_PRESTATGERIA = "18";
    private static final String AFEGIR_PRESTATGE = "19";
    private static final String ELIMINAR_PRESTATGE = "20";
    private static final String REPOSAR_PRESTATGERIA = "21";
    private static final String PRINT_PRESTATGERIA = "22";

    // Caixa
    private static final String AFEGIR_PRODUCTE_CAIXA = "23";
    private static final String RETIRAR_PRODUCTE_CAIXA = "24";
    private static final String PRINTTICKET = "25";
    private static final String PRINTTICKETPERPRESTATGERIES = "26";
    private static final String PAGAR = "27";

    // Magatzem (Producte)
    private static final String EXECUTAR_COMANDES = "28";
    private static final String OBTENIR_COMANDA_AUTOMATICA = "29";
    private static final String ALTA_PRODUCTE = "30";
    private static final String ELIMINAR_PRODUCTE = "31";
    private static final String AFEGIR_SIMILITUD = "32";
    private static final String ELIMINA_SIMILITUD = "33";
    private static final String MODIFICAR_SIMILITUD = "34";
    private static final String MOURE_PRODUCTE_A_PRESTATGERIA = "35";
    private static final String PRINT_PRODUCTE = "36";
    private static final String PRINT_MAGATZEM = "37";


    // Extras
    private static final String HELP = "help";
    private static final String EXIT = "exit";


    private static final String HELPTEXT = "Seleccione un comando (con el número):\n" +
            "GESTIÓ DE COMANDES:   \n"+
            "   " + CREAR_COMANDA + " - Crear una Comanda\n" +
            "   " + ELIMINAR_COMANDA + " - Eliminar una Comanda\n" +
            "   " + AFEGIR_PRODUCTE_COMANDA + " - Agregar producto a Comanda\n" +
            "   " + ELIMINAR_PRODUCTE_COMANDA + " - Eliminar producto de Comanda\n" +
            "   " + PRINT_COMANDA_UNICA + " - Obtener Comanda por nombre\n" +
            "   " + PRINT_COMANDES + " - Consultar todas las Comandas creadas\n" +
            "  \n"+
            "GESTIÓ DE PRESTATGERIA:  \n"+
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
            "   " + PRINT_PRESTATGERIA + " - Imprimir detalles de Prestatgeria\n" +
            "  \n"+
            "GESTIÓ DE CAIXA:  \n"+
            "   " + AFEGIR_PRODUCTE_CAIXA + " - Agregar producto a Caixa\n" +
            "   " + RETIRAR_PRODUCTE_CAIXA + " - Retirar producto de la Caixa\n" +
            "   " + PRINTTICKET + " - Imprimir el ticket total\n" +
            "   " + PRINTTICKETPERPRESTATGERIES + " - Imprimir ticket por Prestatgeries\n" +
            "   " + PAGAR + " - Pagar y vaciar la Caixa\n" +
            "  \n"+
            "GESTIÓ DE MAGATZEM (PRODUCTES):  \n"+
            "   " + EXECUTAR_COMANDES + " - Ejecutar Comandas seleccionadas\n" +
            "   " + OBTENIR_COMANDA_AUTOMATICA + " - Obtener Comanda automática con los productos que faltan en el Almacen\n" +
            "   " + ALTA_PRODUCTE + " - Dar de alta un nuevo producto\n" +
            "   " + ELIMINAR_PRODUCTE + " - Eliminar un producto\n" +
            "   " + AFEGIR_SIMILITUD + " - Agregar similitud entre productos\n" +
            "   " + ELIMINA_SIMILITUD + " - Eliminar similitud entre productos\n" +
            "   " + MODIFICAR_SIMILITUD + " - Modificar similitud entre productos\n" +
            "   " + MOURE_PRODUCTE_A_PRESTATGERIA + " - Mover producto a Prestatgeria\n" +
            "   " + PRINT_PRODUCTE + " - Imprimir detalles de producto\n" +
            "   " + PRINT_MAGATZEM + " - Imprimir detalles de Magatzem\n" +

            "  \n"+
            "EXTRAS:  \n"+
            "   " + HELP + " - Mostrar comandos disponibles\n" +
            "   " + EXIT + " - Salir\n";

    static CtrlDomini ctrlDomini = new CtrlDomini();

    private static String readLine(Scanner scanner) {
        return scanner.nextLine();
    }

    // Métodos de prueba para cada funcionalidad


    //Comandes
    public static void testCrearComanda(Scanner scanner) {
        System.out.println("Nombre de la Comanda:");
        String nomComanda = readLine(scanner);
        ctrlDomini.crearComanda(nomComanda);
    }

    public static void testEliminarComanda(Scanner scanner) {
        System.out.println("Nombre de la Comanda a eliminar:");
        String nomComanda = readLine(scanner);
        ctrlDomini.eliminarComanda(nomComanda);
    }

    public static void testAfegirProducteComanda(Scanner scanner) {
        System.out.println("Nombre de la Comanda:");
        String nomComanda = readLine(scanner);
        System.out.println("Nombre del Producto:");
        String nomProducte = readLine(scanner);
        System.out.println("Cantidad:");
        int quantitat = Integer.parseInt(readLine(scanner));
        ctrlDomini.afegirProducteComanda(nomComanda, nomProducte, quantitat);
    }
    public static void testEliminarProducteComanda(Scanner scanner) {
        System.out.println("Nombre de la Comanda:");
        String nomComanda = readLine(scanner);
        System.out.println("Nombre del Producto:");
        String nomProducte = readLine(scanner);
        ctrlDomini.eliminarProducteComanda(nomComanda,nomProducte);


    }

    public static void testPrintComandes(Scanner scanner) {
        ctrlDomini.printComandes();
    }


    public static void testPrintComandaUnica(Scanner scanner) {
        System.out.println("Nombre de la Comanda:");
        String nomComanda = readLine(scanner);
        ctrlDomini.printComandaUnica(nomComanda);
    }





    //Prestatgeria
// Métodos de Prestatgeria

    public static void testAfegirProductePrestatgeria(Scanner scanner) {
        System.out.println("Nombre del Producto:");
        String nomProducte = readLine(scanner);
        System.out.println("Cantidad:");
        int quantitat = Integer.parseInt(readLine(scanner));
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        ctrlDomini.afegirProductePrestatgeria(nomProducte, quantitat, idPrestatgeria);
        System.out.println("Producto añadido a la prestatgeria.");
    }

    public static void testMoureProducteDeHueco(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        System.out.println("Nombre del Producto:");
        String nomProducte = readLine(scanner);
        System.out.println("Hueco de origen:");
        int huecoOrigen = Integer.parseInt(readLine(scanner));
        System.out.println("Hueco de destino:");
        int huecoDestino = Integer.parseInt(readLine(scanner));
        ctrlDomini.moureProducteDeHueco(idPrestatgeria, nomProducte, huecoOrigen, huecoDestino);
        System.out.println("Producto movido de hueco.");
    }

    public static void testFixarProducte(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        System.out.println("Nombre del Producto:");
        String nomProducte = readLine(scanner);
        ctrlDomini.fixarProducte(idPrestatgeria, nomProducte);
        System.out.println("Producto fijado en la prestatgeria.");
    }

    public static void testDesfixarProducte(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        System.out.println("Nombre del Producto:");
        String nomProducte = readLine(scanner);
        ctrlDomini.desfixarProducte(idPrestatgeria, nomProducte);
        System.out.println("Producto desfijado de la prestatgeria.");
    }

    public static void testRetirarProducteAMagatzem(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        System.out.println("Nombre del Producto:");
        String nomProducte = readLine(scanner);
        ctrlDomini.retirarProducteAMagatzem(idPrestatgeria, nomProducte);
        System.out.println("Producto retirado al magatzem.");
    }

    public static void testDecrementarStockAProducte(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        System.out.println("Nombre del Producto:");
        String nomProducte = readLine(scanner);
        System.out.println("Cantidad a decrementar:");
        int cantidad = Integer.parseInt(readLine(scanner));
        ctrlDomini.decrementarStockAProducte(idPrestatgeria, nomProducte, cantidad);
        System.out.println("Stock decrementado.");
    }

    public static void testGenerarDistribucioBacktracking(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        ctrlDomini.generarDistribucioBacktracking(idPrestatgeria);
        System.out.println("Distribución generada (Backtracking) en prestatgeria.");
    }

    public static void testGenerarDistribucioHillClimbing(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        ctrlDomini.generarDistribucioHillClimbing(idPrestatgeria);
        System.out.println("Distribución generada (Hill Climbing) en prestatgeria.");
    }

    public static void testAfegirPrestatgeria(Scanner scanner) {
        System.out.println("ID de la nueva Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        System.out.println("Mida prestage:");
        int midaPrestatge = Integer.parseInt(readLine(scanner));
        System.out.println("Mida prestatgeria");
        int midaPrestatgeria = Integer.parseInt(readLine(scanner));
        ctrlDomini.afegirPrestatgeria(idPrestatgeria, midaPrestatge,midaPrestatgeria);
        System.out.println("Prestatgeria añadida.");
    }

    public static void testEliminarPrestatgeria(Scanner scanner) {
        System.out.println("ID de la Prestatgeria a eliminar:");
        String idPrestatgeria = readLine(scanner);
        ctrlDomini.eliminarPrestatgeria(idPrestatgeria);
        System.out.println("Prestatgeria eliminada.");
    }

    public static void testAfegirPrestatge(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        ctrlDomini.afegirPrestatge(idPrestatgeria);

    }

    public static void testEliminarPrestatge(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);

        ctrlDomini.eliminarPrestatge(idPrestatgeria);
        System.out.println("Prestatge eliminado de la prestatgeria.");
    }

    public static void testReposarPrestatgeria(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        ctrlDomini.reposarPrestatgeria(idPrestatgeria);
        System.out.println("Prestatgeria repuesta.");
    }

    public static void testPrintPrestatgeria(Scanner scanner) {
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        ctrlDomini.printPrestatgeria(idPrestatgeria);
    }





    //Caixa
    public static void testAfegirProducteCaixa(Scanner scanner) {
        System.out.println("Nombre del Producto:");
        String nomProducte = readLine(scanner);
        System.out.println("Cantidad:");
        int quantitat = Integer.parseInt(readLine(scanner));
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        ctrlDomini.afegir_producte_caixa(nomProducte, quantitat, idPrestatgeria);
        System.out.println(quantitat + "unidades del producto " + nomProducte + " de la prestatgeria " + idPrestatgeria +  " añadidos a la Caja!");
    }
    public static void testRetirarProducteCaixa(Scanner scanner) {
        System.out.println("Nombre del Producto:");
        String nomProducte = readLine(scanner);
        System.out.println("Cantidad:");
        int quantitat = Integer.parseInt(readLine(scanner));
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        ctrlDomini.retirar_producte_caixa(nomProducte, quantitat, idPrestatgeria);
        System.out.println(quantitat + "unidades del producto " + nomProducte + " de la prestatgeria " + idPrestatgeria +  " retirados de la Caja!");
    }

    public static void testPrintTicket() {
        ctrlDomini.imprimir_ticket_caixa();
    }

    public static void testPrintTicketPerPrestatgeries(){
        ctrlDomini.imprimir_ticket_per_prestatgeries();
    }

    public static void testPagar() {
        ctrlDomini.pagar_caixa();
        System.out.println("Caja pagada y vaciada!");
    }

    //Magatzem(Productes)

    public static void testExecutarComandes(Scanner scanner) {
        System.out.println("Nombres de las Comandas a ejecutar (separados por comas):");
        String[] noms = readLine(scanner).split(",");
        ctrlDomini.executarComandes(noms);
    }
    public static void testObtenirComandaAutomatica(Scanner scanner) {
        System.out.println("Nombre de la Comanda:");
        String nom = readLine(scanner);
        ctrlDomini.obtenirComandaAutomatica(nom);
    }
    public static void testAltaProducte(Scanner scanner) {
        System.out.println("Nombre del Producto:");
        String nom = readLine(scanner);
        System.out.println("Stock Máximo posible en Prestatgeria:");
        int mh = Integer.parseInt(readLine(scanner));
        System.out.println("Stock Máximo posible en Magatzem:");
        int mm = Integer.parseInt(readLine(scanner));
        ctrlDomini.altaProducte(nom, mh, mm);

    }
    public static void testEliminarProducte(Scanner scanner) {
        System.out.println("Nombre del Producto:");
        String nom = readLine(scanner);
        ctrlDomini.eliminar_producte(nom);
    }



    public static void testAfegirSimilitud(Scanner scanner) {
        System.out.println("Nombre del primer Producto:");
        String nom1 = readLine(scanner);
        System.out.println("Nombre del segundo Producto:");
        String nom2 = readLine(scanner);
        System.out.println("Valor de similitud:");
        float value = Float.parseFloat(readLine(scanner));
        ctrlDomini.afegir_similitud(nom1, nom2, value);
    }


    public static void testEliminarSimilitud(Scanner scanner) {
        System.out.println("Nombre del primer Producto:");
        String nom1 = readLine(scanner);
        System.out.println("Nombre del segundo Producto:");
        String nom2 = readLine(scanner);
        ctrlDomini.eliminarSimilitud(nom1,nom2);
    }


    public static void testModificarSimilitud(Scanner scanner) {
        System.out.println("Nombre del primer Producto:");
        String nom1 = readLine(scanner);
        System.out.println("Nombre del segundo Producto:");
        String nom2 = readLine(scanner);
        System.out.println("Valor de similitud:");
        float value = Float.parseFloat(readLine(scanner));
        ctrlDomini.modificarSimilitud(nom1, nom2, value);

    }

    public static void testMoureProducteAPrestatgeria(Scanner scanner) {}


    public static void testPrintProducte(Scanner scanner) {
        System.out.println("Nombre del Producto:");
        String nom = readLine(scanner);
        ctrlDomini.print_producte(nom);
    }
    public static void testPrintMagatzem() {
        ctrlDomini.printMagatzem();
    }


    // Procesador de comandos
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
            case PRINT_COMANDA_UNICA:
                testPrintComandaUnica(scanner);
                break;
            case PRINT_COMANDES:
                testPrintComandes(scanner);
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
                testDecrementarStockAProducte(scanner);
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
            case PRINT_PRESTATGERIA:
                testPrintPrestatgeria(scanner);
                break;

            // Caixa
            case AFEGIR_PRODUCTE_CAIXA:
                testAfegirProducteCaixa(scanner);
                break;
            case RETIRAR_PRODUCTE_CAIXA:
                testRetirarProducteCaixa(scanner);
                break;
            case PRINTTICKET:
                testPrintTicket();
                break;
            case PRINTTICKETPERPRESTATGERIES:
                testPrintTicketPerPrestatgeries();
                break;
            case PAGAR:
                testPagar();
                break;

            // Magatzem (Producte)
            case EXECUTAR_COMANDES:
                testExecutarComandes(scanner);
                break;
            case OBTENIR_COMANDA_AUTOMATICA:
                testObtenirComandaAutomatica(scanner);
                break;
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
            case MODIFICAR_SIMILITUD:
                testModificarSimilitud(scanner);
                break;
            case PRINT_PRODUCTE:
                testPrintProducte(scanner);
                break;
            case PRINT_MAGATZEM:
                testPrintMagatzem();
                break;
            case MOURE_PRODUCTE_A_PRESTATGERIA:
                testMoureProducteAPrestatgeria(scanner);
                break;

            // Extras
            case HELP:
                System.out.println(HELPTEXT);
                break;
            case EXIT:
                System.out.println("Saliendo del programa...");
                return false;

            default:
                System.out.println("Comando no reconocido. Escriba 'help' para ver los comandos disponibles.");
                break;
        }
        return true;
    }


    // Main method
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