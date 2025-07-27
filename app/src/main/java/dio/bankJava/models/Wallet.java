package dio.bankJava.models;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import lombok.Getter;

public abstract class Wallet {

    @Getter
    private final AccountType accountType;

    protected final List<Money> moneyList;

    // Iniciando uma carteira
    public Wallet(AccountType accountType) {
        this.accountType = accountType;
        this.moneyList = new ArrayList<>();
    }

    protected List<Money> generateMoney(final long amount, final String description) {
        var history = new MoneyAudit(UUID.randomUUID(), accountType, description, OffsetDateTime.now());
        return Stream.generate(() -> new Money(history)).limit(amount).toList();
    }

    public long getBalance() {
        return moneyList.size();
    }

    public void addMoney(final List<Money> money, final AccountType accountType, final String description) {
        var history = new MoneyAudit(UUID.randomUUID(), accountType, description, OffsetDateTime.now());
        money.forEach(m -> m.addHistory(history));
        this.moneyList.addAll(money);
    }

    public List<Money> reduceMoney(final long amount) {
        List<Money> toRemove = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            toRemove.add(this.moneyList.removeFirst());
        }
        return toRemove;
    }

    public List<MoneyAudit> getFinancialTransactions() {
        return moneyList.stream().flatMap(m -> m.getHistoric().stream()).toList();
    }
}
