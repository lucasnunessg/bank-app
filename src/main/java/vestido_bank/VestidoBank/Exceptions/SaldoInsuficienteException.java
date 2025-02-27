package vestido_bank.VestidoBank.Exceptions;

public class SaldoInsuficienteException extends RuntimeException {

  public SaldoInsuficienteException(String message) {
    super("Seu saldo não é suficiente para realizar a operação.");
  }
}
