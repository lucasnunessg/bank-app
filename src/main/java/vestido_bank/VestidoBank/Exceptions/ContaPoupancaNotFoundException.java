package vestido_bank.VestidoBank.Exceptions;

public class ContaPoupancaNotFoundException extends RuntimeException {

  public ContaPoupancaNotFoundException(String message) {
    super("Conta poupança não encontrada!");
  }
}
