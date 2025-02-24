package vestido_bank.VestidoBank.Service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.stereotype.Service;
import vestido_bank.VestidoBank.Controller.Dto.ResponseAccountSaldo;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaCorrente;
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
