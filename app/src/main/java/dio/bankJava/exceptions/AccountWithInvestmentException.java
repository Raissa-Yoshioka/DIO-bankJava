package dio.bankJava.exceptions;

// Só é permitido um investimento principal por conta
public class AccountWithInvestmentException extends RuntimeException {

    public AccountWithInvestmentException(String message) {super(message);}
}
