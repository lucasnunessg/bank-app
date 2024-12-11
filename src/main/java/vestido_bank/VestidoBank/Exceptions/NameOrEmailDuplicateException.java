package vestido_bank.VestidoBank.Exceptions;

public class NameOrEmailDuplicateException extends RuntimeException {

  public NameOrEmailDuplicateException(String message) {
    super("Nome ou E-mail já existentes.");
  }
}
