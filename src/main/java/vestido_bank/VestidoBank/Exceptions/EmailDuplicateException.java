package vestido_bank.VestidoBank.Exceptions;

public class EmailDuplicateException extends RuntimeException {

  public EmailDuplicateException(String message) {
    super("E-mail já utilizado.");
  }
}
