package vestido_bank.VestidoBank;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaCorrente;
import vestido_bank.VestidoBank.Entity.ContaPoupanca;
import vestido_bank.VestidoBank.Entity.Transaction;
import vestido_bank.VestidoBank.Repository.ClientRepository;
import vestido_bank.VestidoBank.Repository.ContaCorrenteRepository;
import vestido_bank.VestidoBank.Repository.ContaPoupancaRepository;
import vestido_bank.VestidoBank.Repository.TransactionsRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

  private final ContaPoupancaRepository contaPoupancaRepository;
  private final ContaCorrenteRepository contaCorrenteRepository;
  private final TransactionsRepository transactionsRepository;
  private final ClientRepository clientRepository;
  private final PasswordEncoder passwordEncoder;

  public DatabaseSeeder(ContaPoupancaRepository contaPoupancaRepository,
      ContaCorrenteRepository contaCorrenteRepository,
      TransactionsRepository transactionsRepository,
      ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
    this.contaPoupancaRepository = contaPoupancaRepository;
    this.contaCorrenteRepository = contaCorrenteRepository;
    this.transactionsRepository = transactionsRepository;
    this.clientRepository = clientRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void run(String... args) throws Exception {
    // Cria os clientes
    List<Client> clients = seedClient();

    // Cria as contas correntes e poupanças para cada cliente
    List<ContaCorrente> contasCorrentes = seedContaCorrente(clients);
    List<ContaPoupanca> contasPoupancas = seedContaPoupanca(clients);

    // Cria transações entre as contas
    List<Transaction> transactions = seedTransaction(contasCorrentes, contasPoupancas);
  }

  private List<Client> seedClient() {
    List<Client> clients = new ArrayList<>();

    clients.add(
        new Client("Lucas Pacheco Nunes", "04294004043", "5599999999", "R. Acioli Vaz de Andrade",
            "lucasnunespacheco@gmail.com", passwordEncoder.encode("12345678"))
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

    // Cria uma conta corrente para cada cliente com saldo inicial de 5000
    for (Client client : clients) {
      ContaCorrente contaCorrente = new ContaCorrente(
          5000.0f, // Saldo inicial
          1000.0f, // Limite
          LocalDateTime.now().minusDays(clients.indexOf(client) * 2), // Data de criação variável
          client
      );
      contasCorrentes.add(contaCorrente);
    }

    return contaCorrenteRepository.saveAll(contasCorrentes);
  }

  private List<ContaPoupanca> seedContaPoupanca(List<Client> clients) {
    List<ContaPoupanca> contasPoupancas = new ArrayList<>();

    // Cria uma conta poupança para cada cliente com saldo inicial de 5000
    for (Client client : clients) {
      ContaPoupanca contaPoupanca = new ContaPoupanca(
          5000.0f, // Saldo inicial
          0.5f, // Rendimento mensal
          LocalDateTime.now().minusDays(clients.indexOf(client) * 3), // Data de criação variável
          client
      );
      contasPoupancas.add(contaPoupanca);
    }

    return contaPoupancaRepository.saveAll(contasPoupancas);
  }

  private List<Transaction> seedTransaction(List<ContaCorrente> contasCorrentes, List<ContaPoupanca> contasPoupancas) {
    List<Transaction> transactions = new ArrayList<>();

    // Cria transações entre contas correntes e poupanças de diferentes clientes
    for (int i = 0; i < contasCorrentes.size(); i++) {
      ContaCorrente contaCorrenteOrigem = contasCorrentes.get(i);
      ContaPoupanca contaPoupancaOrigem = contasPoupancas.get(i);

      // O índice do cliente de destino é o próximo cliente na lista (circular)
      int j = (i + 1) % contasCorrentes.size();
      ContaCorrente contaCorrenteDestino = contasCorrentes.get(j);
      ContaPoupanca contaPoupancaDestino = contasPoupancas.get(j);

      // Transação da conta corrente do cliente i para a conta corrente do cliente j
      Transaction transaction1 = new Transaction(
          contaCorrenteOrigem.getClient(), // Cliente associado à transação (origem)
          contaCorrenteOrigem, // Conta corrente como origem
          contaCorrenteDestino, // Conta corrente como destino
          null, // Conta poupança como destino (não se aplica)
          null, // Conta poupança como origem (não se aplica)
          100.0f, // Valor da transação
          LocalDateTime.now().minusHours(i * 2), // Data/hora variável
          "Transferência para outro cliente (corrente)", // Descrição
          contaCorrenteOrigem.getSaldo() - 100.0f // Saldo restante na conta de origem
      );
      transactions.add(transaction1);

      // Transação da conta poupança do cliente i para a conta poupança do cliente j
      Transaction transaction2 = new Transaction(
          contaPoupancaOrigem.getClient(), // Cliente associado à transação (origem)
          null, // Conta corrente como origem (não se aplica)
          null, // Conta corrente como destino (não se aplica)
          contaPoupancaDestino, // Conta poupança como destino
          contaPoupancaOrigem, // Conta poupança como origem
          50.0f, // Valor da transação
          LocalDateTime.now().minusHours(i * 3), // Data/hora variável
          "Transferência para outro cliente (poupança)", // Descrição
          contaPoupancaOrigem.getSaldo() - 50.0f // Saldo restante na conta de origem
      );
      transactions.add(transaction2);
    }

    return transactionsRepository.saveAll(transactions);
  }
}