# DIO bankJava
## Desafio de Projeto 1 do bootcamp NTT DATA da DIO

![Java](https://img.shields.io/badge/Java-21-royalblue?style=for-the-badge&logo=openjdk)
![Gradle](https://img.shields.io/badge/Gradle-8.8-green?style=for-the-badge&logo=gradle)
![Lombok](https://img.shields.io/badge/Lombok-v1.18.38-darkred?style=for-the-badge&logo=projectlombok)

Este projeto é uma aplicação Java orientada a objetos, desenvolvida com o objetivo de consolidar conceitos fundamentais da programação orientada a objetos (POO), como herança, encapsulamento, polimorfismo, abstração e reuso de código. 

---

## Organização do Projeto

- A aplicação simula um sistema bancário básico que oferece as seguistes operações:
    
    * Criação de contas;
    * Depósitos e saques;
    * Transferências via PIX;
    * Criação e gerenciamento de investimentos;
    *  Listagem das contas, contas de investimento e carteiras de investimento ativas;
    * Acompanhamento de histórico de transações.

- O projeto está separado da seguinte forma:

    * `Main.java`: arquivo que lida com o menu da aplicação e processamento das entradas do usuário.
    
    * Pasta `exceptions`:  
        - Classes que lidam com exceções específicas para diferentes cenários, como por exemplo `AccountNotFoundException`, que lida com o caso de uma conta não ser encontrada pela aplicação.
    * Pasta `models`:
        - Classes que contém os modelos de domínio principais da aplicação, como por exemplo `AccountType`, para a definição dos tipos de conta oferecidos pela aplicação.
    * Pasta `repository`:
        - Classes que atuam como camada de acesso de dados na memória, gerenciando as regras de nengócio das operações entre as classes.

---

## Execução do Projeto

### Pré-requisitos

  - JDK 21 ou superior;
  - Gradle

### Passos Iniciais

  1. Clone o repositório:
     ```sh
     git clone https://github.com/Raissa-Yoshioka/DIO-bankJava.git
     ```

  2. Abra o projeto na sua IDE (VS Code, Eclipse ou outras).
  3. Localize o arquivo `Main.java` e o execute.
  4. Ao executar, o seguinte menu aparece no terminal:
     ```sh
             Bem-vindo ao DIO BankJava
      ------------------------------------------
      Selecione a operação desejada:
      1  - Criar uma conta
      2  - Criar um investimento
      3  - Criar uma carteira de investimento
      4  - Depositar na conta
      5  - Sacar da conta
      6  - Realizar uma transferência entre contas
      7  - Investir
      8  - Sacar investimento
      9  - Atualizar investimento
      10 - Listar contas
      11 - Listar investimentos
      12 - Listar carteiras de investimento
      13 - Mostrar Extrato de Histórico
      14 - Sair
      ```
  5. E pronto! Você pode testar as operações e funcionamento da aplicação.
