package vestido_bank.VestidoBank.Exceptions;

public class LimiteInsuficienteException extends RuntimeException {

  public LimiteInsuficienteException(String message) {
    super("Limite do cartão insuficiente para realizar a compra.");
  }
}
