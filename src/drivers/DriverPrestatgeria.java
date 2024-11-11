package drivers;

import classes.Prestatgeria;
import classes.Pair;

import java.io.File;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

public class DriverPrestatgeria {

    private static final String CREAR_PRESTATGERIA = "0";
    private static final String AFEGIR_PRODUCTE = "1";
    private static final String ELIMINAR_PRODUCTE = "2";
    private static final String INCREMENTAR_QUANTITAT = "3";
    private static final String DECREMENTAR_QUANTITAT = "4";
    private static final String FIXAR_PRODUCTE = "5";
    private static final String DESFIXAR_PRODUCTE = "6";
    private static final String IMPRIMIR_PRODUCTES = "7";
    private static final String AUTO_REOMPLIR = "8";
    private static final String GET_ID = "9";
    private static final String GET_MIDA_PRESTATGERIA = "10";
    private static final String GET_MAX_BUIT = "11";
    private static final String GET_PRODUCTES = "12";
    private static final String GET_PRODUCTES_SIZE = "13";
    private static final String ESTA_A_PRESTATGERIA = "14";
    private static final String GET_POS = "15";
    private static final String GET_PRODUCTES_FIXATS = "16";
    private static final String HELP = "help";
    private static final String EXIT = "exit";

    private static final String HELPTXT = "Introduïu un dels següents números per executar la corresponent comanda:\n" +
            CREAR_PRESTATGERIA + " - Crear una nova prestatgeria\n" +
            AFEGIR_PRODUCTE + " - Afegir un producte a la prestatgeria\n" +
            ELIMINAR_PRODUCTE + " - Eliminar un producte de la prestatgeria\n" +
            INCREMENTAR_QUANTITAT + " - Incrementar la quantitat d'un producte\n" +
            DECREMENTAR_QUANTITAT + " - Decrementar la quantitat d'un producte\n" +
            FIXAR_PRODUCTE + " - Fixar un producte en la prestatgeria\n" +
            DESFIXAR_PRODUCTE + " - Desfixar un producte en la prestatgeria\n" +
            IMPRIMIR_PRODUCTES + " - Imprimir tots els productes\n" +
            AUTO_REOMPLIR + " - Auto-reomplir productes\n" +
            GET_ID + " - Obtenir l'ID de la prestatgeria\n" +
            GET_MIDA_PRESTATGERIA + " - Obtenir la mida de la prestatgeria\n" +
            GET_MAX_BUIT + " - Obtenir el màxim de productes buits\n" +
            GET_PRODUCTES + " - Llistar tots els productes\n" +
            GET_PRODUCTES_SIZE + " - Obtenir el nombre de productes\n" +
            ESTA_A_PRESTATGERIA + " - Comprovar si un producte està en la prestatgeria\n" +
            GET_POS + " - Obtenir la posició d'un producte\n" +
            GET_PRODUCTES_FIXATS + " - Llistar els productes fixats\n" +
            HELP + " - Mostrar les comandes disponibles\n" +
            EXIT + " - Sortir del programa\n";

    private static Prestatgeria P;
    private static final String NO_HI_HA_PRESTATGERIA = "No hi ha cap prestatgeria creada. Crea una prestatgeria primer.";

    public static void crearPrestatgeria(Scanner scanner) {
        System.out.println("Introdueix l'ID de la prestatgeria:");
        String id = scanner.nextLine();
        System.out.println("Introdueix la mida de la prestatgeria:");
        int midaPrestatgeria = Integer.parseInt(scanner.nextLine());
        System.out.println("Introdueix la mida del prestatge:");
        int midaPrestatge = Integer.parseInt(scanner.nextLine());
        P = new Prestatgeria(id, midaPrestatgeria, midaPrestatge);
        System.out.println("Prestatgeria creada correctament.");
    }

    public static void afegirProducte(Scanner scanner) {
        System.out.println("Introdueix el nom del producte:");
        String nom = scanner.nextLine();
        System.out.println("Introdueix la quantitat:");
        int quantitat = Integer.parseInt(scanner.nextLine());
        P.afegir_producte(nom, quantitat);
        System.out.println("Producte afegit correctament.");
    }

    public static void eliminarProducte(Scanner scanner) {
        System.out.println("Introdueix el nom del producte a eliminar:");
        String nom = scanner.nextLine();
        P.eliminar_producte(nom);
        System.out.println("Producte eliminat correctament.");
    }

    public static void incrementarQuantitat(Scanner scanner) {
        System.out.println("Introdueix el nom del producte:");
        String nom = scanner.nextLine();
        System.out.println("Introdueix la quantitat a incrementar:");
        int quantitat = Integer.parseInt(scanner.nextLine());
        P.incrementar_quantitat(nom, quantitat);
        System.out.println("Quantitat incrementada correctament.");
    }

    public static void decrementarQuantitat(Scanner scanner) {
        System.out.println("Introdueix el nom del producte:");
        String nom = scanner.nextLine();
        System.out.println("Introdueix la quantitat a decrementar:");
        int quantitat = Integer.parseInt(scanner.nextLine());
        P.decrementar_quantitat(nom, quantitat);
        System.out.println("Quantitat decrementada correctament.");
    }

