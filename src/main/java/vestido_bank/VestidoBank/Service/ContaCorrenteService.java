package vestido_bank.VestidoBank.Service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vestido_bank.VestidoBank.Entity.ContaCorrente;
import vestido_bank.VestidoBank.Exceptions.ContaCorrentNotFoundException;
import vestido_bank.VestidoBank.Repository.ContaCorrenteRepository;
import java.util.List;

@Service
public class ContaCorrenteService {

  ContaCorrenteRepository contaCorrenteRepository;

  @Autowired
  public ContaCorrenteService(ContaCorrenteRepository contaCorrenteRepository) {
    this.contaCorrenteRepository = contaCorrenteRepository;
  }

  public List<ContaCorrente> getAllContasCorrentes() {
  return contaCorrenteRepository.findAll();

  }

  public ContaCorrente getContaCorrenteById(Long id) {
    Optional<ContaCorrente> contaCorrente = contaCorrenteRepository.findById(id);
    if(contaCorrente.isEmpty()) {
      throw new ContaCorrentNotFoundException("Não encontrado.");
    }
    return contaCorrente.get();
  }

  public ContaCorrente deleteByIdConta(Long id) {
    ContaCorrente contaCorrente = getContaCorrenteById(id);

    contaCorrenteRepository.delete(contaCorrente);

    return contaCorrente;
  }

  public ContaCorrente createContaCorrente(ContaCorrente contaCorrente) {
  return contaCorrenteRepository.save(contaCorrente);
  }

}
