package drivers;

import java.util.Scanner;
import classes.Caixa;
import java.io.File;
import java.util.Scanner;


public class DriverCaixa {

    public static final String CREAR_CAIXA = "0";
    public static final String AFEGIR_PRODUCTE = "1";
    public static final String RETIRAR_PRODUCTE = "2";
    public static final String GET_QUANTITAT = "3";
    public static final String PRINTTICKET = "4";
    public static final String PRINTTICKETPERPRESTATGERIES = "5";
    public static final String PAGAR = "6";
    public static final String HELP = "help";
    public static final String EXIT = "exit";


    private static final String HELPTXT = "Introduïu un dels següents números per executar la corresponent comanda:\n" +
    "   " + CREAR_CAIXA + " - Crear una nova caixa\n" +
    "   " + AFEGIR_PRODUCTE + " - Afegir un producte a la caixa\n" +
    "   " + RETIRAR_PRODUCTE + " - Retirar un producte de la caixa\n" +
    "   " + GET_QUANTITAT + " - Obtenir la quantitat d'un producte de la caixa\n" +
    "   " + PRINTTICKET + " - Imprimir el tiquet total\n" +
    "   " + PRINTTICKETPERPRESTATGERIES + " - Imprimir el tiquet per productes aprestatgeries\n" +
    "   " + PAGAR + " - Pagar i buidar la caixa\n" +
    "   " + HELP + " - Mostra totes les comandes disponibles\n" +
    "   " + EXIT + " - Sortir del programa\n";


    private static final String NOHIHACAIXA = "No hi ha cap caixa creada. Crea una caixa primer.";
    private static Caixa C = new Caixa();

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

        C.afegir_producte(nom, quantitat);
        System.out.println("Producte " + nom + " amb quantitat " + quantitat + " de prestatgeria " + id + " afegit correctament");
    }

    public static void testretirarproducte(Scanner scanner){
        System.out.println("Introdueix el nom del producte a retirar:");
        String nom = readLine(scanner);

        System.out.println("Introdueix la quantitat del producte a retirar:");
        int quantitat = Integer.parseInt(readLine(scanner));


        C.retirar_producte(nom, quantitat);
        System.out.println("Producte" + nom + "amb quantitat" + quantitat + "retirat correctament");
    }

    public static void testgetquantitat(Scanner scanner){
        System.out.println("Introdueix el nom del producte del qual vols obtenir la quantitat:");
        String nom = readLine(scanner);


        int quantitat = C.get_quantitat(nom);
        System.out.println("La quantitat del producte" + nom +  "és" + quantitat);
    }

    public static void testpagar(){
        C.pagar();
        System.out.println("Ticket pagat correctament");
    }

    public static boolean commands(String command, Scanner scanner) {
        switch(command) {
            case CREAR_CAIXA:
                testcrearcaixa();
                break;
            case AFEGIR_PRODUCTE:
                if(C == null) {
                    System.out.println(NOHIHACAIXA);
                } else {
                testafegirproducte(scanner);
                }
                break;
            case RETIRAR_PRODUCTE:
                if(C == null) {
                    System.out.println(NOHIHACAIXA);
                } else {
                testretirarproducte(scanner);
                }
                break;
            case GET_QUANTITAT:
                if(C == null) {
                    System.out.println(NOHIHACAIXA);
                } else {
               testgetquantitat(scanner);
                }
                break;
            case PRINTTICKET:
                if(C == null) {
                    System.out.println(NOHIHACAIXA);
                } else {
                //
                }
                break;
            case PRINTTICKETPERPRESTATGERIES:
                if(C == null) {
                    System.out.println(NOHIHACAIXA);
                }
                else {
                    //
                }
                break;
            case PAGAR:
                if(C == null) {
                    System.out.println(NOHIHACAIXA);
                }
                else{
                    testpagar();
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