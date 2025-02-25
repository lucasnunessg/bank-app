package vestido_bank.VestidoBank.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vestido_bank.VestidoBank.Entity.CreditCard;
import vestido_bank.VestidoBank.Exceptions.CreditCardNotFoundExceptions;
import vestido_bank.VestidoBank.Repository.CreditCardRepository;

@Service
public class CreditCardService {

  private CreditCardRepository creditCardRepository;

  @Autowired
  public CreditCardService(CreditCardRepository creditCardRepository) {
    this.creditCardRepository = creditCardRepository;
  }

  public List<CreditCard> getAllCredits() {
   return creditCardRepository.findAll();
  }

  public CreditCard findById(Long id) {
    return creditCardRepository.findById(id)
        .orElseThrow(() -> new CreditCardNotFoundExceptions("Nao encontrado"));

  }

  public CreditCard createCard(Long clientId, CreditCard creditCard) {
    return creditCardRepository.save(creditCard);
  }

  public CreditCard updateCard(Long id, CreditCard creditCard) {
    CreditCard creditCard1 = findById(id);

    creditCard.setClient(creditCard.getClient());
    creditCard.setDataVencimento(creditCard.getDataVencimento());
    creditCard.setFaturaAtual(creditCard.getFaturaAtual());
    creditCard.setLimite(creditCard.getLimite());
    creditCard.setStatus(creditCard.getStatus());

    return creditCardRepository.save(creditCard1);
  }

  public CreditCard deleteCard(Long id) {
  CreditCard creditCard = findById(id);

    creditCardRepository.delete(creditCard);
    return creditCard;
  }

}
