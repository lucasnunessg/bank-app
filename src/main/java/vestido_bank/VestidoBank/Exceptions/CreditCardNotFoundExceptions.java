package vestido_bank.VestidoBank.Exceptions;

public class CreditCardNotFoundExceptions extends RuntimeException {

  public CreditCardNotFoundExceptions(String message) {
    super("Não foi possível encontrar Cartão de Crédito.");
  }
}
