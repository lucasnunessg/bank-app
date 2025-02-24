package vestido_bank.VestidoBank.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.stereotype.Service;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaPoupanca;
import vestido_bank.VestidoBank.Exceptions.ClientNotFoundException;
import vestido_bank.VestidoBank.Exceptions.ContaPoupancaNotFoundException;
import vestido_bank.VestidoBank.Exceptions.DepositInvalid;
import vestido_bank.VestidoBank.Exceptions.SakeInvalid;
import vestido_bank.VestidoBank.Repository.ClientRepository;
import vestido_bank.VestidoBank.Repository.ContaPoupancaRepository;

@Service
public class ContaPoupancaService {

  ContaPoupancaRepository contaPoupancaRepository;
  ClientRepository clientRepository;

  @Autowired
  public ContaPoupancaService(ContaPoupancaRepository contaPoupancaRepository,
      ClientRepository clientRepository) {
    this.contaPoupancaRepository = contaPoupancaRepository;
    this.clientRepository = clientRepository;
  }


  public List<ContaPoupanca> getAllPoupancas() {
    return contaPoupancaRepository.findAll();
  }


  public ContaPoupanca getPoupancaById(Long id) {
    Optional<ContaPoupanca> poupanca = contaPoupancaRepository.findById(id);
    if (poupanca.isEmpty()) {
      throw new ContaPoupancaNotFoundException("Não encontrada");
    }
    return poupanca.get();
  }


  public ContaPoupanca createContaPoupanca(Long clientId, ContaPoupanca contaPoupanca) {
    Optional<Client> client = clientRepository.findById(clientId);
    if (client.isEmpty()) {
      throw new ClientNotFoundException("Não encontrado");
    }

    contaPoupanca.setClient(
        client.get()); //precisa passar o .get aqui pq o optional esta so no findById, nao na resposta
    return contaPoupancaRepository.save(contaPoupanca);
  }

  public ContaPoupanca deleteRelationship(Long clientId, ContaPoupanca contaPoupanca) {
    Optional<Client> client = clientRepository.findById(clientId);
    if (client.isEmpty()) {
      throw new ClientNotFoundException("Não encontrado");
    }

    contaPoupanca.setClient(null);
    return contaPoupancaRepository.save(contaPoupanca);
  }

  public ContaPoupanca rendimentoConta(Long contaId) {
    ContaPoupanca contaPoupanca = contaPoupancaRepository.findById(contaId)
        .orElseThrow(() -> new ContaPoupancaNotFoundException("Conta não encontrada!"));

    contaPoupanca.aplicarRendimento();
    return contaPoupancaRepository.save(contaPoupanca);

  }

  public BigDecimal getRendimentoTotal(Long contaId) {
    ContaPoupanca contaPoupanca = contaPoupancaRepository.findById(contaId)
        .orElseThrow(() -> new ContaPoupancaNotFoundException("Conta não encontrada"));

    // Saldo inicial da conta
    BigDecimal saldoInicial = new BigDecimal("5000.0");

    // Taxa de rendimento mensal (em decimal)
    BigDecimal rendimentoMensal = new BigDecimal("0.005");

    // Data de criação da conta
    LocalDateTime dataCriacao = contaPoupanca.getData_criacao();

    // Data atual
    LocalDateTime dataAtual = LocalDateTime.now();

    // Calcula o número de meses desde a criação da conta
    long mesesDecorridos = Duration.between(dataCriacao, dataAtual).toDays() / 30;

    // Calcula o rendimento total usando juros compostos
    BigDecimal fatorRendimento = BigDecimal.ONE.add(rendimentoMensal);
    BigDecimal rendimentoTotal = saldoInicial.multiply(fatorRendimento.pow((int) mesesDecorridos)).subtract(saldoInicial);

    // Arredonda o resultado para 2 casas decimais
    rendimentoTotal = rendimentoTotal.setScale(2, RoundingMode.HALF_UP);

    return rendimentoTotal;
  }


  public ContaPoupanca depositar(Long contaId, float valor) {
    ContaPoupanca contaPoupanca = contaPoupancaRepository.findById(contaId)
        .orElseThrow(() -> new ContaPoupancaNotFoundException("Conta não encontrada!"));

    if (valor < 0) {
      throw new DepositInvalid("Não é possível depositar valor menor que zero.");
    }

    contaPoupanca.depositar(valor);
    return contaPoupancaRepository.save(contaPoupanca);
  }

  public ContaPoupanca sacar(Long contaId, float valor) {
    ContaPoupanca contaPoupanca = contaPoupancaRepository.findById(contaId)
        .orElseThrow(() -> new ContaPoupancaNotFoundException("Não foi encontrada!"));

    if (valor > contaPoupanca.getSaldo()) {
      throw new SakeInvalid("Não é possível sacar valor acima do saldo");
    }

    if (valor <= 0) {
      throw new SakeInvalid("Não é possível sacar 0");
    }

    contaPoupanca.saque(valor);
    return contaPoupancaRepository.save(contaPoupanca);
  }

  public ContaPoupanca salvar(ContaPoupanca contaPoupanca) {
    return contaPoupancaRepository.save(contaPoupanca); //fiz pra poder salvar apos deposito/saque
  }
}
