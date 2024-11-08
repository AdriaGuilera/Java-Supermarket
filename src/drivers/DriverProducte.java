package drivers;

import java.util.Scanner;
import classes.Producte;

public class DriverProducte {

    public static final String CREAR_PRODUCTE = "0";
    public static final String MOD_NOM = "1";
    public static final String MOD_CATEGORIA = "2";
    public static final String MOD_MAX_HUECO = "3";
    public static final String MOD_MAX_MAGATZEM = "4";
    public static final String MOD_STOCK = "5";
    public static final String INCREMENTAR_STOCK = "6";
    public static final String DECREMENTAR_STOCK = "7";
    public static final String AFEGIR_SIMILITUD = "8";
    public static final String GET_SIMILITUD = "9";
    public static final String IMPRIMIR_SIMILITUDS = "10";
    public static final String GET_STOCK = "11";
    public static final String GET_CATEGORIA = "12";
    public static final String GET_MAX_HUECO = "13";
    public static final String GET_MAX_MAGATZEM = "14";
    public static final String HELP = "help";
    public static final String EXIT = "exit";

    private static final String HELPTXT = "Introduïu un dels següents números per executar la corresponent comanda:\n" +
            "   " + CREAR_PRODUCTE + " - Crear un nou producte\n" +
            "   " + MOD_NOM + " - Modificar el nom del producte\n" +
            "   " + MOD_MAX_HUECO + " - Modificar el màxim de hueco\n" +
            "   " + MOD_MAX_MAGATZEM + " - Modificar el màxim de magatzem\n" +
            "   " + MOD_STOCK + " - Modificar el stock actual\n" +
            "   " + INCREMENTAR_STOCK + " - Incrementar el stock\n" +
            "   " + DECREMENTAR_STOCK + " - Decrementar el stock\n" +
            "   " + AFEGIR_SIMILITUD + " - Afegir similitud amb un altre producte\n" +
            "   " + GET_SIMILITUD + " - Obtenir la similitud amb un altre producte\n" +
            "   " + IMPRIMIR_SIMILITUDS + " - Imprimir totes les similituds\n" +
            "   " + GET_STOCK + " - Obtenir el stock actual\n" +
            "   " + GET_MAX_HUECO + " - Obtenir el màxim hueco\n" +
            "   " + GET_MAX_MAGATZEM + " - Obtenir el màxim magatzem\n" +
            "   " + HELP + " - Mostra totes les comandes disponibles\n" +
            "   " + EXIT + " - Sortir del programa\n";

    private static final String NO_HI_HA_PRODUCTE = "No hi ha cap producte creat. Crea un producte primer.";
    private static Producte P;

    private static String readLine(Scanner scanner) {
        return scanner.nextLine();
    }

    public static void testCrearProducte(Scanner scanner) {
        System.out.println("Introdueix el nom del producte:");
        String nom = readLine(scanner);



        System.out.println("Introdueix el màxim hueco:");
        int max_hueco = Integer.parseInt(readLine(scanner));

        System.out.println("Introdueix el màxim magatzem:");
        int max_magatzem = Integer.parseInt(readLine(scanner));

        P = new Producte(nom, max_hueco, max_magatzem);
        System.out.println("Producte creat correctament");
    }

    public static void testModNom(Scanner scanner) {
        System.out.println("Introdueix el nou nom del producte:");
        String nou_nom = readLine(scanner);
        P.mod_nom(nou_nom);
        System.out.println("Nom del producte modificat a: " + nou_nom);
    }


    public static void testModMaxHueco(Scanner scanner) {
        System.out.println("Introdueix el nou màxim de hueco:");
        int max_hueco = Integer.parseInt(readLine(scanner));
        P.mod_mh(max_hueco);
        System.out.println("Màxim hueco modificat a: " + max_hueco);
    }

    public static void testModMaxMagatzem(Scanner scanner) {
        System.out.println("Introdueix el nou màxim de magatzem:");
        int max_magatzem = Integer.parseInt(readLine(scanner));
        P.mod_mm(max_magatzem);
        System.out.println("Màxima quantitat al magatzem modificada a: " + max_magatzem);
    }

