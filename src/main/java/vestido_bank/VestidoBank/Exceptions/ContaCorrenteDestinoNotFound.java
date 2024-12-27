package vestido_bank.VestidoBank.Exceptions;

public class ContaCorrenteDestinoNotFound extends RuntimeException {

  public ContaCorrenteDestinoNotFound(String message) {
    super("Conta Corrente de destino não encontrada.");
  }
}
