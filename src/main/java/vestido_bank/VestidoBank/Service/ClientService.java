package vestido_bank.VestidoBank.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaCorrente;
import vestido_bank.VestidoBank.Exceptions.ClientNotFoundException;

import vestido_bank.VestidoBank.Repository.ClientRepository;
import java.util.List;
import vestido_bank.VestidoBank.Repository.ContaCorrenteRepository;

@Service
public class ClientService implements UserDetailsService {


  ClientRepository clientRepository;
  ContaCorrenteRepository contaCorrenteRepository;

  @Autowired
  public ClientService(ClientRepository clientRepository,
      ContaCorrenteRepository contaCorrenteRepository) {

    this.clientRepository = clientRepository;
    this.contaCorrenteRepository = contaCorrenteRepository;
  }

  public ClientService() {
  }

  public List<Client> getAllClients() {
    return clientRepository.findAll();
  }

  public List<Client> getAllClientsWithoutMe(Long id) {
    List<Client> allClients = clientRepository.findAll();
    return allClients.stream()
        .filter(client -> !client.getId().equals(id)) //filtra pra excluir o proprio id
        .collect(Collectors.toList()); //converte de novo p lista
  }

  public Optional <Client> findByusername(String name) {
    Optional<Client> client = clientRepository.findByName(name);
    if(client.isEmpty()) {
      throw new ClientNotFoundException("Não encontrado");
    }

    return client;
  }

  public Client findByEmail(String email) {
    Client client = clientRepository.findByEmail(email);
        if(client == null) {
          throw new ClientNotFoundException("Não encontrado");
        }

        return client;
  }

  public Client createClient(Client client) {

    String hashedpassword = new BCryptPasswordEncoder()
        .encode(client.getPassword());

    client.setPassword(hashedpassword);
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

  public Client updatePassword(Long id, String newPassword) {
    Client client1 = getById(id);

    String hashedpassword = new BCryptPasswordEncoder()
        .encode(newPassword);

    client1.setPassword(hashedpassword);

    return clientRepository.save(client1);
  }


  public ContaCorrente createContaCorrenteClient(Long clientId, ContaCorrente contaCorrente) {
    Client client = getById(clientId);
    contaCorrente.setClient(client);
    return contaCorrenteRepository.save(contaCorrente);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws ClientNotFoundException {
    Client client = clientRepository.findByEmail(email);
    if (client == null) {
      throw new ClientNotFoundException("Não encontrado");
    }

    return client;
  }

  public void savedResetToken(Long clientId, String token) {
    Client client = getById(clientId);
    client.setResetToken(token);
    client.setTokenExpirity(LocalDateTime.now().plusHours(1));
    clientRepository.save(client);
  }

  public Client validateResetToken(String token) {
    Optional<Client> optionalPerson = clientRepository.findByResetToken(token);
    if (optionalPerson.isEmpty() || optionalPerson.get().getTokenExpirity().isBefore(LocalDateTime.now())) {
      throw new RuntimeException("Token inválido ou expirado.");
    }
    return optionalPerson.get();
  }



}
