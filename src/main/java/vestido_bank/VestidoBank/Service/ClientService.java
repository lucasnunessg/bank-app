package vestido_bank.VestidoBank.Service;

import java.util.Optional;
import java.util.UUID;
import middlewares.RegexPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.MessageActionType;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaCorrente;
import vestido_bank.VestidoBank.Exceptions.ClientNotFoundException;
import vestido_bank.VestidoBank.Exceptions.CognitoErrorCreateException;
import vestido_bank.VestidoBank.Exceptions.NameOrEmailDuplicateException;
import vestido_bank.VestidoBank.Repository.ClientRepository;
import java.util.List;
import vestido_bank.VestidoBank.Repository.ContaCorrenteRepository;

@Service
public class ClientService implements UserDetailsService {

  @Value("${aws.cognito.userPoolId}")
      private String userPoolId;

  @Value("${aws.cognito.clientId}")
      private String clientId;

  ClientRepository clientRepository;
  ContaCorrenteRepository contaCorrenteRepository;
  private final CognitoIdentityProviderClient cognitoClient;

  @Autowired
  public ClientService(ClientRepository clientRepository,
      ContaCorrenteRepository contaCorrenteRepository, CognitoIdentityProviderClient cognitoClient) {

    this.clientRepository = clientRepository;
    this.contaCorrenteRepository = contaCorrenteRepository;
    this.cognitoClient = cognitoClient;
  }

  public List<Client> getAllClients() {
    return clientRepository.findAll();
  }

  public Client createClient(Client client) {

    String hashedpassword = new BCryptPasswordEncoder()
        .encode(client.getPassword());

    client.setPassword(hashedpassword);

    if (clientRepository.existsByEmailOrName(client.getEmail(), client.getName())) {
      throw new NameOrEmailDuplicateException("Já utilizado");
    }

    //  if(clientRepository.existsByemail(client.getEmail())) {
    //  throw new EmailDuplicateException("E-mail já utilizado.");
    // }

    // if(clientRepository.existsByName(client.getName())){
    //   throw new ClientDuplicateException("Cliente já existe.");
    // }

    Client savedClient = clientRepository.save(client);
    createUserInCognito(client);

    return savedClient;
  }

  public Client createUserInCognito(Client client) {


    AdminCreateUserRequest createUserRequest = AdminCreateUserRequest.builder()
        .userPoolId(userPoolId)
        .username(client.getEmail())
        .userAttributes(
            AttributeType.builder().name("email").value(client.getEmail()).build(),
            AttributeType.builder().name("given_name").value(client.getName()).build(),
            AttributeType.builder().name("custom:contact").value(client.getContact()).build(),
            AttributeType.builder().name("custom:address").value(client.getAddress()).build(),
            AttributeType.builder().name("custom:cpf").value(client.getCpf()).build(),
            AttributeType.builder().name("custom:password").value(client.getPassword()).build()
        )
        .messageAction(MessageActionType.SUPPRESS)
        .build();
    try{
      cognitoClient.adminCreateUser(createUserRequest);
    } catch (CognitoIdentityProviderException e) {
      e.printStackTrace();
      throw new CognitoErrorCreateException("Erro ao criar usuário");
    }
    return client;
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
  @Override
  public UserDetails loadUserByUsername(String email) throws ClientNotFoundException {
    Client client = clientRepository.findByEmail(email);
    if(client == null) {
    throw new ClientNotFoundException("Não encontrado");
    }

    return client;
  }


}
