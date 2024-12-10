package vestido_bank.VestidoBank.Exceptions;

public class ClientDuplicateException extends RuntimeException {

  public ClientDuplicateException(String message) {
    super("Cliente já existe.");
  }
}
