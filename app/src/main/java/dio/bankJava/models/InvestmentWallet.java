package dio.bankJava.models;

import static dio.bankJava.models.AccountType.INVESTMENT;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.stream.Stream;

import lombok.Getter;

@Getter
public class InvestmentWallet extends Wallet {

    private final Investment investment;
    private final AccountWallet account;

    public InvestmentWallet(final Investment investment, final AccountWallet account, final long amount) {
        super(INVESTMENT);
        this.investment = investment;
        this.account = account;
        addMoney(account.reduceMoney(amount), getAccountType(), "Investimento");
    }

    public void updateAmount(final long percent) {
        var amount = getBalance() * percent / 100;
        var history = new MoneyAudit(UUID.randomUUID(), getAccountType(), "Rendimentos", OffsetDateTime.now());
        var money = Stream.generate(() -> new Money(history)).limit(amount).toList();
        this.moneyList.addAll(money);
    }

    @Override
    public String toString() {
        return "InvestmentWallet{" + investment + ", Account =" + account +
                ", Service =" + getAccountType() + ", Funds = R$" + getBalance()/100 +
                "," + getBalance() % 100 + "}";
    } 
}
