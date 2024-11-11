package drivers;

import classes.Comanda;

import java.io.File;
import java.util.Scanner;

public class DriverComanda {

    private static final String CONSTRUCTORA = "0";
    private static final String AFEGIR_PRODUCTE = "1";
    private static final String  ELIMINAR_PRODUCTE = "2";
    private static final String GET_ORDRES = "3";
    private static final String OBTENIR_QUANTITAT_PRODUCTE = "4";
    private static final String HELP = "help";
    private static final String EXIT = "exit";



    private static final String HELPTEXT = "Introduïu un dels següents números per executar la corresponent comanda:\n" +
            "   " + CONSTRUCTORA        + " - Construir una Comanda Buida\n" +
            "   " + AFEGIR_PRODUCTE       + " - Afegeix un (Producte, quantitat) a la Comanda\n" +
            "   " + ELIMINAR_PRODUCTE      + " - Eliminar un Producte de la Comanda \n" +
            "   " + GET_ORDRES     + " - Obtenir totes les ordres (Producte,quantitat) de la Comanda\n" +
            "   " + OBTENIR_QUANTITAT_PRODUCTE    + " - Obtenir la quantitat d'un Producte de la Comanda\n" +
            "   " + HELP          + " - Mostrar totes les accions de la clase Comanda disponibles\n" +
            "   " + EXIT          + " - Sortir del programa\n";


    static Comanda comanda;

    private static String readLine(Scanner scanner) {
        return scanner.nextLine();
    }


    // Funciones (Casos)

    //Crea un comanda buida
    public static void testConstructora(Scanner scanner){
         System.out.println("Escrigui el nom de la Comanda:");
         String nomComanda = readLine(scanner);
         comanda = new Comanda(nomComanda);
        System.out.println("Comanda creada correctament!");
    }

    public static void testAfegirProducte(Scanner scanner){
        System.out.println("Escrigui el nom del Producte:");
        String nomProducte = readLine(scanner);

        System.out.println("Escrigui la quantitat del Producte:");
        int quantitat = Integer.parseInt(readLine(scanner));

        comanda.afegirProducte(nomProducte,quantitat);

        System.out.println("Producte afegit correctament a la Comanda!");
    }

    public static void testEliminarProducte(Scanner scanner){
        System.out.println("Escrigui el nom de Producte:");
        String nomProducte = readLine(scanner);
        comanda.eliminarProducte(nomProducte);
        System.out.println("Producte eliminat correctament de la Comanda!");
    }

    public static void testPrintOrdres(){
        System.out.println(comanda.getOrdres());
    }

    public static void testObtenirQuantitatProducte(Scanner scanner){
        System.out.println("Escrigui el nom del Producte:");
        String nomProducte= readLine(scanner);
        System.out.println(comanda.obtenirQuantitatProducte(nomProducte));
    }

    //Main

    public static boolean commands(String command, Scanner scanner) {
        switch(command) {
            case CONSTRUCTORA:
                testConstructora(scanner);
                break;
            case AFEGIR_PRODUCTE:
                testAfegirProducte(scanner);
                break;
            case ELIMINAR_PRODUCTE:
                testEliminarProducte(scanner);
                break;
            case GET_ORDRES:
                testPrintOrdres();
                break;
            case OBTENIR_QUANTITAT_PRODUCTE:
                testObtenirQuantitatProducte(scanner);
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
                System.out.println("ERROR: Arxiu no admès");
            }
        }

        if (run) System.out.println(HELPTEXT);
        Scanner in = new Scanner(System.in);
        while (run)
            run = commands(in.nextLine(), in);

        System.out.println("Gràcies per utilitzar el programa DriverComanda!");

    }




}
