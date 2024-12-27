package vestido_bank.VestidoBank.Exceptions;

public class ContaPoupancaDestinoNotFound extends RuntimeException {

  public ContaPoupancaDestinoNotFound(String message) {
    super("Conta poupança de destino não encontrada.");
  }
}
