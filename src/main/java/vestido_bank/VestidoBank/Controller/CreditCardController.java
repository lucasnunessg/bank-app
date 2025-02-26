package vestido_bank.VestidoBank.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.CreditCard;
import vestido_bank.VestidoBank.Exceptions.ClientNotFoundException;
import vestido_bank.VestidoBank.Exceptions.CreditCardNotFoundExceptions;
import vestido_bank.VestidoBank.Service.ClientService;
import vestido_bank.VestidoBank.Service.CreditCardService;

@RestController
@RequestMapping("/credit-card")
public class CreditCardController {

  private CreditCardService creditCardService;
  private ClientService clientService;

  @Autowired
  public CreditCardController(CreditCardService creditCardService, ClientService clientService) {
    this.creditCardService = creditCardService;
    this.clientService = clientService;
  }

  @GetMapping
  public List<CreditCard> getAllCredits() {
    return creditCardService.getAllCredits();

  }

  @GetMapping("/{id}")
  public CreditCard getCreditById(@PathVariable Long id) throws CreditCardNotFoundExceptions {
    CreditCard creditCard = creditCardService.findById(id);
        if(creditCard == null) {
          throw new CreditCardNotFoundExceptions("Não foi possível encontrar");
        }

        return creditCard;
  }

  @PostMapping("/{clientId}/create/credit-card")
  public CreditCard createCreditCard(
      @PathVariable Long clientId,
      @RequestBody CreditCard creditCard
  ) throws ClientNotFoundException {

    Client client = clientService.getById(clientId);
    if (client == null) {
      throw new ClientNotFoundException("Cliente não encontrado, tente novamente");
    }

    return creditCardService.createCard(clientId, creditCard);
  }

  @PutMapping("/{creditCardId}/edit")
  public CreditCard editCardCard(@PathVariable Long creditCardId, @RequestBody CreditCard creditCard) {
    CreditCard creditCard1 = creditCardService.findById(creditCardId);
    if(creditCard1 == null) {
      throw new CreditCardNotFoundExceptions("Não foi possível encontrar cartão de crédito");
    }
    return creditCardService.updateCard(creditCard1.getId(), creditCard);
  }

  @DeleteMapping("/{creditCardId}/delete")
  public CreditCard deleteCard(@PathVariable Long creditCardId) throws CreditCardNotFoundExceptions {
    CreditCard creditCard = creditCardService.findById(creditCardId);
    if(creditCard == null) {
      throw  new CreditCardNotFoundExceptions("Não encontrado!");
    }

    return creditCardService.deleteCard(creditCard.getId());
  }

}
