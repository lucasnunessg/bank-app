package vestido_bank.VestidoBank.Service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.stereotype.Service;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaPoupanca;
import vestido_bank.VestidoBank.Exceptions.ClientNotFoundException;
import vestido_bank.VestidoBank.Exceptions.ContaPoupancaNotFoundException;
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
}
