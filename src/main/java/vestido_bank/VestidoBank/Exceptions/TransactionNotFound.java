package vestido_bank.VestidoBank.Exceptions;

public class TransactionNotFound extends RuntimeException {

  public TransactionNotFound(String message) {
    super("Transação não encontrada");
  }
}
