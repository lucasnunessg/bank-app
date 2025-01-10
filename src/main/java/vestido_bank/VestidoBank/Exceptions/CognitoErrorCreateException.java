package vestido_bank.VestidoBank.Exceptions;

public class CognitoErrorCreateException extends RuntimeException {

  public CognitoErrorCreateException(String message) {
    super("Erro ao criar usuário, contatar AWS.");
  }
}
