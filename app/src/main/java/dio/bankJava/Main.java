package dio.bankJava;

import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("        Bem-vindo ao DIO BankJava   ");
        System.out.println("------------------------------------------");

        do {
            System.out.println("Selecione a operação desejada:");
            System.out.println("1 - Criar uma conta");
            System.out.println("2 - Criar um investimento");
            System.out.println("3 - Criar uma carteira de investimento");
            System.out.println("4 - Depositar na conta");
            System.out.println("5 - Sacar da conta");
            System.out.println("6 - Realizar uma Transferência entre contas");
            System.out.println("7 - Investir");
            System.out.println("8 - Sacar investimento");
            System.out.println("9 - Atualizar investimento");
            System.out.println("10 - Listar contas");
            System.out.println("11 - Listar investimentos");
            System.out.println("12 - Listar carteiras de investimento");
            System.out.println("13 - Mostrar Extrato de Histórico");
            System.out.println("14 - Sair");
        } while(true);
    }
}
