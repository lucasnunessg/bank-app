package vestido_bank.VestidoBank.Controller.Dto;

import jakarta.validation.constraints.NotNull;

public record ResetPasswordDto(@NotNull String password, @NotNull String token) {

}
