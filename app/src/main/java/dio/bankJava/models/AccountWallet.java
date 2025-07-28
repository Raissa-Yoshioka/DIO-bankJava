package dio.bankJava.models;

import static dio.bankJava.models.AccountType.ACCOUNT;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class AccountWallet extends Wallet {

    private final List<String> pix;
    private final List<InvestmentWallet> investmentsWallets = new ArrayList<>();

    public void addMoney(final long amount, final String description) {
        var money = generateMoney(amount, description);
        this.moneyList.addAll(money);
    }

    public AccountWallet(final long amount, final List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
        addMoney(amount, "Valor da criação da conta");
    }

    public List<InvestmentWallet> getInvestmentsWallets() {
        return this.investmentsWallets;
    }

    @Override
    public String toString() {
        return "AccountWallet{" + "Service = " + getAccountType() +
                "; Funds = R$" + getBalance() / 100 + "," + getBalance() % 100 +
                "; Pix = " + pix + '}';
    }
}
