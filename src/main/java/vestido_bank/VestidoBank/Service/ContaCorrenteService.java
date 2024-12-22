package vestido_bank.VestidoBank.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaCorrente;
import vestido_bank.VestidoBank.Exceptions.ContaCorrentNotFoundException;
import vestido_bank.VestidoBank.Exceptions.DepositInvalid;
import vestido_bank.VestidoBank.Exceptions.SakeInvalid;
import vestido_bank.VestidoBank.Repository.ClientRepository;
import vestido_bank.VestidoBank.Repository.ContaCorrenteRepository;
import java.util.List;

@Service
public class ContaCorrenteService {

  ContaCorrenteRepository contaCorrenteRepository;
  ClientRepository clientRepository;

  @Autowired
  public ContaCorrenteService(ContaCorrenteRepository contaCorrenteRepository,
      ClientRepository clientRepository) {
    this.contaCorrenteRepository = contaCorrenteRepository;
    this.clientRepository = clientRepository;
  }

  public List<ContaCorrente> getAllContasCorrentes() {
    return contaCorrenteRepository.findAll();

  }

  public ContaCorrente getContaCorrenteById(Long id) {
    Optional<ContaCorrente> contaCorrente = contaCorrenteRepository.findById(id);
    if (contaCorrente.isEmpty()) {
      throw new ContaCorrentNotFoundException("Não encontrado.");
    }
    return contaCorrente.get();
  }

  public ContaCorrente deleteByIdConta(Long id) {
    ContaCorrente contaCorrente = getContaCorrenteById(id);

    contaCorrenteRepository.delete(contaCorrente);

    return contaCorrente;
  }

  public ContaCorrente criarContaCorrente(ContaCorrente contaCorrente) {

    return contaCorrenteRepository.save(contaCorrente);
  }

  public ContaCorrente depositarSaldo(Long contaId, float valor) {
    ContaCorrente contaCorrente = contaCorrenteRepository.findById(contaId)
        .orElseThrow(() -> new IllegalArgumentException("Não foi possível encontrar a conta!"));

    if (valor <= 0) {
      throw new IllegalArgumentException("O valor do depósito deve ser positivo");
    }

    // Verifica se o saldo após o depósito ultrapassa o limite
    if (contaCorrente.getSaldo() + valor > contaCorrente.getLimite()) {
      throw new DepositInvalid(
          "O depósito excede o limite permitido de " + contaCorrente.getLimite());
    }

    contaCorrente.depositar(valor); // Adiciona o valor ao saldo da conta
    return contaCorrenteRepository.save(contaCorrente);
  }


  public ContaCorrente sacarSaldo(Long contaId, float valor) {
    ContaCorrente contaCorrente = contaCorrenteRepository.findById(contaId)
        .orElseThrow(() -> new IllegalArgumentException("Não foi possível sacar, tente novamente"));

    if (valor <= 0) {
      throw new SakeInvalid("O valor do saque deve ser positivo");
    }

    if (valor > contaCorrente.getSaldo()) {
      throw new SakeInvalid("Valor de saque não pode ser menor que o saldo");
    }
    contaCorrente.sacar(valor);

    return contaCorrenteRepository.save(contaCorrente);
  }

  public ContaCorrente salvar(ContaCorrente contaCorrente) {
    return contaCorrenteRepository.save(contaCorrente); //fiz pra poder salvar apos deposito/saque
  }


}
