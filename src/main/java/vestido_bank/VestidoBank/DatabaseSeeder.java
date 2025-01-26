package vestido_bank.VestidoBank;

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
    List<ContaCorrente> contaCorrente = seedProducts();
    List<ContaPoupanca> contaPoupanca = seedProducts();
    List<Transaction> transactions = seedProduct();
    List<Client> clients = seedClient();
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

  private List<ContaCorrente> seedContaCorrente() {

  }
}
