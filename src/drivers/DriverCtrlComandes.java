package drivers;

import controladors.CtrlComandes;
import classes.Comanda;
import Exepcions.ComandaNotFoundException;
import Exepcions.QuanitatInvalidException;
import Exepcions.ProductNotFoundComandaException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DriverCtrlComandes {
    //Variables de uso
    private static Map<String, Integer> productosFaltantes = new HashMap<>() {{
        put("Producto1", 10);
        put("Producto2", 5);
        put("Producto3", 20);
        put("Producto4", 15);
        put("Producto5", 8);
    }};
    // Command constants
    private static final String CONSTRUCTORA = "0";
    private static final String CREAR_COMANDA = "1";
    private static final String CREAR_COMANDA_AUTOMATICA = "2";
    private static final String ELIMINAR_COMANDA = "3";
    private static final String AFEGIR_PRODUCTE_COMANDA = "4";
    private static final String ELIMINAR_PRODUCTE_COMANDA = "5";
    private static final String OBTENIR_COMANDES = "6";
    private static final String GET_COMANDA_UNICA = "7";
    private static final String GET_COMANDES = "8";
    private static final String EXISTEIX_COMANDA = "9";
    private static final String HELP = "help";
    private static final String EXIT = "exit";

    private static final String HELPTEXT = "Introduïu un dels següents números per executar la corresponent comanda:\n" +
            "   " + CONSTRUCTORA                  + " - Limpiar el constrolador de comandas actual\n" +
            "   " + CREAR_COMANDA                 + " - Crear una nova Comanda\n" +
            "   " + CREAR_COMANDA_AUTOMATICA      + " - Crear una Comanda amb productes automàticament\n" +
            "   " + ELIMINAR_COMANDA              + " - Elimina una Comanda existent\n" +
            "   " + AFEGIR_PRODUCTE_COMANDA       + " - Afegeix una quantitat de producte a una Comanda existent\n" +
            "   " + ELIMINAR_PRODUCTE_COMANDA     + " - Eliminar una quantitat de producte d'una Comanda\n" +
            "   " + OBTENIR_COMANDES              + " - Obtenir una llista de Comandes especificades\n" +
            "   " + GET_COMANDA_UNICA             + " - Obtenir una comanda específica\n" +
            "   " + GET_COMANDES                  + " - Obtenir totes les comandes creades\n" +
            "   " + EXISTEIX_COMANDA              + " - Verificar si existeix una Comanda\n" +
            "   " + HELP                          + " - Mostrar totes les accions de la classe CtrlComandes disponibles\n" +
            "   " + EXIT                          + " - Sortir del programa\n";

    static CtrlComandes ctrlComandes = new CtrlComandes();

    private static String readLine(Scanner scanner) {
        return scanner.nextLine();
    }

    // Test methods for each command

    public static void testConstructora() {
        ctrlComandes = new CtrlComandes();
        System.out.println("CtrlComandes creada correctament!");
    }

    public static void testCrearComanda(Scanner scanner) {
        System.out.println("Escrigui el nom de la nova Comanda:");
        String nomComanda = readLine(scanner);
        try {
            ctrlComandes.crearComanda(nomComanda);
            System.out.println("Comanda '" + nomComanda + "' creada correctament.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testCrearComandaAutomatica(Scanner scanner) {
        System.out.println("Escrigui el nom de la nova Comanda automàtica:");
        String nomComanda = readLine(scanner);

        try {
            ctrlComandes.crearComandaAutomatica(nomComanda, productosFaltantes);
            System.out.println("Comanda automàtica creada correctament.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testEliminarComanda(Scanner scanner) {
        System.out.println("Escrigui el nom de la Comanda a eliminar:");
        String nomComanda = readLine(scanner);
        try {
            ctrlComandes.eliminarComanda(nomComanda);
            System.out.println("Comanda '" + nomComanda + "' eliminada correctament.");
        } catch (ComandaNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testAfegirProducteComanda(Scanner scanner) {
        System.out.println("Escrigui el nom de la Comanda:");
        String nomComanda = readLine(scanner);
        System.out.println("Escrigui el nom del Producte:");
        String nomProducte = readLine(scanner);
        System.out.println("Escrigui la quantitat del Producte:");
        int quantitat = Integer.parseInt(readLine(scanner));
        try {
            ctrlComandes.afegirProducteComanda(nomComanda, nomProducte, quantitat);
            System.out.println("Producte '" + nomProducte + "' afegit correctament a la Comanda '" + nomComanda + "'.");
        } catch (QuanitatInvalidException | ComandaNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testEliminarProducteComanda(Scanner scanner) {
        System.out.println("Escrigui el nom de la Comanda:");
        String nomComanda = readLine(scanner);
        System.out.println("Escrigui el nom del Producte:");
        String nomProducte = readLine(scanner);
        System.out.println("Escrigui la quantitat del Producte:");
        int quantitat = Integer.parseInt(readLine(scanner));
        try {
            ctrlComandes.eliminarProducteComanda(nomComanda, nomProducte, quantitat);
            System.out.println("Producte '" + nomProducte + "' eliminat correctament de la Comanda '" + nomComanda + "'.");
        } catch (ProductNotFoundComandaException | ComandaNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    //Getters

    public static Map<String, Comanda> testObtenirComandes(Scanner scanner) {
        System.out.println("Escrigui els noms de les Comandes que vols obtenir separats per coma (exemple: Comanda1,Comanda2):");
        String input = readLine(scanner);
        String[] nomsComandes = input.split(",");
        try {
            return ctrlComandes.obtenirComandes(nomsComandes);

        } catch (ComandaNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public static Comanda testGetComandaUnica(Scanner scanner) {
        System.out.println("Escrigui el nom de la Comanda a obtenir:");
        String nomComanda = readLine(scanner);
        try {
            return ctrlComandes.getComandaUnica(nomComanda);
        } catch (ComandaNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Map<String, Comanda> testConsultarComandes() {
        return ctrlComandes.getComandes();

    }

    public static boolean testExisteixComanda(Scanner scanner) {
        System.out.println("Escrigui el nom de la Comanda a verificar:");
        String nomComanda = readLine(scanner);
        return ctrlComandes.existeixComanda(nomComanda);

    }

        // Command processor

        public static boolean commands(String command, Scanner scanner) {
            switch (command) {
                case CONSTRUCTORA -> testConstructora();
                case CREAR_COMANDA -> testCrearComanda(scanner);
                case CREAR_COMANDA_AUTOMATICA -> testCrearComandaAutomatica(scanner);
                case ELIMINAR_COMANDA -> testEliminarComanda(scanner);
                case AFEGIR_PRODUCTE_COMANDA -> testAfegirProducteComanda(scanner);
                case ELIMINAR_PRODUCTE_COMANDA -> testEliminarProducteComanda(scanner);
                case OBTENIR_COMANDES -> testObtenirComandes(scanner);
                case GET_COMANDA_UNICA -> testGetComandaUnica(scanner);
                case GET_COMANDES -> testConsultarComandes();
                case EXISTEIX_COMANDA -> testExisteixComanda(scanner);
                case HELP -> System.out.println(HELPTEXT);
                case EXIT -> {
                    System.out.println("Gràcies per utilitzar el programa DriverCtrlComandes!");
                    return false;
                }
                default -> System.out.println("Comanda no vàlida. Escriu 'help' per veure les comandes disponibles.");
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

        System.out.println("Gràcies per utilitzar el programa DriverCtrlComandes!");
    }




}
