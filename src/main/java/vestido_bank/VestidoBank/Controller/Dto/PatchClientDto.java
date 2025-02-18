package vestido_bank.VestidoBank.Controller.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import vestido_bank.VestidoBank.Entity.Client;

public record PatchClientDto(
    String name,
    String cpf,
    String contact,
    String address,
    @Email(message = "Digite um e-mail válido!")
    String email,
    @Size(min = 6, message = "Senha deve conter 6 dígitos ou mais")
    String password
) {

}
