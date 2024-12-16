package vestido_bank.VestidoBank.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaCorrente;
import vestido_bank.VestidoBank.Exceptions.ContaCorrentNotFoundException;
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

  public ContaCorrente depositar(Long contaId, float valor) {
    ContaCorrente contaCorrente = contaCorrenteRepository.findById(contaId)
        .orElseThrow(() -> new IllegalArgumentException("Não foi possível encontrar a conta!"));
    contaCorrente.depositar(valor);

    return contaCorrenteRepository.save(contaCorrente);
  }

  public ContaCorrente sacarSaldo(Long contaId, float valor) {
    ContaCorrente contaCorrente = contaCorrenteRepository.findById(contaId)
        .orElseThrow(() -> new IllegalArgumentException("Não foi possível sacar, tente novamente"));
    contaCorrente.sacar(valor);
    return contaCorrenteRepository.save(contaCorrente);
  }


}
