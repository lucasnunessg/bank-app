package vestido_bank.VestidoBank.Exceptions;

import org.springframework.web.client.ResourceAccessException;

public class ConnectionFailedException extends ResourceAccessException {

  public ConnectionFailedException(String message) {
    super("Ocorreu um erro de conexão, tente novamente mais tarde.");
  }
}
