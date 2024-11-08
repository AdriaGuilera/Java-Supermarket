package classes;

public class Supermercat {

    private double Saldo;





    public Supermercat(double inicial){
        Saldo = inicial;
    }



    public double getSaldo(){
        return Saldo;
    }
    public void aumentarSaldo(double diners){
        Saldo += diners;
    }
    public void disminuirSaldo(double diners){
        Saldo -= diners;
    }


}
