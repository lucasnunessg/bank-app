package vestido_bank.VestidoBank.Exceptions;

public class ClientNotFoundException extends RuntimeException {

  public ClientNotFoundException(String message) {
    super("Cliente não encontrado!");
  }
}
