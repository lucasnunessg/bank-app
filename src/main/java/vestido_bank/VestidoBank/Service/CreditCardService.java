package vestido_bank.VestidoBank.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.CreditCard;
import vestido_bank.VestidoBank.Exceptions.ClientNotFoundException;
import vestido_bank.VestidoBank.Exceptions.CreditCardNotFoundExceptions;
import vestido_bank.VestidoBank.Repository.ClientRepository;
import vestido_bank.VestidoBank.Repository.CreditCardRepository;

@Service
public class CreditCardService {

  private CreditCardRepository creditCardRepository;
  private ClientService clientService;
  private ClientRepository clientRepository;

  @Autowired
  public CreditCardService(CreditCardRepository creditCardRepository, ClientService clientService,
      ClientRepository clientRepository) {
    this.creditCardRepository = creditCardRepository;
    this.clientService = clientService;
    this.clientRepository = clientRepository;
  }

  public List<CreditCard> getAllCredits() {
    return creditCardRepository.findAll();
  }

  public CreditCard findById(Long id) {
    return creditCardRepository.findById(id)
        .orElseThrow(() -> new CreditCardNotFoundExceptions("Nao encontrado"));

  }

  public CreditCard createCard(Long clientId, CreditCard creditCard) {

    Optional<Client> client = clientRepository.findById(clientId);
    if (client.isEmpty()) {
      throw new ClientNotFoundException("Não foi possível encontrar cliente");
    }

    creditCard.setClient(
        client.get()
    );
    return creditCardRepository.save(creditCard);
  }

  public CreditCard updateCard(Long id, Long clientId, CreditCard creditCard) {
    CreditCard creditCard1 = findById(id);
    Client client = clientService.getById(clientId); // Recupera o cliente pelo ID

    if (client == null) {
      throw new ClientNotFoundException("Cliente não encontrado");
    }

    creditCard1.setDataVencimento(creditCard.getDataVencimento());
    creditCard1.setFaturaAtual(creditCard.getFaturaAtual());
    creditCard1.setLimite(creditCard.getLimite());
    creditCard1.setStatus(creditCard.getStatus());
    creditCard1.setClient(client);

    return creditCardRepository.save(creditCard1);
  }

  public CreditCard deleteCard(Long id) {
    CreditCard creditCard = findById(id);

    creditCard.setClient(null);
    creditCardRepository.delete(creditCard);
    return creditCard;
  }

  public List<BigDecimal> getFaturaAtual(Long clientId) {

    return creditCardRepository.findFaturasAtuaisByClientId(clientId);
  }


}
