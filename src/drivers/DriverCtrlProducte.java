package drivers;

import controladors.CtrlProducte;
import classes.Producte;

import java.util.Scanner;

public class DriverCtrlProducte {

    // Command constants
    private static final String CONSTRUCTORA = "0";
    private static final String ALTA_PRODUCTE = "1";
    private static final String MODIFICAR_PRODUCTE = "2";
    private static final String DECREMENTAR_STOCK = "3";
    private static final String IMPREME_PRODUCTE = "4";
    private static final String ELIMINAR_PRODUCTE = "5";
    private static final String BUSCAR_PRODUCTE = "6";
    private static final String SIMILITUD = "7";
    private static final String EXIT = "exit";
    private static final String HELP = "help";

    // Help text
    private static final String HELPTEXT = "Introduïu un dels següents números per executar la corresponent comanda:\n" +
            "   " + CONSTRUCTORA          + " - Crear el controlador de productes (CtrlProducte)\n" +
            "   " + ALTA_PRODUCTE         + " - Alta d'un nou producte\n" +
            "   " + MODIFICAR_PRODUCTE    + " - Modificar un producte existent\n" +
            "   " + DECREMENTAR_STOCK     + " - Decrementar stock d'un producte\n" +
            "   " + IMPREME_PRODUCTE      + " - Imprimir les dades d'un producte\n" +
            "   " + ELIMINAR_PRODUCTE     + " - Eliminar un producte\n" +
            "   " + BUSCAR_PRODUCTE       + " - Buscar un producte per criteris\n" +
            "   " + SIMILITUD             + " - Afegir o consultar la similitud entre productes\n" +
            "   " + HELP                  + " - Mostrar totes les accions de la classe CtrlProducte disponibles\n" +
            "   " + EXIT                  + " - Sortir del programa\n";

    // Controller instance
    static CtrlProducte ctrlProducte = new CtrlProducte();

    private static String readLine(Scanner scanner) {
        return scanner.nextLine();
    }

    // Test methods for each command

    // Initializes CtrlProducte
    public static void testConstructora() {
        ctrlProducte = new CtrlProducte();
        System.out.println("CtrlProducte creada correctament!");
    }

    // Creates a new Producte
    public static void testAltaProducte(Scanner scanner) {
        System.out.println("Escrigui el nom del nou producte:");
        String nom = readLine(scanner);

        System.out.println("Escrigui la capacitat màxima al magatzem:");
        int mh = Integer.parseInt(readLine(scanner));

        System.out.println("Escrigui la capacitat màxima a l'estanteria:");
        int mm = Integer.parseInt(readLine(scanner));

        ctrlProducte.altaProducte(nom, mh, mm);
    }


    // Decreases the stock of a product
    public static void testDecrementarStock(Scanner scanner) {
        System.out.println("Escrigui el nom del producte:");
        String nom = readLine(scanner);

        System.out.println("Escrigui la quantitat a decrementar:");
        int quantitat = Integer.parseInt(readLine(scanner));

        ctrlProducte.decrementar_stock(nom, quantitat);
    }

    // Prints product details
    public static void testImprimirProducte(Scanner scanner) {
        System.out.println("Escrigui el nom del producte a imprimir:");
        String nom = readLine(scanner);

        ctrlProducte.getProducte(nom);
    }

    // Removes a product from the warehouse
    public static void testEliminarProducte(Scanner scanner) {
        System.out.println("Escrigui el nom del producte a eliminar:");
        String nom = readLine(scanner);

        ctrlProducte.eliminar_producte(nom);
    }

    // Add or get similarity between two products
    public static void testSimilitud(Scanner scanner) {
        System.out.println("Escrigui el nom del primer producte:");
        String nom1 = readLine(scanner);

        System.out.println("Escrigui el nom del segon producte:");
        String nom2 = readLine(scanner);

        System.out.println("Escrigui la similitud entre els dos productes:");
        float value = Float.parseFloat(readLine(scanner));

        ctrlProducte.afegir_similitud(nom1, nom2, value);
        double similitud = ctrlProducte.obtenir_similitud(nom1, nom2);
        System.out.println("La similitud entre els productes és: " + similitud);
    }

    // Command processor
    public static boolean commands(String command, Scanner scanner) {
        switch (command) {
            case CONSTRUCTORA:
                testConstructora();
                break;
            case ALTA_PRODUCTE:
                testAltaProducte(scanner);
                break;
            case DECREMENTAR_STOCK:
                testDecrementarStock(scanner);
                break;
            case IMPREME_PRODUCTE:
                testImprimirProducte(scanner);
                break;
            case ELIMINAR_PRODUCTE:
                testEliminarProducte(scanner);
                break;
            case SIMILITUD:
                testSimilitud(scanner);
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

        // Interactive mode
        if (run) System.out.println(HELPTEXT);
        Scanner in = new Scanner(System.in);
        while (run)
            run = commands(in.nextLine(), in);

        System.out.println("Gràcies per utilitzar el programa DriverCtrlProducte!");
    }
}
