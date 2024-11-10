package drivers;

import controladors.CtrlPrestatgeria;
import classes.Pair;

import java.io.File;
import java.util.Scanner;
import java.util.Set;

public class DriverCtrlPrestatgeria {

    public static final String CREAR_CTRL_PRESTATGERIA = "0";
    public static final String AFEGIR_PRESTATGERIA = "1";
    public static final String ELIMINAR_PRESTATGERIA = "2";
    public static final String FIXAR_PRODUCTE = "3";
    public static final String DESFIXAR_PRODUCTE = "4";
    public static final String REPOSAR_PRESTATGERIA = "5";
    public static final String RETIRAR_PRODUCTE_PRESTATGERIA = "6";
    public static final String MOURE_STOCK_MAGATZEM = "7";
    public static final String AFEGIR_PRESTATGE = "8";
    public static final String ELIMINAR_PRESTATGE = "9";
    public static final String MOURE_PRODUCTE = "10";
    public static final String CONTAINS_PRODUCTE = "11";
    public static final String HELP = "help";
    public static final String EXIT = "exit";

    private static final String HELPTXT = "Introduïu un dels següents números per executar la corresponent comanda:\n" +
            "   " + CREAR_CTRL_PRESTATGERIA + " - Crear el controlador de prestatgeries\n" +
            "   " + AFEGIR_PRESTATGERIA + " - Afegir una prestatgeria\n" +
            "   " + ELIMINAR_PRESTATGERIA + " - Eliminar una prestatgeria\n" +
            "   " + FIXAR_PRODUCTE + " - Fixar un producte a una prestatgeria\n" +
            "   " + DESFIXAR_PRODUCTE + " - Desfixar un producte d'una prestatgeria\n" +
            "   " + REPOSAR_PRESTATGERIA + " - Reposar una prestatgeria\n" +
            "   " + RETIRAR_PRODUCTE_PRESTATGERIA + " - Retirar un producte d'una prestatgeria\n" +
            "   " + MOURE_STOCK_MAGATZEM + " - Moure stock del magatzem a una prestatgeria\n" +
            "   " + AFEGIR_PRESTATGE + " - Afegir un prestatge\n" +
            "   " + ELIMINAR_PRESTATGE + " - Eliminar un prestatge\n" +
            "   " + MOURE_PRODUCTE + " - Moure un producte a una prestatgeria\n" +
            "   " + CONTAINS_PRODUCTE + " - Comprovar si un producte està en una prestatgeria\n" +
            "   " + HELP + " - Mostra totes les comandes disponibles\n" +
            "   " + EXIT + " - Sortir del programa\n";

    private static CtrlPrestatgeria ctrlPrestatgeria;

    private static String readLine(Scanner scanner) {
        return scanner.nextLine();
    }

    public static void testCrearCtrlPrestatgeria() {
        ctrlPrestatgeria = new CtrlPrestatgeria();
        System.out.println("Controlador de prestatgeries creat correctament");
    }

    public static void testAfegirPrestatgeria(Scanner scanner) {
        System.out.println("Introdueix l'ID de la prestatgeria:");
        String id = readLine(scanner);

        System.out.println("Introdueix la mida de la prestatgeria:");
        int mida = Integer.parseInt(readLine(scanner));

        System.out.println("Introdueix el màxim de la prestatgeria:");
        int max = Integer.parseInt(readLine(scanner));

        System.out.println("Introdueix la mida de prestatge:");
        int mida_prestatge = Integer.parseInt(readLine(scanner));

        System.out.println(ctrlPrestatgeria.afegirPrestatgeria(id, mida, max, mida_prestatge));
    }

    public static void testEliminarPrestatgeria(Scanner scanner) {
        System.out.println("Introdueix l'ID de la prestatgeria a eliminar:");
        String id = readLine(scanner);

        System.out.println(ctrlPrestatgeria.eliminarPrestatgeria(id));
    }

    public static void testFixarProducte(Scanner scanner) {
        System.out.println("Introdueix l'ID de la prestatgeria:");
        String id = readLine(scanner);

        System.out.println("Introdueix el nom del producte:");
        String nomP = readLine(scanner);

        System.out.println(ctrlPrestatgeria.fixarProducte(id, nomP));
    }

    public static void testDesfixarProducte(Scanner scanner) {
        System.out.println("Introdueix l'ID de la prestatgeria:");
        String id = readLine(scanner);

        System.out.println("Introdueix el nom del producte:");
        String nomP = readLine(scanner);

        System.out.println(ctrlPrestatgeria.desfixarProducte(id, nomP));
    }

    public static void testReposarPrestatgeria(Scanner scanner) {
        System.out.println("Introdueix l'ID de la prestatgeria a reposar:");
        String id = readLine(scanner);

        Set<Pair<String, Integer>> result = ctrlPrestatgeria.reposarPrestatgeria(id);
        if (result.isEmpty()) {
            System.out.println("No s'ha pogut reposar la prestatgeria o està buida.");
        } else {
            result.forEach(pair -> System.out.println("Producte: " + pair.getKey() + ", Quantitat: " + pair.getValue()));
        }
    }

