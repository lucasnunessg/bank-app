package vestido_bank.VestidoBank.Exceptions;

public class ContaCorrenteOrigemNotFound extends RuntimeException {

  public ContaCorrenteOrigemNotFound(String message) {
    super("Conta Corrente de origem não encontrada.");
  }
}
