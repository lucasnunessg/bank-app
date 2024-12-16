package vestido_bank.VestidoBank.Controller.Dto;

import java.time.LocalDateTime;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaCorrente;

public record ContaCorrenteCreateDto(float saldo, float limite) {

  public ContaCorrente toEntity(Client client) {
    return new ContaCorrente(saldo, limite, LocalDateTime.now(), client);
  }
}
