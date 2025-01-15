package vestido_bank.VestidoBank.Controller.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import vestido_bank.VestidoBank.Entity.Client;


public record CreateClientDto(
    @NotBlank
    String name,

    @NotBlank
    String cpf,

    @NotBlank
    String contact,

    @NotBlank
    String address,


    @Email(message = "Digite um e-mail válido!")
    String email,

    @Size(min = 6, message = "Senha deve conter 6 dígitos ou mais")
    @NotBlank
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=]).+$",
        message = "Senha deve conter pelo menos uma letra maiúscula, uma minúscula, um número e um caractere especial"
    )
    String password

) {

  public Client toEntity() {
    return new Client(name, cpf, contact, address, email, password);
  }
}
