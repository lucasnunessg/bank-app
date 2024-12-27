package vestido_bank.VestidoBank.Exceptions;

public class ContaPoupancaOrigemNotFound extends RuntimeException {

  public ContaPoupancaOrigemNotFound(String message) {
    super("Conta Poupança de origem não encontrada.");
  }
}
