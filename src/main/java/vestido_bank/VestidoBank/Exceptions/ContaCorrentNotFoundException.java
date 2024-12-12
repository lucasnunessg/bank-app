package vestido_bank.VestidoBank.Exceptions;

public class ContaCorrentNotFoundException extends RuntimeException {

  public ContaCorrentNotFoundException(String message) {
    super("Conta corrente não encontrada!");
  }
}
