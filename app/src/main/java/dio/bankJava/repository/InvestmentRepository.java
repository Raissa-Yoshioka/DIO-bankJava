package dio.bankJava.repository;

import static dio.bankJava.repository.CommonRepository.checkBalanceForTransactions;

import java.util.ArrayList;
import java.util.List;

import dio.bankJava.exceptions.AccountWithInvestmentException;
import dio.bankJava.exceptions.InvestmentNotFoundException;
import dio.bankJava.exceptions.WalletNotFoundException;
import dio.bankJava.models.AccountWallet;
import dio.bankJava.models.Investment;
import dio.bankJava.models.InvestmentWallet;

public class InvestmentRepository {
    
    private long nextId = 0;
    private final List<Investment> investments = new ArrayList<>();
    private final List<InvestmentWallet> wallets = new ArrayList<>();

    public Investment create(final long tax, final long initialBalance) {
        this.nextId++;
        var investment = new Investment(this.nextId, tax, initialBalance);
        investments.add(investment);
        return investment;
    }

    public Investment findById(final long id) {
        return investments.stream().filter(a -> a.id() == id).findFirst()
                .orElseThrow(
                    () -> new InvestmentNotFoundException("O investimento '" + id + "' não foi encontrado.")
                );
    }

    public InvestmentWallet findWalletByAccountPix(final String pix) {
        return wallets.stream()
                .filter(w -> w.getAccount().getPix().contains(pix))
                .findFirst()
                .orElseThrow(
                    () -> new WalletNotFoundException("A carteira não foi encontrada.")
                );
    }

    public InvestmentWallet initInvestment(final AccountWallet account, final long id) {
        if (!wallets.isEmpty()) {
            var accountInUse = wallets.stream().map(InvestmentWallet::getAccount).toList();
            if (accountInUse.contains(account)) {
                throw new AccountWithInvestmentException("A conta '" + account + "' já possui um investimento.");
            }
        }

        var investment = findById(id);
        checkBalanceForTransactions(account, investment.initialFunds());
        var wallet = new InvestmentWallet(investment, account, investment.initialFunds());
        wallets.add(wallet);
        return wallet;
    }

    public InvestmentWallet deposit(final String pix, final long funds) {
        var wallet = findWalletByAccountPix(pix);
        checkBalanceForTransactions(wallet, funds);
        wallet.getAccount().addMoney(wallet.reduceMoney(funds), wallet.getAccountType(), "Depósito de Investimentos");
        return wallet;
    }

    public InvestmentWallet withdraw(final String pix, final long funds) {
        var wallet = findWalletByAccountPix(pix);
        checkBalanceForTransactions(wallet, funds);
        wallet.getAccount().addMoney(wallet.reduceMoney(funds), wallet.getAccountType(), "Saque de Investimentos");

        if (wallet.getBalance() == 0) {
            wallets.remove(wallet);
        }
        return wallet;
    }

    public void updateAmount() {
        wallets.forEach(w -> w.updateAmount(w.getInvestment().tax()));
    }

    public List<InvestmentWallet> listWallets() {
        return this.wallets;
    }

    public List<Investment> list() {
        return this.investments;
    }
}
