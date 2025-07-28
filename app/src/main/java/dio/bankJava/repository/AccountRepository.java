package dio.bankJava.repository;

import static dio.bankJava.repository.CommonRepository.checkBalanceForTransactions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dio.bankJava.exceptions.*;
import dio.bankJava.models.AccountWallet;
import dio.bankJava.models.MoneyAudit;

public class AccountRepository {

    private final List<AccountWallet> accounts = new ArrayList<>();
    
    public AccountWallet create(final List<String> pix, final long initialBalance) {
        if (!accounts.isEmpty()) {
            var pixInUse = accounts.stream().flatMap(a -> a.getPix().stream()).toList();
            for (var verifyPix : pix) {
                if (pixInUse.contains(verifyPix)) {
                    throw new PixInUseException("O pix '" + verifyPix + "' já está em uso.");
                }
            }
        }

        var newAccount = new AccountWallet(initialBalance, pix);
        accounts.add(newAccount);

        return newAccount;
    }

    public AccountWallet findByPix(final String pix) {
        return accounts.stream().filter(account -> account.getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("A conta com a chave pix '" + pix + "' não existe."));
    }

    public void deposit(final String pix, final long fundsAmount) {
        var target = findByPix(pix);
        target.addMoney(fundsAmount, "Depósito");
        System.out.println("Depósito realizado com sucesso.");
    }

    public long withdraw(final String pix, final long amount) {
        var source = findByPix(pix);
        checkBalanceForTransactions(source, amount);
        source.reduceMoney(amount);
        System.out.println("Saque realizado com sucesso");
        return amount;
    }

    public void transferMoney(final String sourcePix, final String targetPix, final long amount) {
        var source = findByPix(sourcePix);
        checkBalanceForTransactions(source, amount);
        var target = findByPix(targetPix);
        var message = "Pix enviado de '" + sourcePix + "' para '" + targetPix + "'.";
        target.addMoney(source.reduceMoney(amount), source.getAccountType(), message);
    }

    public List<AccountWallet> list() {
        return this.accounts;
    }

    public Map<LocalDateTime, List<MoneyAudit>> getHistory(String pix) {
        var account = findByPix(pix);
        var accountTransactions = account.getFinancialTransactions().stream();

        var investmentTransactions = account.getInvestmentsWallets().stream()
                .flatMap(w -> w.getFinancialTransactions().stream());
        
        return Stream.concat(accountTransactions, investmentTransactions)
                    .collect(Collectors.groupingBy(t -> t.createdAt().toLocalDateTime(), 
                    TreeMap::new, Collectors.toList()));
    }
}
