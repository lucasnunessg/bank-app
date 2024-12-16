package vestido_bank.VestidoBank.Service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vestido_bank.VestidoBank.Entity.Account;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaCorrente;
import vestido_bank.VestidoBank.Exceptions.ClientDuplicateException;
import vestido_bank.VestidoBank.Exceptions.ClientNotFoundException;
import vestido_bank.VestidoBank.Exceptions.EmailDuplicateException;
import vestido_bank.VestidoBank.Exceptions.NameOrEmailDuplicateException;
import vestido_bank.VestidoBank.Repository.ClientRepository;
import java.util.List;
import vestido_bank.VestidoBank.Repository.ContaCorrenteRepository;

@Service
public class ClientService {

  ClientRepository clientRepository;
  ContaCorrenteRepository contaCorrenteRepository;

  @Autowired
  public ClientService(ClientRepository clientRepository,
      ContaCorrenteRepository contaCorrenteRepository) {

    this.clientRepository = clientRepository;
    this.contaCorrenteRepository = contaCorrenteRepository;
  }

  public List<Client> getAllClients() {
    return clientRepository.findAll();
  }

  public Client createClient(Client client) {

    if (clientRepository.existsByEmailOrName(client.getEmail(), client.getName())) {
      throw new NameOrEmailDuplicateException("Já utilizado");
    }

    //  if(clientRepository.existsByemail(client.getEmail())) {
    //  throw new EmailDuplicateException("E-mail já utilizado.");
    // }

    // if(clientRepository.existsByName(client.getName())){
    //   throw new ClientDuplicateException("Cliente já existe.");
    // }
    return clientRepository.save(client);
  }

  public Client getById(Long id) {
    Optional<Client> clientId = clientRepository.findById(id);
    if (clientId.isEmpty()) {
      throw new ClientNotFoundException("Cliente não encontrado.");
    }
    return clientId.get();
  }

  public Client deleteById(Long id) {
    Client client = getById(id);

    clientRepository.deleteById(id);

    return client;
  }

  public Client updateClient(Long id, Client client) {
    Client clientDb = getById(id);

    clientDb.setAddress(client.getAddress());
    clientDb.setContact(client.getContact());
    clientDb.setCpf(client.getCpf());
    clientDb.setName(client.getName());

    return clientRepository.save(clientDb);
  }

  public ContaCorrente createContaCorrenteClient(Long clientId, ContaCorrente contaCorrente) {
    Client client = getById(clientId);
    contaCorrente.setClient(client);
    return contaCorrenteRepository.save(contaCorrente);
  }

  public List<Account> buscarContas(Long contaId) {
    Client client = clientRepository.findById(contaId)
        .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado!"));

    return client.getAccounts();
  }

}
