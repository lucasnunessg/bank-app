package vestido_bank.VestidoBank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaCorrente;
import vestido_bank.VestidoBank.Entity.ContaPoupanca;
import vestido_bank.VestidoBank.Entity.CreditCard;
import vestido_bank.VestidoBank.Entity.Transaction;
import vestido_bank.VestidoBank.Repository.ClientRepository;
import vestido_bank.VestidoBank.Repository.ContaCorrenteRepository;
import vestido_bank.VestidoBank.Repository.ContaPoupancaRepository;
import vestido_bank.VestidoBank.Repository.CreditCardRepository;
import vestido_bank.VestidoBank.Repository.TransactionsRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

  private final ContaPoupancaRepository contaPoupancaRepository;
  private final ContaCorrenteRepository contaCorrenteRepository;
  private final TransactionsRepository transactionsRepository;
  private final ClientRepository clientRepository;
  private final CreditCardRepository creditCardRepository;
  private final PasswordEncoder passwordEncoder;
  private final Random random = new Random();

  public DatabaseSeeder(ContaPoupancaRepository contaPoupancaRepository,
      ContaCorrenteRepository contaCorrenteRepository,
      TransactionsRepository transactionsRepository,
      ClientRepository clientRepository,
      CreditCardRepository creditCardRepository,
      PasswordEncoder passwordEncoder) {
    this.contaPoupancaRepository = contaPoupancaRepository;
    this.contaCorrenteRepository = contaCorrenteRepository;
    this.transactionsRepository = transactionsRepository;
    this.clientRepository = clientRepository;
    this.creditCardRepository = creditCardRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void run(String... args) throws Exception {
    // Cria os clientes
    List<Client> clients = seedClient();

    // Cria as contas correntes e poupanças para cada cliente
    List<ContaCorrente> contasCorrentes = seedContaCorrente(clients);
    List<ContaPoupanca> contasPoupancas = seedContaPoupanca(clients);

    // Cria cartões de crédito para cada cliente
    List<CreditCard> creditCards = seedCreditCards(clients);

    // Cria transações entre as contas e com cartões de crédito
    List<Transaction> transactions = seedTransaction(contasCorrentes, contasPoupancas, creditCards);
  }

  private List<Client> seedClient() {
    List<Client> clients = new ArrayList<>();

    clients.add(
        new Client("Lucas Pacheco Nunes", "04294004043", "5599999999", "R. Acioli Vaz de Andrade",
            "lucasnunespacheco@gmail.com", passwordEncoder.encode("12345678"))
    );

    clients.add(
        new Client("Lucas Pacheco Nunessss", "04294004043", "5599999999",
            "R. Acioli Vaz de Andrade",
            "lucasnunespacheco2@gmail.com", passwordEncoder.encode("12345678"))
    );

    clients.add(
        new Client("Maria Silva", "12345678901", "5512345678", "Av. Paulista, 1000",
            "mariasilva@gmail.com", passwordEncoder.encode("senha123"))
    );

    clients.add(
        new Client("João Oliveira", "98765432109", "5598765432", "R. das Flores, 45",
            "joaooliveira@hotmail.com", passwordEncoder.encode("joao1234"))
    );

    clients.add(
        new Client("Ana Costa", "45678912345", "5533334444", "R. do Sol, 12",
            "anacosta@yahoo.com", passwordEncoder.encode("ana5678"))
    );

    clients.add(
        new Client("Pedro Santos", "32165498700", "5588889999", "Av. Brasil, 500",
            "pedrosantos@gmail.com", passwordEncoder.encode("pedro2023"))
    );

    clients.add(
        new Client("Carla Lima", "65412398732", "5577778888", "R. das Palmeiras, 33",
            "carlalima@outlook.com", passwordEncoder.encode("carla123"))
    );

    return clientRepository.saveAll(clients);
  }

  private List<ContaCorrente> seedContaCorrente(List<Client> clients) {
    List<ContaCorrente> contasCorrentes = new ArrayList<>();

    // Datas de criação simuladas para cada conta corrente
    List<LocalDateTime> datasCriacao = List.of(
        LocalDateTime.of(2022, 1, 1, 0, 0), // 1º de janeiro de 2022
        LocalDateTime.of(2022, 6, 15, 0, 0), // 15 de junho de 2022
        LocalDateTime.of(2023, 3, 10, 0, 0), // 10 de março de 2023
        LocalDateTime.of(2021, 12, 25, 0, 0), // 25 de dezembro de 2021
        LocalDateTime.of(2023, 7, 1, 0, 0), // 1º de julho de 2023
        LocalDateTime.of(2020, 5, 20, 0, 0), // 20 de maio de 2020
        LocalDateTime.of(2023, 9, 5, 0, 0) // 5 de setembro de 2023
    );

    // Cria uma conta corrente para cada cliente com saldo inicial de 5000
    for (int i = 0; i < clients.size(); i++) {
      Client client = clients.get(i);
      LocalDateTime dataCriacao = datasCriacao.get(i);

      ContaCorrente contaCorrente = new ContaCorrente(
          5000.0f, // Saldo inicial
          1000.0f, // Limite
          dataCriacao, // Data de criação específica
          client
      );
      contasCorrentes.add(contaCorrente);
    }

    return contaCorrenteRepository.saveAll(contasCorrentes);
  }

  private List<ContaPoupanca> seedContaPoupanca(List<Client> clients) {
    List<ContaPoupanca> contasPoupancas = new ArrayList<>();

    // Datas de criação simuladas para cada conta poupança
    List<LocalDateTime> datasCriacao = List.of(
        LocalDateTime.of(2022, 1, 1, 0, 0), // 1º de janeiro de 2022
        LocalDateTime.of(2022, 6, 15, 0, 0), // 15 de junho de 2022
        LocalDateTime.of(2023, 3, 10, 0, 0), // 10 de março de 2023
        LocalDateTime.of(2021, 12, 25, 0, 0), // 25 de dezembro de 2021
        LocalDateTime.of(2023, 7, 1, 0, 0), // 1º de julho de 2023
        LocalDateTime.of(2020, 5, 20, 0, 0), // 20 de maio de 2020
        LocalDateTime.of(2023, 9, 5, 0, 0) // 5 de setembro de 2023
    );

    // Cria uma conta poupança para cada cliente com saldo inicial de 5000
    for (int i = 0; i < clients.size(); i++) {
      Client client = clients.get(i);
      LocalDateTime dataCriacao = datasCriacao.get(i);

      ContaPoupanca contaPoupanca = new ContaPoupanca(
          5000.0f, // Saldo inicial
          0.5f, // Rendimento mensal (0,5%)
          dataCriacao, // Data de criação específica
          client
      );
      contaPoupanca.aplicarRendimento();
      contasPoupancas.add(contaPoupanca);
    }

    return contaPoupancaRepository.saveAll(contasPoupancas);
  }

  private List<CreditCard> seedCreditCards(List<Client> clients) {
    List<CreditCard> creditCards = new ArrayList<>();

    for (Client client : clients) {
      BigDecimal faturaAtual = BigDecimal.valueOf(random.nextDouble() * 1000);
      BigDecimal limite = BigDecimal.valueOf(2000 + random.nextDouble() * 3000);
      LocalDate dataVencimento = LocalDate.now().plusMonths(1);

      CreditCard creditCard = new CreditCard(
          limite,
          faturaAtual,
          dataVencimento,
          "active",
          client
      );
      creditCards.add(creditCard);
    }

    return creditCardRepository.saveAll(creditCards);
  }

  private List<Transaction> seedTransaction(List<ContaCorrente> contasCorrentes,
      List<ContaPoupanca> contasPoupancas,
      List<CreditCard> creditCards) {
    List<Transaction> transactions = new ArrayList<>();

    // Datas de transação simuladas
    List<LocalDateTime> datasTransacao = List.of(
        LocalDateTime.of(2023, 1, 15, 10, 0), // 15 de janeiro de 2023
        LocalDateTime.of(2023, 2, 20, 14, 30), // 20 de fevereiro de 2023
        LocalDateTime.of(2023, 3, 25, 9, 15), // 25 de março de 2023
        LocalDateTime.of(2023, 4, 10, 16, 45), // 10 de abril de 2023
        LocalDateTime.of(2023, 5, 5, 11, 0), // 5 de maio de 2023
        LocalDateTime.of(2023, 6, 30, 13, 20), // 30 de junho de 2023
        LocalDateTime.of(2023, 7, 15, 8, 0) // 15 de julho de 2023
    );

    // Cria transações entre contas correntes e poupanças de diferentes clientes
    for (int i = 0; i < contasCorrentes.size(); i++) {
      ContaCorrente contaCorrenteOrigem = contasCorrentes.get(i);
      ContaPoupanca contaPoupancaOrigem = contasPoupancas.get(i);
      CreditCard creditCard = creditCards.get(i);

      // O índice do cliente de destino é o próximo cliente na lista (circular)
      int j = (i + 1) % contasCorrentes.size();
      ContaCorrente contaCorrenteDestino = contasCorrentes.get(j);
      ContaPoupanca contaPoupancaDestino = contasPoupancas.get(j);

      // Transação da conta corrente do cliente i para a conta corrente do cliente j
      Transaction transaction1 = new Transaction(
          contaCorrenteOrigem.getClient(), // Cliente associado à transação (origem)
          contaCorrenteOrigem, // Conta corrente como origem
          contaCorrenteDestino, // Conta corrente como destino
          null, // Cartão de crédito como origem (não se aplica)
          null, // Cartão de crédito como destino (não se aplica)
          null, // Conta poupança como destino (não se aplica)
          null, // Conta poupança como origem (não se aplica)
          100.0f, // Valor da transação
          datasTransacao.get(i), // Data/hora específica
          "Transferência para outro cliente (corrente)", // Descrição
          contaCorrenteOrigem.getSaldo() - 100.0f // Saldo restante na conta de origem
      );
      transactions.add(transaction1);

      // Transação da conta poupança do cliente i para a conta poupança do cliente j
      Transaction transaction2 = new Transaction(
          contaPoupancaOrigem.getClient(), // Cliente associado à transação (origem)
          null, // Conta corrente como origem (não se aplica)
          null, // Conta corrente como destino (não se aplica)
          null, // Cartão de crédito como origem (não se aplica)
          null, // Cartão de crédito como destino (não se aplica)
          contaPoupancaDestino, // Conta poupança como destino
          contaPoupancaOrigem, // Conta poupança como origem
          50.0f, // Valor da transação
          datasTransacao.get(i).plusDays(1), // Data/hora específica (1 dia após a primeira transação)
          "Transferência para outro cliente (poupança)", // Descrição
          contaPoupancaOrigem.getSaldo() - 50.0f // Saldo restante na conta de origem
      );
      transactions.add(transaction2);

      // Transações fictícias de compras com cartão de crédito
      List<String> estabelecimentos = List.of(
          "Supermercado Preço Bom",
          "Restaurante Sabor da Casa",
          "Loja de Eletrônicos TechWorld",
          "Posto de Gasolina Shell",
          "Livraria Leitura",
          "Cinema Cineplex",
          "Loja de Roupas FashionStyle"
      );

      for (int k = 0; k < estabelecimentos.size(); k++) {
        String estabelecimento = estabelecimentos.get(k);
        float valorCompra = (float) (random.nextDouble() * 200 + 10); // Valor entre 10 e 210
        LocalDateTime dataCompra = datasTransacao.get(i).plusHours(k + 2); // Horários diferentes

        Transaction transactionCompra = new Transaction(
            creditCard.getClient(), // Cliente associado à transação
            null, // Conta corrente como origem (não se aplica)
            null, // Conta corrente como destino (não se aplica)
            creditCard, // Cartão de crédito como origem
            null, // Cartão de crédito como destino (não se aplica)
            null, // Conta poupança como destino (não se aplica)
            null, // Conta poupança como origem (não se aplica)
            valorCompra, // Valor da compra
            dataCompra, // Data/hora específica
            "Compra no " + estabelecimento, // Descrição
            creditCard.getFaturaAtual().floatValue() // Saldo restante na fatura
        );
        transactions.add(transactionCompra);

        // Atualiza a fatura do cartão de crédito
        creditCard.setFaturaAtual(creditCard.getFaturaAtual().add(BigDecimal.valueOf(valorCompra)));
      }
    }

    // Salva todas as transações no banco de dados
    return transactionsRepository.saveAll(transactions);
  }
}