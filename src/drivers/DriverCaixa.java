package drivers;

import java.util.Scanner;
import classes.Caixa;


public class DriverCaixa {

    public static final String CREAR_CAIXA = "0";
    public static final String AFEGIR_PRODUCTE = "1";
    public static final String RETIRAR_PRODUCTE = "2";
    public static final String GET_QUANTITAT = "3";
    public static final String PRINTPRODUCTE = "4";
    public static final String PRINTTICKET = "5";
    public static final String PAGAR = "6";
    public static final String HELP = "help";
    public static final String EXIT = "exit";


    private static final String HELPTXT = "Introduïu un dels següents números per executar la corresponent comanda:\n" +
    "   " + CREAR_CAIXA + " - Crear una nova caixa\n" +
    "   " + AFEGIR_PRODUCTE + " - Afegir un producte a la caixa\n" +
    "   " + RETIRAR_PRODUCTE + " - Retirar un producte de la caixa\n" +
    "   " + GET_QUANTITAT + " - Obtenir la quantitat d'un producte de la caixa\n" +
    "   " + PRINTTICKET + " - Imprimir el tiquet\n" +
    "   " + PAGAR + " - Pagar i buidar la caixa\n" +
    "   " + HELP + " - Mostra totes les comandes disponibles\n" +
    "   " + EXIT + " - Sortir del programa\n";

    private static Caixa C;

    private static String readLine(Scanner scanner) {
        return scanner.nextLine();
    }

    public static void testcrearcaixa(){
        C = new Caixa();
        System.out.println("Caixa creada correctament");
    }
    public static void testafegirproducte(Scanner scanner){
        System.out.println("Introdueix el nom del producte a afegir:");
        String nom = readLine(scanner);

        System.out.println("Introdueix la quantitat del producte a afegir:");
        int quantitat = Integer.parseInt(readLine(scanner));

        System.out.println("Introdueix l'id de la prestatgeria on es troba el producte:");
        String id = readLine(scanner);

        C.afegir_producte(nom, quantitat, id);
        System.out.println("Producte" + nom + "amb quantitat" + quantitat + "de prestatgeria" + id + "afegit correctament");
    }
    //Write the rest of the tests below
    public static void testretirarproducte(){
        Caixa C = new Caixa();
        C.afegir_producte("CocaCola", 2, "p1");
        C.retirar_producte("CocaCola", 1, "p1");
    }

    public static boolean commands(String command, Scanner scanner) {
        switch(command) {
            case CREAR_CAIXA:
                testcrearcaixa();
                break;
            case AFEGIR_PRODUCTE:
                testafegirproducte(scanner);
                break;
            case RETIRAR_PRODUCTE:
                testretirarproducte();
                break;
            case GET_QUANTITAT:
               // testgetquantitat();
                break;
            case PRINTPRODUCTE:
              //  testprintproducte();
                break;
            case PRINTTICKET:
             //   testprintticket();
                break;
            case PAGAR:
              //  testpagar();
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
}
