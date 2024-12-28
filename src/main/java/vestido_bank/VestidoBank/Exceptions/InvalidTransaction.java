package vestido_bank.VestidoBank.Exceptions;

public class InvalidTransaction extends RuntimeException {

  public InvalidTransaction(String message) {
    super("Saldo insuficiente para fazer a transferência");
  }
}
