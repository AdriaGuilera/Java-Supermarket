package drivers;

import controladors.CtrlDomini;
import controladors.*;
import java.io.File;
import java.util.Scanner;

public class DriverCtrlDomini {

    // Comandos para el menú
    //Comandes
    private static final String CONSTRUCTOR = "0";
    private static final String CREAR_COMANDA = "1";
    private static final String ELIMINAR_COMANDA = "2";
    private static final String AFEGIR_PRODUCTE_COMANDA = "3";
    private static final String OBTENIR_COMANDES = "4";
    private static final String CONSULTAR_COMANDES = "50";

    //Prestatgeria
    private static final String MOURE_PRESTATGERIA = "5";

    //Caixa
    private static final String AFEGIR_PRODUCTE_CAIXA = "6";
    private static final String CREAR_CAIXA = "51";
    private static final String RETIRAR_PRODUCTE = "53";
    private static final String GET_QUANTITAT = "54";
    private static final String PRINTTICKET = "55";
    private static final String PRINTTICKETPERPRESTATGERIES = "56";
    public static final String  PAGAR = "57";

    //Magatzem (Producte)
    private static final String GENERAR_COMANDA_AUTOMATICA = "7";
    private static final String EXECUTAR_COMANDES = "8";
    private static final String MODIFICAR_PRODUCTE = "9";
    private static final String ELIMINAR_PRODUCTE = "10";
    private static final String ALTA_PRODUCTE = "11";
    private static final String AFEGIR_SIMILITUD = "12";
    private static final String IMPRIMIR_PRODUCTE = "13";
    private static final String IMPRIMIR_MAGATZEM = "13";
    private static final String HELP = "help";
    private static final String EXIT = "exit";




    private static final String HELPTEXT = "Seleccione un comando (con el número):\n" +
            "   " + CONSTRUCTOR + " - Inicializar CtrlDomini\n" +
            "GESTIÓ DE COMANDES:   \n"+
            "   " + CREAR_COMANDA + " - Crear una Comanda\n" +
            "   " + ELIMINAR_COMANDA + " - Eliminar una Comanda\n" +
            "   " + AFEGIR_PRODUCTE_COMANDA + " - Agregar producto a Comanda\n" +
            "   " + OBTENIR_COMANDES + " - Obtener Comandas por nombre\n" +
            "   " + CONSULTAR_COMANDES + " - Consultar todas las Comandas creadas\n" +
            "  \n"+
            "GESTIÓ DE PRESTATGERIA:  \n"+
            "   " + MOURE_PRESTATGERIA + " - Mover producto a Prestatgeria\n" +
            "  \n"+
            "GESTIÓ DE CAIXA:  \n"+
            "   " + AFEGIR_PRODUCTE_CAIXA + " - Agregar producto a Caixa\n" +
            "   " + CREAR_CAIXA + " - Crear una nova caixa\n" +
            "   " + RETIRAR_PRODUCTE + " - Retirar un producte de la caixa\n" +
            "   " + GET_QUANTITAT + " - Obtenir la quantitat d'un producte de la caixa\n" +
            "   " + PRINTTICKET + " - Imprimir el tiquet total\n" +
            "   " + PRINTTICKETPERPRESTATGERIES + " - Imprimir el tiquet per productes aprestatgeries\n" +
            "   " + PAGAR + " - Pagar i buidar la caixa\n" +
            "  \n"+
            "GESTIÓ DE MAGATZEM(PRODUCTES):  \n"+
            "   " + GENERAR_COMANDA_AUTOMATICA + " - Generar Comanda Automática\n" +
            "   " + EXECUTAR_COMANDES + " - Ejecutar Comandas\n" +
            "   " + MODIFICAR_PRODUCTE + " - Modificar Producto\n" +
            "   " + ELIMINAR_PRODUCTE + " - Eliminar Producto\n" +
            "   " + ALTA_PRODUCTE + " - Dar de Alta Producto\n" +
            "   " + AFEGIR_SIMILITUD + " - Agregar Similitud entre Productos\n" +
            "   " + IMPRIMIR_PRODUCTE + " - Imprimir detalles de Producto\n" +
            "   " + IMPRIMIR_MAGATZEM + " - Imprimir detalles de Producto\n" +
            "  \n"+
            "EXTRAS:  \n"+
            "   " + HELP + " - Mostrar comandos disponibles\n" +
            "   " + EXIT + " - Salir\n";

    static CtrlDomini ctrlDomini = new CtrlDomini();

    private static String readLine(Scanner scanner) {
        return scanner.nextLine();
    }

    // Métodos de prueba para cada funcionalidad



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