    public static void fixarProducte(Scanner scanner) {
        System.out.println("Introdueix el nom del producte a fixar:");
        String nom = scanner.nextLine();
        P.fixar_producte_prestatgeria(nom);
        System.out.println("Producte fixat correctament.");
    }

    public static void desfixarProducte(Scanner scanner) {
        System.out.println("Introdueix el nom del producte a desfixar:");
        String nom = scanner.nextLine();
        P.desfixar_producte_prestatgeria(nom);
        System.out.println("Producte desfixat correctament.");
    }

    public static void imprimirProductes() {
        System.out.println("Productes en la prestatgeria:");
        P.imprimirdistribucio();
    }

    public static void autoReomplir() {
       // Set<Pair<String, Integer>> productes = P.auto_reomplir();
        //productes.forEach(pair -> System.out.println("Producte: " + pair.getKey() + ", Quantitat: " + pair.getValue()));
    }

    public static void getId() {
        System.out.println("ID de la prestatgeria: " + P.getid());
    }

    public static void getMidaPrestatgeria() {
        System.out.println("Mida de la prestatgeria: " + P.getMidaPrestatgeria());
    }


    public static void getProductes() {
        //   Vector<String> productes = P.getProductes();
        //System.out.println("Productes en la prestatgeria: " + productes);
    }

    public static void getProductesSize() {
        System.out.println("Nombre de productes: " + P.getProductesSize());
    }

    public static void estaAPrestatgeria(Scanner scanner) {
        System.out.println("Introdueix el nom del producte:");
        String nom = scanner.nextLine();
        System.out.println("Està a la prestatgeria? " + P.esta_a_prestatgeria(nom));
    }

    public static void getPos(Scanner scanner) {
        System.out.println("Introdueix el nom del producte:");
        String nom = scanner.nextLine();
        System.out.println("Posició del producte: " + P.get_pos(nom));
    }

    public static void getProductesFixats() {
        Set<String> productesFixats = P.getProductesFixats();
        System.out.println("Productes fixats: " + productesFixats);
    }

    public static boolean commands(String command, Scanner scanner) {
        switch (command) {
            case CREAR_PRESTATGERIA:
                crearPrestatgeria(scanner);
                crearPrestatgeria(scanner);
                break;
            case AFEGIR_PRODUCTE:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRESTATGERIA);
                    break;
                }
                afegirProducte(scanner);
                break;
            case ELIMINAR_PRODUCTE:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRESTATGERIA);
                    break;
                }
                eliminarProducte(scanner);
                break;
            case INCREMENTAR_QUANTITAT:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRESTATGERIA);
                    break;
                }
                incrementarQuantitat(scanner);
                break;
            case DECREMENTAR_QUANTITAT:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRESTATGERIA);
                    break;
                }
                decrementarQuantitat(scanner);
                break;
            case FIXAR_PRODUCTE:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRESTATGERIA);
                    break;
                }
                fixarProducte(scanner);
                break;
            case DESFIXAR_PRODUCTE:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRESTATGERIA);
                    break;
                }
                desfixarProducte(scanner);
                break;
            case IMPRIMIR_PRODUCTES:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRESTATGERIA);
                    break;
                }
                imprimirProductes();
                break;
            case AUTO_REOMPLIR:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRESTATGERIA);
                    break;
                }
                autoReomplir();
                break;
            case GET_ID:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRESTATGERIA);
                    break;
                }
                getId();
                break;
            case GET_MIDA_PRESTATGERIA:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRESTATGERIA);
                    break;
                }
                getMidaPrestatgeria();
                break;
            case GET_PRODUCTES:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRESTATGERIA);
                    break;
                }
                getProductes();
                break;
            case GET_PRODUCTES_SIZE:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRESTATGERIA);
                    break;
                }
                getProductesSize();
                break;
            case ESTA_A_PRESTATGERIA:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRESTATGERIA);
                    break;
                }
                estaAPrestatgeria(scanner);
                break;
            case GET_POS:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRESTATGERIA);
                    break;
                }
                getPos(scanner);
                break;
            case GET_PRODUCTES_FIXATS:
                if (P == null) {
                    System.out.println(NO_HI_HA_PRESTATGERIA);
                    break;
                }
                getProductesFixats();
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
        if (args.length != 0) {
            try {
                File file = new File(args[0]);
                Scanner myReader = new Scanner(file);
                while (myReader.hasNextLine() && run)
                    run = commands(myReader.nextLine(), myReader);
                myReader.close();
            } catch (Exception e) {
                System.out.println("Error: Arxiu no admès.");
            }
        }

        if (run){
            System.out.println(HELPTXT);
        }
        Scanner in = new Scanner(System.in);
        while (run) {
            run = commands(in.nextLine(), in);
        }
        System.out.println("Programa DriverPrestatgeria finalitzat.");
    }
}