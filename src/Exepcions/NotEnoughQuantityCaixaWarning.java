package Exepcions;

public class NotEnoughQuantityCaixaWarning extends RuntimeException {
    public NotEnoughQuantityCaixaWarning(String id_producte) {
        super("Not enough quantity of product " + id_producte + " in Caixa. Removed all available units.");
    }
}
