package drivers;

import controladors.CtrlComandes;
import classes.Comanda;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

public class DriverCtrlComandes {

    // Command constants
    private static final String CONSTRUCTORA = "0";
    private static final String CREAR_COMANDA = "1";
    private static final String AFEGIR_PRODUCTE_COMANDA = "2";
    private static final String ELIMINAR_COMANDA = "3";
    private static final String OBTENIR_COMANDES = "4";
    private static final String CONSULTAR_COMANDES = "5";
    private static final String HELP = "help";
    private static final String EXIT = "exit";

    // Help text
    private static final String HELPTEXT = "Introduïu un dels següents números per executar la corresponent comanda:\n" +
            "   " + CONSTRUCTORA                  + " - Construir el controlador de comandes (CtrlComandes)\n" +
            "   " + CREAR_COMANDA                 + " - Crear una nova Comanda\n" +
            "   " + AFEGIR_PRODUCTE_COMANDA       + " - Afegeix un producte a una Comanda existent\n" +
            "   " + ELIMINAR_COMANDA              + " - Elimina una Comanda existent\n" +
            "   " + OBTENIR_COMANDES              + " - Obtenir una llista de Comandes especificades\n" +
            "   " + CONSULTAR_COMANDES              + " - Veure totes les comandes creades\n" +
            "   " + HELP                          + " - Mostrar totes les accions de la clase CtrlComandes disponibles\n" +
            "   " + EXIT                          + " - Sortir del programa\n";

    // Controller instance
    static CtrlComandes ctrlComandes;

    private static String readLine(Scanner scanner) {
        return scanner.nextLine();
    }

    // Test methods for each command

    // Initializes CtrlComandes
    public static void testConstructora() {
        ctrlComandes = new CtrlComandes();
        System.out.println("CtrlComandes creada correctament!");
    }

    // Creates a new Comanda
    public static void testCrearComanda(Scanner scanner) {
        System.out.println("Escrigui el nom de la nova Comanda:");
        String nomComanda = readLine(scanner);
        CtrlComandes.crearComanda(nomComanda);
    }

    // Adds a product to a specific Comanda
    public static void testAfegirProducteComanda(Scanner scanner) {
        System.out.println("Escrigui el nom de la Comanda:");
        String nomComanda = readLine(scanner);

        System.out.println("Escrigui el nom del Producte:");
        String nomProducte = readLine(scanner);

        System.out.println("Escrigui la quantitat del Producte:");
        int quantitat = Integer.parseInt(readLine(scanner));

        CtrlComandes.afegirProducteComanda(nomComanda, nomProducte, quantitat);
    }

    // Removes an existing Comanda
    public static void testEliminarComanda(Scanner scanner) {
        System.out.println("Escrigui el nom de la Comanda a eliminar:");
        String nomComanda = readLine(scanner);

        CtrlComandes.eliminarComanda(nomComanda);
    }

    // Retrieves specified Comandes
    public static void testObtenirComandes(Scanner scanner) {
        System.out.println("Escrigui els noms de les Comandes que vols obtenir separats per coma (exemple: Comanda1,Comanda2):");
        String input = readLine(scanner);
        String[] nomsComandes = input.split(",");

        Map<String, Comanda> comandes = CtrlComandes.obtenirComandes(nomsComandes);

        System.out.println("Comandes trobades:");
        for (String nom : comandes.keySet()) {
            System.out.println(" - " + nom + ": " + comandes.get(nom).getOrdres());
        }
    }


    public static void testConsultarComandes() {
        Map<String, Comanda> comandes_creades = ctrlComandes.getComandesCreades();
        if (comandes_creades.isEmpty()) {
            System.out.println("No hi ha comandes creades.");
            return;
        }

        System.out.println("Llista de totes les comandes:");
        for (Map.Entry<String, Comanda> entry : comandes_creades.entrySet()) {
            String nomComanda = entry.getKey();
            Comanda comanda = entry.getValue();

            System.out.println("Comanda: " + nomComanda);
            System.out.println("Productes i quantitats: " + comanda.getOrdres());
        }
    }

        // Command processor
    public static boolean commands(String command, Scanner scanner) {
        switch (command) {
            case CONSTRUCTORA:
                testConstructora();
                break;
            case CREAR_COMANDA:
                testCrearComanda(scanner);
                break;
            case AFEGIR_PRODUCTE_COMANDA:
                testAfegirProducteComanda(scanner);
                break;
            case ELIMINAR_COMANDA:
                testEliminarComanda(scanner);
                break;
            case OBTENIR_COMANDES:
                testObtenirComandes(scanner);
                break;
            case CONSULTAR_COMANDES:
                testConsultarComandes();
                break;
            case HELP:
                System.out.println(HELPTEXT);
                break;
            case EXIT:
                return false;
            default:
                System.out.println("Comanda no vàlida. Escriu 'help' per veure les comandes disponibles.");
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

        System.out.println("Gràcies per utilitzar el programa DriverCtrlComandes!");
    }
}
