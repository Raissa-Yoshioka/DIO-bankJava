package dio.bankJava.repository;

import lombok.NoArgsConstructor;
import static lombok.AccessLevel.PRIVATE;
import static dio.bankJava.models.AccountType.ACCOUNT;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import dio.bankJava.exceptions.InsufficientBalanceException;
import dio.bankJava.models.*;

@NoArgsConstructor(access = PRIVATE)
public final class CommonRepository {

    public static void checkBalanceForTransactions(final Wallet source, final long amount) {
        if (source.getBalance() < amount) {
            throw new InsufficientBalanceException("Sua conta não possui saldo o suficiente para essa operação.");
        }
    }

    public static List<Money> generateMoney(final UUID transactionID, final long balance, final String description) {
        var history = new MoneyAudit(transactionID, ACCOUNT, description, OffsetDateTime.now());
        return Stream.generate(() -> new Money(history)).limit(balance).toList();
    }
}