    public static void testObtenirComandes(Scanner scanner) {
        System.out.println("Nombres de las Comandas (separados por comas):");
        String[] nomsComandes = readLine(scanner).split(",");
        ctrlDomini.obtenirComandes(nomsComandes).forEach((nom, comanda) ->
                System.out.println("Comanda: " + nom + ", Productos: " + comanda.getOrdres()));
    }

    public static void testConsultarComandes() {
        ctrlDomini.consultarComandes();
    }

    public static void testMourePrestatgeria(Scanner scanner) {
        System.out.println("Nombre del Producto:");
        String nom = readLine(scanner);
        System.out.println("Cantidad:");
        int quantitat = Integer.parseInt(readLine(scanner));
        System.out.println("ID de la Prestatgeria:");
        String idPrest = readLine(scanner);
        ctrlDomini.mourePrestatgeria(nom, quantitat, idPrest);
    }

    public static void testAfegirProducteCaixa(Scanner scanner) {
        System.out.println("Nombre del Producto:");
        String nomProducte = readLine(scanner);
        System.out.println("Cantidad:");
        int quantitat = Integer.parseInt(readLine(scanner));
        System.out.println("ID de la Prestatgeria:");
        String idPrestatgeria = readLine(scanner);
        ctrlDomini.afegir_producte_caixa(nomProducte, quantitat, idPrestatgeria);
    }

    public static void testGenerarComandaAutomatica() {
        ctrlDomini.generarComandaAutomatica();
    }

    public static void testExecutarComandes(Scanner scanner) {
        System.out.println("Nombres de las Comandas a ejecutar (separados por comas):");
        String[] noms = readLine(scanner).split(",");
        ctrlDomini.executarComandes(noms);
    }

    public static void testModificarProducte(Scanner scanner) {
        System.out.println("Nombre del Producto:");
        String nom = readLine(scanner);
        System.out.println("Nuevo nombre:");
        String nouNom = readLine(scanner);
        System.out.println("Maximo stock posible en Estanteria:");
        int mh = Integer.parseInt(readLine(scanner));

        System.out.println("Maximo estoc posible en Magatzem:");
        int mm = Integer.parseInt(readLine(scanner));
        ctrlDomini.modificarProducte(nom, nouNom, mh, mm);
    }

    public static void testEliminarProducte(Scanner scanner) {
        System.out.println("Nombre del Producto:");
        String nom = readLine(scanner);
        ctrlDomini.eliminar_producte(nom);
    }

    public static void testAltaProducte(Scanner scanner) {
        System.out.println("Nombre del Producto:");
        String nom = readLine(scanner);
        System.out.println("Stock Máximo posible en Prestatgeria:");
        int mh = Integer.parseInt(readLine(scanner));
        System.out.println("Stock Máximo posible en Magatzem:");
        int mm = Integer.parseInt(readLine(scanner));
        ctrlDomini.altaProducte(nom, mh, mm);
        System.out.println("Producto añadido al Almacén!");
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

    public static void testImprimirProducte(Scanner scanner) {
        System.out.println("Nombre del Producto:");
        String nom = readLine(scanner);
        ctrlDomini.imprimir_producte(nom);
    }



    // Procesador de comandos
    public static boolean commands(String command, Scanner scanner) {
        switch (command) {

            case CREAR_COMANDA:
                testCrearComanda(scanner);
                break;
            case ELIMINAR_COMANDA:
                testEliminarComanda(scanner);
                break;
            case AFEGIR_PRODUCTE_COMANDA:
                testAfegirProducteComanda(scanner);
                break;
            case OBTENIR_COMANDES:
                testObtenirComandes(scanner);
                break;
            case CONSULTAR_COMANDES:
                testConsultarComandes();
                break;
            case MOURE_PRESTATGERIA:
                testMourePrestatgeria(scanner);
                break;
            case AFEGIR_PRODUCTE_CAIXA:
                testAfegirProducteCaixa(scanner);
                break;
            case GENERAR_COMANDA_AUTOMATICA:
                testGenerarComandaAutomatica();
                break;
            case EXECUTAR_COMANDES:
                testExecutarComandes(scanner);
                break;
            case MODIFICAR_PRODUCTE:
                testModificarProducte(scanner);
                break;
            case ELIMINAR_PRODUCTE:
                testEliminarProducte(scanner);
                break;
            case ALTA_PRODUCTE:
                testAltaProducte(scanner);
                break;
            case AFEGIR_SIMILITUD:
                testAfegirSimilitud(scanner);
                break;
            case IMPRIMIR_PRODUCTE:
                testImprimirProducte(scanner);
                break;
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