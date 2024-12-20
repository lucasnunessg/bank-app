package vestido_bank.VestidoBank.Exceptions;

public class DepositInvalid extends IllegalArgumentException {

  public DepositInvalid(String message) {
    super(message);
  }
}
