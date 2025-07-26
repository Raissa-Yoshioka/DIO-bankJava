package dio.bankJava.models;

import java.util.List;

import lombok.Getter;

public abstract class Wallet {

    @Getter
    private final AccountType accountType;

    protected final List<Money> money;

    // Iniciando uma carteira
    public Wallet(AccountType accountType) {
        this.accountType = accountType;
        this.money = new ArrayList<>();
    }
}