    public static void testModStock(Scanner scanner) {
        System.out.println("Introdueix el nou stock:");
        int nou_stock = Integer.parseInt(readLine(scanner));
        P.mod_stock(nou_stock);
        System.out.println("Stock modificat a: " + nou_stock);
    }

    public static void testIncrementarStock(Scanner scanner) {
        System.out.println("Introdueix la quantitat a incrementar:");
        int quant = Integer.parseInt(readLine(scanner));
        P.incrementar_stock(quant);
        System.out.println("Stock incrementat en " + quant + " unitats.");
    }

    public static void testDecrementarStock(Scanner scanner) {
        System.out.println("Introdueix la quantitat a decrementar:");
        int quant = Integer.parseInt(readLine(scanner));
        P.decrementar_stock(quant);
        System.out.println("Stock decrementat en " + quant + " unitats.");
    }

    public static void testAfegirSimilitud(Scanner scanner) {
        System.out.println("Introdueix el nom del producte similar:");
        String nom = readLine(scanner);

        System.out.println("Introdueix el valor de similitud:");
        float valor = Float.parseFloat(readLine(scanner));

        P.afegir_similitud(nom, valor);
        System.out.println("Similitud amb" + nom + " de " + valor + "afegida.");
    }

    public static void testGetSimilitud(Scanner scanner) {
        System.out.println("Introdueix el nom del producte per obtenir la similitud:");
        String nom = readLine(scanner);

        float similitud = P.getSimilitud(nom);
        System.out.println("Similitud amb el producte " + nom + " és " + similitud);
    }

    public static void testImprimirSimilituds() {
        P.imprimir_similituds();
    }

    public static void testGetStock() {
        int stock = P.get_stock();
        System.out.println("Stock actual: " + stock);
    }


    public static void testGetMaxHueco() {
        int maxHueco = P.get_max_hueco();
        System.out.println("Màxim hueco: " + maxHueco);
    }

    public static void testGetMaxMagatzem() {
        int maxMagatzem = P.get_max_magatzem();
        System.out.println("Màxim magatzem: " + maxMagatzem);
    }

    public static boolean commands(String command, Scanner scanner) {
        switch(command) {
            case CREAR_PRODUCTE:
                testCrearProducte(scanner);
                break;
            case MOD_NOM:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRODUCTE);
                } else {
                    testModNom(scanner);
                }
                break;
            case MOD_MAX_HUECO:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRODUCTE);
                } else {
                    testModMaxHueco(scanner);
                }
                break;
            case MOD_MAX_MAGATZEM:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRODUCTE);
                } else {
                    testModMaxMagatzem(scanner);
                }
                break;
            case MOD_STOCK:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRODUCTE);
                } else {
                    testModStock(scanner);
                }
                break;
            case INCREMENTAR_STOCK:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRODUCTE);
                } else {
                    testIncrementarStock(scanner);
                }
                break;
            case DECREMENTAR_STOCK:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRODUCTE);
                } else {
                    testDecrementarStock(scanner);
                }
                break;
            case AFEGIR_SIMILITUD:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRODUCTE);
                } else {
                    testAfegirSimilitud(scanner);
                }
                break;
            case GET_SIMILITUD:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRODUCTE);
                } else {
                    testGetSimilitud(scanner);
                }
                break;
            case IMPRIMIR_SIMILITUDS:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRODUCTE);
                } else {
                    testImprimirSimilituds();
                }
                break;
            case GET_STOCK:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRODUCTE);
                } else {
                    testGetStock();
                }
                break;

            case GET_MAX_HUECO:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRODUCTE);
                } else {
                    testGetMaxHueco();
                }
                break;
            case GET_MAX_MAGATZEM:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRODUCTE);
                } else {
                    testGetMaxMagatzem();
                }
                break;
            case HELP:
                System.out.println(HELPTXT);
                break;
            case EXIT:
                return false;
            default:
                System.out.println("Comanda no vàlida. Escriu 'help' per veure les comandes disponibles.");
                break;
        }
        return true;
    }

    public static void main(String[] args) {
        boolean run = true;
        Scanner in = new Scanner(System.in);
        System.out.println(HELPTXT);
        while (run) {
            run = commands(in.nextLine(), in);
        }
        System.out.println("Programa DriverProducte finalitzat.");
    }
}