    public static void testRetirarProductePrestatgeria(Scanner scanner) {
        System.out.println("Introdueix l'ID de la prestatgeria:");
        String id = readLine(scanner);

        System.out.println("Introdueix el nom del producte:");
        String nomP = readLine(scanner);

        Pair<String, Integer> result = ctrlPrestatgeria.retirarProductePrestatgeria(id, nomP);
        System.out.println("Producte retirat: " + result.getKey() + ", Quantitat: " + result.getValue());
    }

    public static void testMoureStockMagatzem(Scanner scanner) {
        System.out.println("Introdueix l'ID de la prestatgeria:");
        String id = readLine(scanner);

        System.out.println("Introdueix el nom del producte:");
        String nomP = readLine(scanner);

        System.out.println("Introdueix la quantitat:");
        int quantitat = Integer.parseInt(readLine(scanner));

        ctrlPrestatgeria.decrementar_quantitat_producte(id, nomP, quantitat);
        System.out.println("Quantitat de " + nomP + "a prestatgeria "+ id +  " decrementada en " + quantitat);
    }

    public static void testAfegirPrestatge(Scanner scanner) {
        System.out.println("Introdueix l'ID de la prestatgeria:");
        String id = readLine(scanner);

        System.out.println(ctrlPrestatgeria.afegir_prestatgeria(id));
    }

    public static void testEliminarPrestatge(Scanner scanner) {
        System.out.println("Introdueix l'ID de la prestatgeria:");
        String id = readLine(scanner);

        System.out.println(ctrlPrestatgeria.eliminar_prestatgeria(id));
    }

    public static void testMoureProducte(Scanner scanner) {
        System.out.println("Introdueix el nom del producte:");
        String nom = readLine(scanner);

        System.out.println("Introdueix la quantitat:");
        int quantitat = Integer.parseInt(readLine(scanner));

        System.out.println("Introdueix l'ID de la prestatgeria:");
        String id_prest = readLine(scanner);

        System.out.println("Introdueix el màxim buit:");
        int max_hueco = Integer.parseInt(readLine(scanner));

        int result = ctrlPrestatgeria.moureProducte(nom, quantitat, id_prest, max_hueco);
        System.out.println(result == 0 ? "Producte mogut correctament" : "Error en moure el producte");
    }

    public static void testContainsProducte(Scanner scanner) {
        System.out.println("Introdueix el nom del producte:");
        String nom_producte = readLine(scanner);

        System.out.println("Introdueix la quantitat:");
        int quantitat = Integer.parseInt(readLine(scanner));

        System.out.println("Introdueix l'ID de la prestatgeria:");
        String id_prestatgeria = readLine(scanner);

        System.out.println("Introdueix la quantitat ja afegida:");
        int quantitat_ja_afegida = Integer.parseInt(readLine(scanner));

        boolean result = ctrlPrestatgeria.contains_producte(nom_producte, quantitat, id_prestatgeria, quantitat_ja_afegida);
        System.out.println(result ? "El producte està a la prestatgeria" : "El producte no està a la prestatgeria");
    }

    public static boolean commands(String command, Scanner scanner) {
        switch (command) {
            case CREAR_CTRL_PRESTATGERIA:
                testCrearCtrlPrestatgeria();
                break;
            case AFEGIR_PRESTATGERIA:
                testAfegirPrestatgeria(scanner);
                break;
            case ELIMINAR_PRESTATGERIA:
                testEliminarPrestatgeria(scanner);
                break;
            case FIXAR_PRODUCTE:
                testFixarProducte(scanner);
                break;
            case DESFIXAR_PRODUCTE:
                testDesfixarProducte(scanner);
                break;
            case REPOSAR_PRESTATGERIA:
                testReposarPrestatgeria(scanner);
                break;
            case RETIRAR_PRODUCTE_PRESTATGERIA:
                testRetirarProductePrestatgeria(scanner);
                break;
            case MOURE_STOCK_MAGATZEM:
                testMoureStockMagatzem(scanner);
                break;
            case AFEGIR_PRESTATGE:
                testAfegirPrestatge(scanner);
                break;
            case ELIMINAR_PRESTATGE:
                testEliminarPrestatge(scanner);
                break;
            case MOURE_PRODUCTE:
                testMoureProducte(scanner);
                break;
            case CONTAINS_PRODUCTE:
                testContainsProducte(scanner);
                break;
            case HELP:
                System.out.println(HELPTXT);
                break;
            case EXIT:
                System.out.println("Sortint del programa...");
                return false;
            default:
                System.out.println("Comanda no reconeguda. Escriu 'help' per veure les comandes disponibles.");
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
        System.out.println("Programa DriverCaixa finalitzat.");
    }
}
