package vestido_bank.VestidoBank.Controller.Dto;

import java.time.LocalDateTime;
import vestido_bank.VestidoBank.Entity.ContaCorrente;

public record ContaCorrenteCreateDto(float saldo, float limite, LocalDateTime data_criacao) {
  public ContaCorrente toEntity() {
    return new ContaCorrente(saldo, limite, data_criacao);
  }

}
