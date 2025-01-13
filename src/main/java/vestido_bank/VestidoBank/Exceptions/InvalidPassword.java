package vestido_bank.VestidoBank.Exceptions;

public class InvalidPassword extends RuntimeException {

  public InvalidPassword(String message) {
    super(message);
  }

  public InvalidPassword() {
    super("Senha inválida. A senha deve conter pelo menos: "
        + "uma letra maiúscula, uma letra minúscula, um número, um caractere especial e ter no mínimo 8 caracteres.");
  }
}
