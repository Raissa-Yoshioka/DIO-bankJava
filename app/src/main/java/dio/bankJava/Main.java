package dio.bankJava;

import java.util.Arrays;
import java.util.Scanner;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

import dio.bankJava.exceptions.*;
import dio.bankJava.repository.AccountRepository;
import dio.bankJava.repository.InvestmentRepository;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    private final static AccountRepository accountRepository = new AccountRepository();
    private final static InvestmentRepository investmentRepository = new InvestmentRepository();

    public static void main(String[] args) {
        System.out.println("        Bem-vindo ao DIO BankJava   ");
        System.out.println("------------------------------------------");
        do {
            System.out.println("  ");
            System.out.println("Selecione a operação desejada:");
            System.out.println("1  - Criar uma conta");
            System.out.println("2  - Criar um investimento");
            System.out.println("3  - Criar uma carteira de investimento");
            System.out.println("4  - Depositar na conta");
            System.out.println("5  - Sacar da conta");
            System.out.println("6  - Realizar uma transferência entre contas");
            System.out.println("7  - Investir");
            System.out.println("8  - Sacar investimento");
            System.out.println("9  - Atualizar investimento");
            System.out.println("10 - Listar contas");
            System.out.println("11 - Listar investimentos");
            System.out.println("12 - Listar carteiras de investimento");
            System.out.println("13 - Mostrar Extrato de Histórico");
            System.out.println("14 - Sair");
            System.out.println(" ");

            var option = scanner.nextInt();
            switch (option) {
                case 1 -> createAccount();
                case 2 -> createInvestment();
                case 3 -> createWalletInvestment();
                case 4 -> deposit();
                case 5 -> withdraw();
                case 6 -> transferToAccount();
                case 7 -> increaseInvestment();
                case 8 -> withdrawInvestment();
                case 9 -> {
                    investmentRepository.updateAmount();
                    System.out.println("Investimentos reajustados");
                }
                case 10 -> accountRepository.list().forEach(System.out::println);
                case 11 -> investmentRepository.list().forEach(System.out::println); 
                case 12 -> investmentRepository.listWallets().forEach(System.out::println);
                case 13 -> checkHistory();
                case 14 -> System.exit(0);
                default -> System.out.println("Opção inválida");
            }
        } while(true);
    }

    private static void createAccount() {
        System.out.println("Informe as chaves pix, separadas por ';'");
        var pix = Arrays.stream(scanner.next().split(";")).toList();

        System.out.println("Informe o valor inicial do depósito (apenas números):");
        var amount = scanner.nextLong();

        var wallet = accountRepository.create(pix, amount);
        System.out.println("Conta criada: " + wallet);
    }

    private static void createInvestment() {
        System.out.println("Informe a taxa do investimento");
        var tax = scanner.nextInt();

        System.out.println("Informe o valor inicial do depósito");
        var initialFunds = scanner.nextLong();
        var investment = investmentRepository.create(tax, initialFunds);
        System.out.println("Investimento criado: " + investment);
    }

    private static void createWalletInvestment() {
        System.out.println("Informe a chave pix da conta: ");
        var pix = scanner.next();
        var account = accountRepository.findByPix(pix);

        System.out.println("Informe o identificador do investimento");
        var investmentId = scanner.nextInt();
        
        var investmentWallet = investmentRepository.initInvestment(account, investmentId);
        System.out.println("Carteira de investimento criada: " + investmentWallet);
    }

    private static void deposit() {
        System.out.println("Informe a chave pix da conta para depósito:");
        var pix = scanner.next();

        System.out.println("Informe o valor que será depositado:");
        var amount = scanner.nextLong();

        try {
            accountRepository.deposit(pix, amount);
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void withdraw() {
        System.out.println("Informe a chave pix da conta para saque");
        var pix = scanner.next();

        System.out.println("Informe o valor a ser sacado");
        var amount = scanner.nextLong();
        try {
            accountRepository.withdraw(pix, amount);
        } catch (InsufficientBalanceException | AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void transferToAccount() {
        System.out.println("Informe a chave pix da conta de origem:");
        var source = scanner.next();

        System.out.println("Informe a chave pix da conta de destino:");
        var target = scanner.next();

        System.out.println("Informe o valor da transferência:");
        var amount = scanner.nextLong();

        try {
            accountRepository.transferMoney(source, target, amount);
        } catch (InsufficientBalanceException | AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void increaseInvestment() {
        System.out.println("Informe a chave pix da conta para investimento:");
        var pix = scanner.next();

        System.out.println("Informe o valor que será investido:");
        var amount = scanner.nextLong();
        try {
            investmentRepository.deposit(pix, amount);
        } catch (WalletNotFoundException | AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void withdrawInvestment() {
        System.out.println("Informe a chave pix da conta para resgate do investimento:");
        var pix = scanner.next();

        System.out.println("Informe o valor a ser sacado:");
        var amount = scanner.nextLong();
        try {
            investmentRepository.withdraw(pix, amount);
        } catch (InsufficientBalanceException | AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void checkHistory() {
        System.out.println("Informe a chave pix da conta para verificar o extrato:");
        var pix = scanner.next();
        try {
            var sortedHistory = accountRepository.getHistory(pix);
            System.out.println("Extrato de: '" + pix + "'':");
            sortedHistory.forEach((k, v) -> {
                System.out.println(k.format(ISO_DATE_TIME));
                System.out.println(v.getFirst().transactionID());
                System.out.println(v.getFirst().description());
                System.out.println("R$" + (v.size() / 100) + "," + (v.size() % 100));
            });
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
